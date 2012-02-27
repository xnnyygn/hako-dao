package org.hako;

/**
 * Some value.
 * 
 * @author xnnyygn
 */
public final class Some<T> implements Option<T> {

  private final T value;

  /**
   * Create with value.
   * 
   * @param value
   * @throws IllegalArgumentException if value is {@code null}
   */
  public Some(T value) throws IllegalArgumentException {
    super();
    if (value == null) {
      throw new IllegalArgumentException("cannot create Some object with null");
    }
    this.value = value;
  }

  /**
   * Return {@link #value}.
   * 
   * @return {@link #value}
   */
  public T get() throws UnsupportedOperationException {
    return value;
  }

  /**
   * Return {@link #value}.
   * 
   * @return {@link #value}
   * @see #get()
   */
  public T getOrElse(T defVal) {
    return get();
  }

  /**
   * Return {@code true}.
   */
  public boolean hasValue() {
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Some<?> other = (Some<?>) obj;
    if (value == null) {
      if (other.value != null) return false;
    } else if (!value.equals(other.value)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "Some(" + value + ")";
  }

}
