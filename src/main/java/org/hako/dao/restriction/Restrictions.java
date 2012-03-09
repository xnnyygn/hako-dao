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
package org.hako.dao.restriction;

import org.hako.dao.restriction.compare.EqualsRestriction;

/**
 * Restrictions.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Restrictions {

  /**
   * Create equals restriction.
   * 
   * @param propertyName column name
   * @param value
   * @return equals restriction
   * @see EqualsRestriction
   */
  public static EqualsRestriction eq(String propertyName, Object value) {
    // TODO refactor restriction
    return new EqualsRestriction(propertyName, value);
  }

  /**
   * Create like restriction.
   * 
   * @param propertyName property name
   * @param pattern
   * @return like restriction
   */
  public static Restriction like(String propertyName, String pattern) {
    // TODO refactor with starts with, ends with, contains
    return new LikeRestriction(propertyName, pattern);
  }

  /**
   * Create greater than restriction.
   * 
   * @param propertyName property name
   * @param value
   * @return restriction
   * @see GreaterThanRestriction
   */
  public static Restriction gt(String propertyName, Object value) {
    return new GreaterThanRestriction(propertyName, value);
  }

}
