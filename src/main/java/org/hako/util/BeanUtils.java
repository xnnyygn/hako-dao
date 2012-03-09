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
package org.hako.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;

/**
 * Bean utilities.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BeanUtils {

  private static final Log logger = LogFactory.getLog(BeanUtils.class);

  /**
   * Get properties from bean.
   * 
   * @param bean bean, may be {@code null}
   * @return properties, if bean is null, just return a empty map
   */
  public static Map<String, Object> getProperties(Object bean) {
    Map<String, Object> props = new HashMap<String, Object>();
    if (bean != null) {
      props.putAll(getPropsByGetterMethod(bean));
      props.putAll(getPropsByFields(bean));
    }
    return props;
  }

  /**
   * Get properties by getter method.
   * 
   * @param bean bean, never be {@code null}
   * @return properties
   * @see #getValueByGetterMethod(Method, Object)
   */
  private static Map<String, Object> getPropsByGetterMethod(Object bean) {
    Map<String, Object> props = new HashMap<String, Object>();
    for (Method m : bean.getClass().getMethods()) {
      String name = m.getName();
      if (name.startsWith("get") && name.length() > 3
          && m.getParameterTypes().length == 0) {
        Option<Object> valueOpt = getValueByGetterMethod(m, bean);
        if (valueOpt.hasValue()) {
          String propertyName = StringUtils.uncapitalize(name.substring(3));
          props.put(propertyName, valueOpt.get());
        }
      }
    }
    return props;
  }

  /**
   * Get value by getter method.
   * 
   * @param getter
   * @param bean bean, never be {@code null}
   * @return some value or none
   * @see Method#invoke(Object, Object...)
   */
  private static Option<Object> getValueByGetterMethod(Method getter,
      Object bean) {
    try {
      return new Some<Object>(getter.invoke(bean));
    } catch (Exception e) {
      logger.warn("failed to get value by getter method", e);
    }
    return new None<Object>();
  }

  /**
   * Get properties by fields.
   * 
   * @param bean bean, never be {@code null}
   * @return properties
   * @see #getPropsByFields(Object)
   */
  private static Map<String, Object> getPropsByFields(Object bean) {
    Map<String, Object> props = new HashMap<String, Object>();
    for (Field f : bean.getClass().getFields()) {
      props.put(f.getName(), getValueByField(f, bean));
    }
    return props;
  }

  /**
   * Get value by field.
   * 
   * @param field field
   * @param bean bean, never be {@code null}
   * @return some value or none
   * @see Field#get(Object)
   */
  private static Option<Object> getValueByField(Field field, Object bean) {
    try {
      return new Some<Object>(field.get(bean));
    } catch (Exception e) {
      logger.warn("failed to get value by field", e);
    }
    return new None<Object>();
  }



}
