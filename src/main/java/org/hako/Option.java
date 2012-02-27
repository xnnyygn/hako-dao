/**
 * Copyright 2012 XnnYygn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.hako;

/**
 * Base class of {@link Some} and {@link None}. This is the idea from <a
 * href="http://www.scala-lang.org/">Scala</a> to prevent {@code null} in Java.
 * <p><b>DONT</b> create your subclass of {@link Option}.</p>
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public interface Option<T> {

  /**
   * Get value.
   * 
   * @return value
   * @throws UnsupportedOperationException if get value from {@link None}
   * @see None#get()
   */
  public T get() throws UnsupportedOperationException;

  /**
   * Get value or default value.
   * 
   * @param defVal default value
   * @return value in {@link Some} or default value if in {@link None}
   * @see Some#getOrElse(Object)
   * @see None#getOrElse(Object)
   */
  public T getOrElse(T defVal);
  
  /**
   * Check if has value.
   * 
   * @return true if has, otherwise false
   */
  public boolean hasValue();
  
  /**
   * Equals.
   * 
   * @param obj object
   * @return true if equals, otherwise false
   */
  public boolean equals(Object obj);

  /**
   * Get hash code.
   * 
   * @return hash code
   */
  public int hashCode();
  
}
