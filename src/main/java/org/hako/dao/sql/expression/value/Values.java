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
public class Values {

  public static final NullValue NULL = new NullValue();
  public static final DefaultValue DEFAULT = new DefaultValue();

  public static LongValue create(long l) {
    return new LongValue(l);
  }

  public static StringValue create(String s) {
    return new StringValue(s);
  }

  public static DynamicValue create(Object obj) {
    if (obj instanceof String) {
      return create((String) obj);
    }
    return new ObjectValue(obj);
  }

  /**
   * Create static value.
   * 
   * @param obj object
   * @return static value
   */
  public static StaticValue createStatic(Object obj) {
    return new StaticValue(create(obj));
  }

}
