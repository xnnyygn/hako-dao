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
package org.hako.dao.mapping;

import org.hako.dao.field.Field;


/**
 * Default mapping.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
// TODO rename
public class DefaultMapping {

  public void setup(Class<?> entityClass) {
    
    for (java.lang.reflect.Field f : entityClass.getDeclaredFields()) {
      if (f.getType().isAssignableFrom(Field.class)) {
        System.out.println(f.getName());
      }
    }
  }

}
