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
package org.hako.dao.sql.expression.value;

/**
 * Value factory.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ValueFactory {

  /**
   * Create long value.
   * 
   * @param l long
   * @return value
   * @see LongValue
   */
  public static LongValue create(long l) {
    return new LongValue(l);
  }

  /**
   * Create string value.
   * 
   * @param s string
   * @return value
   * @see StringValue
   */
  public static StringValue create(String s) {
    return new StringValue(s);
  }

  /**
   * Create object value.
   * 
   * @param obj object
   * @return value
   * @see ObjectValue
   */
  public static ObjectValue create(Object obj) {
    return new ObjectValue(obj);
  }
}
