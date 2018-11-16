package net.jzajic.graalvm;

import static java.util.Arrays.*;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.spotify.hamcrest.util.DescriptionUtils;

public class IsStruct<A> extends TypeSafeDiagnosingMatcher<A> {

	Class<A> clazz;
	
	IsStruct(Class<A> clazz) {
		this.clazz = clazz;
  }

  Class<A> cls() {
  	return clazz;
  }

  private ImmutableMap<String, FieldHandler<A, ?>> fieldsHandlers = ImmutableMap.of();

  public static <A> IsStruct<A> struct(A expectedValue) {
  	IsStruct struct = struct(expectedValue.getClass());
  	return struct.where(expectedValue);
  }
  
  public static <A> IsStruct<A> struct(final Class<A> cls) {
    return new IsStruct<A>(cls);
  }
	
  public <T> IsStruct<A> where(
      final String fieldName,
      final Matcher<T> returnValueMatcher) {
    return where(
    		fieldName,
        self -> {
        	final Field field = fieldWithName(fieldName, self);
          field.setAccessible(true);
          final T returnValue = (T) field.get(self);
          return returnValue;
        },
        returnValueMatcher);
  }
  
  public <T> IsStruct<A> where(
      final FieldReference<A, T> fieldReference,
      final Matcher<T> returnValueMatcher) {
    final SerializedLambda serializedLambda = serializeLambda(fieldReference);

    ensureDirectMethodReference(serializedLambda);

    return where(
        serializedLambda.getImplMethodName(),
        fieldReference,
        returnValueMatcher);
  }

  private <T> IsStruct<A> where(
      final String fieldName,
      final FieldReference<A, T> valueExtractor,
      final Matcher<T> matcher) {

  	IsStruct<A> value = new IsStruct<A>(clazz);
  	Builder<Object, Object> builder = ImmutableMap.builder();
  	if(this.fieldsHandlers != null)
  		builder.putAll(this.fieldsHandlers);
  	builder.put(fieldName, FieldHandler.create(valueExtractor, matcher));
  	ImmutableMap build = builder.build();
  	value.fieldsHandlers = build;
  	return value;
  }
    
  private Field fieldWithName(String fieldName, A self) throws NoSuchFieldException {
    try {
      return self.getClass().getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      return self.getClass().getField(fieldName);
    }
  }
  
  public IsStruct<A> where(A expected) {
  	IsStruct<A> current = this;
		for (Field expectedField : expected.getClass().getFields()) {
			expectedField.setAccessible(true);
			Class<?> type = expectedField.getType();
			boolean isPrimitive = type.isEnum() || type.isPrimitive() || type.isArray() || String.class.isAssignableFrom(type);
      Object expectedValue;
			try {
				expectedValue = expectedField.get(expected);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				continue;
			}
      current = this.<Object>where(expectedField.getName(), self -> {
      	final Field field = fieldWithName(expectedField.getName(), self);
        field.setAccessible(true);
        final Object returnValue = field.get(self);
        return returnValue;
      }, isPrimitive || expectedValue == null ? Matchers.is(expectedValue) : innerMatcher(Sets.newHashSet(expected), expectedField, expectedValue));
		}
		return current;
	}

  private Matcher<Object> innerMatcher(Set<Object> parentvalues, Field expectedField, Object expectedValue) {
  	if(parentvalues.contains(expectedValue))
  		throw new IllegalStateException("Object graph cycles not allowed! Field name: "+expectedField.getName());
  	IsStruct struct = IsStruct.struct(expectedField.getType());
  	return struct.where(expectedValue);
	}

