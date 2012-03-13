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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of list getters in object.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class Getters {

  /**
   * List getters of object.
   * <p>
   * If object is {@code null}, just return an empty getter list.
   * </p>
   * 
   * @param clazz class
   * @return getters
   * @see #listMethodOnes(Class)
   * @see #listFieldOnes(Class)
   */
  public static List<Getter> list(Class<?> clazz) {
    List<Getter> getters = new ArrayList<Getter>();
    getters.addAll(listMethodOnes(clazz));
    getters.addAll(listFieldOnes(clazz));
    return getters;
  }

  /**
   * List method getters.
   * 
   * @param clazz
   * @return getters
   * @see Class#getMethods()
   * @see IsMethodGetter
   * @see GetMethodGetter
   */
  private static List<Getter> listMethodOnes(Class<?> clazz) {
    List<Getter> methodGetters = new ArrayList<Getter>();
    for (Method method : clazz.getMethods()) {
      if (method.getParameterTypes().length == 0) {
        String methodName = method.getName();
        int methodNameLength = methodName.length();
        if (methodNameLength > 2 && methodName.startsWith("is")) {
          methodGetters.add(new IsMethodGetter(method));
        } else if (methodNameLength > 3 && methodName.startsWith("get")) {
          methodGetters.add(new GetMethodGetter(method));
        }
      }
    }
    return methodGetters;
  }

  /**
   * List field getters.
   * 
   * @param clazz
   * @return getters
   * @see Class#getFields()
   * @see FieldGetter
   */
  private static List<Getter> listFieldOnes(Class<?> clazz) {
    List<Getter> fieldGetters = new ArrayList<Getter>();
    for (Field field : clazz.getFields()) {
      fieldGetters.add(new FieldGetter(field));
    }
    return fieldGetters;
  }

}
