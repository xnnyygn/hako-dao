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
package org.hako.util.object;

import java.lang.reflect.Field;

/**
 * Use field to get value.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class FieldGetter implements Getter {

  private final Field field;

  /**
   * Create.
   * 
   * @param field
   */
  public FieldGetter(Field field) {
    super();
    this.field = field;
  }

  public Object from(Object obj) throws Exception {
    return field.get(obj);
  }

  public String getPropertyName() {
    return field.getName();
  }
  
}
