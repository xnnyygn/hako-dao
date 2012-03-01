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
 * None, means no value, used to replace {@code null}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class None<T> implements Option<T> {

  /**
   * Throw {@link UnsupportedOperationException}.
   */
  public T get() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("cannot get value from None");
  }

  /**
   * Return default value.
   * 
   * @param defVal default value
   * @return {@code defVal}
   */
  public T getOrElse(T defVal) {
    return defVal;
  }

  /**
   * Return {@code false}.
   */
  public boolean hasValue() {
    return false;
  }

  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    if (obj.getClass() != getClass()) return false;
    return true;
  }

  /**
   * Since {@link None} has no field, just return {@code 0}.
   * 
   * @return {@code 0}
   */
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "None";
  }

}
