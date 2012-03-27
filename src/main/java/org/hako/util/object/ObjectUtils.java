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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean utilities.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ObjectUtils {

  private static final Log logger = LogFactory.getLog(ObjectUtils.class);

  /**
   * Get properties from object, including {@code null} value.
   * 
   * @param object
   * @return properties
   * @see #getProperties(Object, boolean)
   */
  public static Map<String, Object> getProperties(Object object) {
    return getProperties(object, false);
  }

  /**
   * Get properties from object.
   * 
   * @param object bean, may be {@code null}
   * @param emitNull should filter null value ?
   * @return properties, if bean is null, just return a empty map
   */
  public static Map<String, Object> getProperties(Object object,
      boolean emitNull) {
    if (object instanceof Map<?, ?>) {
      return getPropertiesFromMap(object, emitNull);
    }
    return getPropertiesFromBean(object, emitNull);
  }

  /**
   * Get properties from bean.
   * 
   * @param beanObject
   * @param emitNull
   * @return properties
   */
  private static Map<String, Object> getPropertiesFromBean(Object beanObject,
      boolean emitNull) {
    Map<String, Object> properties = new HashMap<String, Object>();
    if (beanObject != null) {
      List<Getter> getters = Getters.list(beanObject.getClass());
      boolean debugEnabled = logger.isDebugEnabled();
      for (Getter getter : getters) {
        try {
          Object value = getter.from(beanObject);
          if (!emitNull || value != null) {
            properties.put(getter.getPropertyName(), value);
          }
        } catch (Exception e) {
          // omit
          if (debugEnabled) {
            logger.debug("failed to get value from object", e);
          }
        }
      }
    }
    return properties;
  }

  /**
   * Get properties from map and emit null if need.
   * 
   * @param mapObject
   * @param emitNull
   * @return properties
   */
  private static Map<String, Object> getPropertiesFromMap(Object mapObject,
      boolean emitNull) {
    Map<String, Object> properties = new HashMap<String, Object>();
    if (mapObject != null) {
      for (Map.Entry<?, ?> entry : ((Map<?, ?>) mapObject).entrySet()) {
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (!emitNull || (key != null && value != null)) {
          properties.put((String) key, value);
        }
      }
    }
    return properties;
  }

}
