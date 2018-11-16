package net.jzajic.graalvm;

import java.io.Serializable;

/**
 * An interface for serializable field references. It is only valid to construct instances of this
 * interface with field references such as {@code Foo::bar}.
 */
@FunctionalInterface
public interface FieldReference<A, R> extends Serializable {

  /**
   * Applies this method reference to the specified owning object.
   *
   * @param self the owning object
   * @return the method result
   */
  R apply(A self) throws Exception;
}
