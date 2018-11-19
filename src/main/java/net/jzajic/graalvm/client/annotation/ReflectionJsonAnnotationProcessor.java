package net.jzajic.graalvm.client.annotation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SupportedAnnotationTypes(
	  "com.fasterxml.jackson.annotation.*")
	@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ReflectionJsonAnnotationProcessor extends AbstractProcessor {

	private Elements elementUtils;
	private Set<String> classes = new HashSet<>();
	private Map<String, List<Map>> methods = new HashMap<>();
	private Map<String, List<Map>> fields = new HashMap<>();

	private String outputDir = System.getProperty("maven.multiModuleProjectDirectory");
	private File outputFile;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		elementUtils = processingEnv.getElementUtils();
		outputFile = new File(outputDir, "reflection-gen.json");
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(annotation)) {
				ElementKind kind = annotatedElement.getKind();
				switch(kind) {
					case CONSTRUCTOR:
					{
						List<Map> classList = getClassList(getEnclosingClass(annotatedElement), methods);
						declareConstructor(classList, annotatedElement);
						break;
					}
					case METHOD:
					{
						List<Map> classList = getClassList(getEnclosingClass(annotatedElement), methods);
						declareMethod(classList, annotatedElement);
						break;
					}
					case FIELD:
					{
						List<Map> classList = getClassList(getEnclosingClass(annotatedElement), fields);
						declareField(classList, annotatedElement);
						break;
					}
				}
			}
		}
		
		List<Object> jsonArray = new ArrayList<>();
		for (String className : classes) {
			Map jsonObject = new HashMap<>();
			jsonObject.put("name", className);
			if(fields.containsKey(className)) {
				jsonObject.put("fields", fields.get(className));
			}
			if(methods.containsKey(className)) {
				jsonObject.put("methods", methods.get(className));
			}
			jsonArray.add(jsonObject);
		}
		
		try (FileOutputStream fo = new FileOutputStream(outputFile)){
			new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(fo, jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return false;
	}
	
	private void declareField(List<Map> classList, Element annotatedElement) {
		Map jsonObject = new HashMap<>();
		jsonObject.put("name", annotatedElement.getSimpleName().toString());
		//TODO: enable in future versions?
		//jsonObject.put("allowWrite", annotatedElement.getModifiers().contains(Modifier.PUBLIC));
		classList.add(jsonObject);
	}

	private void declareMethod(List<Map> classList, Element annotatedElement) {
		Map jsonObject = new HashMap<>();
		jsonObject.put("name", annotatedElement.getSimpleName().toString());
		declareParameters(jsonObject, annotatedElement);
		classList.add(jsonObject);
	}

	private void declareConstructor(List<Map> classList, Element annotatedElement) {
		Map jsonObject = new HashMap<>();
		jsonObject.put("name", "<init>");
		declareParameters(jsonObject, annotatedElement);
		classList.add(jsonObject);
	}

	private void declareParameters(Map jsonObject, Element annotatedElement) {
		List<String> parameterList = new ArrayList<>();
		ExecutableElement executableType = ExecutableElement.class.cast(annotatedElement);
		List<? extends VariableElement> parameterTypes = executableType.getParameters();
		for (VariableElement el : parameterTypes) {
			declareParameter(parameterList, el.asType());
		}
		if(!parameterList.isEmpty())
			jsonObject.put("parameterTypes", parameterList);
	}

	private void declareParameter(List<String> parameterList, TypeMirror typeMirror) {
		String parameterTypeStr = parameterTypeToString(typeMirror);
		if(parameterTypeStr != null)
			parameterList.add(parameterTypeStr);
	}

	private String parameterTypeToString(TypeMirror typeMirror) {
		switch(typeMirror.getKind()) {
		case ARRAY:
			ArrayType at = (ArrayType) typeMirror;
			String elType = parameterTypeToString(at.getComponentType());
			if(elType != null)
				return elType+"[]";
			else
				return null;
		default:
			if(typeMirror.getKind().isPrimitive())
				return typeMirror.toString();
			else {
				DeclaredType declared = DeclaredType.class.cast(typeMirror);
				Element asElement = declared.asElement();
				TypeElement te = TypeElement.class.cast(asElement);
				return getClassName(te);
			}
		}
	}

	private String getEnclosingClass(Element e) {
		while( e != null && !(e instanceof TypeElement) )
    {
        e = e.getEnclosingElement();
    }
		TypeElement te = TypeElement.class.cast(e);
		return getClassName(te);
	}
	
	private String getClassName(TypeElement te) {
		String className;
		if(te.getNestingKind() == NestingKind.MEMBER) {
			StringBuilder nesting = new StringBuilder();
			nesting.append("$"+te.getSimpleName().toString());
			
			Element parent = te.getEnclosingElement();
			TypeElement tp = TypeElement.class.cast(parent);
			
			while(tp.getNestingKind() != NestingKind.TOP_LEVEL) {
				nesting.insert(0, "$"+tp.getSimpleName().toString());
				parent = te.getEnclosingElement();
				tp = TypeElement.class.cast(parent);
			}
			nesting.insert(0, tp.getQualifiedName().toString());
			className =  nesting.toString();
		} else if (te.getNestingKind() == NestingKind.TOP_LEVEL){
			className = te.getQualifiedName().toString();
		} else {
			return null;
		}
		classes.add(className);
		return className;
	}

	private List<Map> getClassList(String className, Map<String, List<Map>> fields) {
		return fields.computeIfAbsent(className, key -> new ArrayList<>());
	}
	
}