	@Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof IsStruct) {
    	IsStruct<?> that = (IsStruct<?>) o;
      return (this.clazz.equals(that.cls()))
           && (this.fieldsHandlers.equals(that.fieldsHandlers));
    }
    return false;
  }
  
  /**
   * Method uses serialization trick to extract information about lambda,
   * to give understandable name in case of mismatch.
   *
   * @param lambda lambda to extract the name from
   * @return a serialized version of the lambda, containing useful information for introspection
   */
  private static SerializedLambda serializeLambda(final Object lambda) {
    final Method writeReplace;
    try {
      writeReplace = AccessController.doPrivileged((PrivilegedExceptionAction<Method>) () -> {
        Method method = lambda.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        return method;
      });
    } catch (PrivilegedActionException e) {
      throw new IllegalStateException("Cannot serialize lambdas in unprivileged context", e);
    }

    try {
      return (SerializedLambda) writeReplace.invoke(lambda);
    } catch (ClassCastException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalArgumentException(
          "Could not serialize as a lambda (is it a lambda?): " + lambda, e);
    }
  }
  
  private static void ensureDirectMethodReference(final SerializedLambda serializedLambda) {
    try {
      final Class<?> implClass = Class.forName(serializedLambda.getImplClass().replace('/', '.'));
      if (stream(implClass.getMethods())
          .noneMatch(m ->
              m.getName().equals(serializedLambda.getImplMethodName())
              && !m.isSynthetic())) {
        throw new IllegalArgumentException("The supplied lambda is not a direct method reference");
      }
    } catch (final ClassNotFoundException e) {
      throw new IllegalStateException(
          "serializeLambda returned a SerializedLambda pointing to an invalid class", e);
    }
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.clazz.hashCode();
    h *= 1000003;
    h ^= this.fieldsHandlers.hashCode();
    return h;
  }
  
  @Override
  public void describeTo(Description description) {
    description.appendText(cls().getSimpleName()).appendText(" {\n");
    fieldsHandlers.forEach((methodName, handler) -> {
      final Matcher<?> matcher = handler.matcher();

      description.appendText("  ").appendText(methodName).appendText("(): ");

      Description innerDescription = new StringDescription();
      matcher.describeTo(innerDescription);

      indentDescription(description, innerDescription);
    });
    description.appendText("}");
  }
  
  private void indentDescription(Description description, Description innerDescription) {
    description
        .appendText(
            Joiner.on("\n  ").join(Splitter.on('\n').split(innerDescription.toString())))
        .appendText("\n");
  }
	
  static class FieldHandler<A, T> {

  	FieldReference<A, T> ref;
  	Matcher<T> matcher;
  	
    FieldReference<A, T> reference() {
    	return ref;
    }

    Matcher<T> matcher() {
    	return matcher;
    }
    
    private FieldHandler(FieldReference<A, T> ref, Matcher<T> matcher) {
			super();
			this.ref = ref;
			this.matcher = matcher;
		}

		static <A, T> FieldHandler<A, T> create(
        final FieldReference<A, T> reference,
        final Matcher<T> matcher) {
      return new FieldHandler<>(reference, matcher);
    }
  }



	@Override
	protected boolean matchesSafely(A item, Description mismatchDescription) {
		if (!cls().isInstance(item)) {
      mismatchDescription.appendText("not an instance of " + cls().getName());
      return false;
    }

    final Map<String, Consumer<Description>> mismatches = new LinkedHashMap<>();

    fieldsHandlers.forEach(
        (methodName, handler) ->
            matchField(item, handler).ifPresent(descriptionConsumer ->
                mismatches.put(methodName, descriptionConsumer)));

    if (!mismatches.isEmpty()) {
      mismatchDescription.appendText(cls().getSimpleName()).appendText(" ");
      DescriptionUtils.describeNestedMismatches(
          fieldsHandlers.keySet(),
          mismatchDescription,
          mismatches,
          IsStruct::describeField);
      return false;
    }

    return true;
	}
	
	private static void describeField(String name, Description description) {
    description.appendText(name).appendText("()");
  }
	
	private static <A> Optional<Consumer<Description>> matchField(
      final A item,
      final FieldHandler<A, ?> handler) {
    final Matcher<?> matcher = handler.matcher();
    final FieldReference<A, ?> reference = handler.reference();

    try {
      final Object value = reference.apply(item);
      if (!matcher.matches(value)) {
        return Optional.of(d -> matcher.describeMismatch(value, d));
      } else {
        return Optional.empty();
      }
    } catch (IllegalAccessException e) {
      return Optional.of(d -> d.appendText("not accessible"));
    } catch (NoSuchFieldException e) {
      return Optional.of(d -> d.appendText("did not exist"));
    } catch (InvocationTargetException e) {
      final Throwable cause = e.getCause();
      return Optional
          .of(d -> d.appendText("threw an exception: ")
              .appendText(cause.getClass().getCanonicalName())
              .appendText(": ").appendText(cause.getMessage()));
    } catch (Exception e) {
      return Optional
          .of(d -> d.appendText("threw an exception: ")
              .appendText(e.getClass().getCanonicalName())
              .appendText(": ").appendText(e.getMessage()));
    }
  }

}
