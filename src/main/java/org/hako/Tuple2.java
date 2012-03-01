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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tuple 2.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Tuple2<T1, T2> {

  public final T1 _1;
  public final T2 _2;

  /**
   * Create.
   * 
   * @param _1 first element
   * @param _2 second element
   */
  public Tuple2(T1 _1, T2 _2) {
    super();
    this._1 = _1;
    this._2 = _2;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(_1).append(_2).toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Tuple2)) return false;
    Tuple2<?, ?> other = (Tuple2<?, ?>) obj;
    return new EqualsBuilder().append(_1, other._1).append(_2, other._2)
        .isEquals();
  }

}
