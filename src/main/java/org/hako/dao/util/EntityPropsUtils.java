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
package org.hako.dao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hako.dao.SimpleField;

/**
 * Entity properties utilities.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
// TODO move into GenericEntity
public class EntityPropsUtils {

  /**
   * Fix case solution in properties. Compare key in properties and field
   * property names, if lower case of two keys equals, use field property name
   * as key, property as value to recreate properties.
   * 
   * @param srcProps source properties
   * @param fields fields
   * @return new properties use field property name as key
   * @see #collectFieldPropertyNames(List)
   */
  public static Map<String, Object> fixCase(Map<String, Object> srcProps,
      List<SimpleField<?>> fields) {
    Map<String, Object> destProps = new HashMap<String, Object>();
    Map<String, String> propertyNames = collectFieldPropertyNames(fields);
    for (Map.Entry<String, Object> entry : srcProps.entrySet()) {
      String lowerCaseKey = entry.getKey().toLowerCase();
      if (propertyNames.containsKey(lowerCaseKey)) {
        destProps.put(propertyNames.get(lowerCaseKey), entry.getValue());
      }
    }
    return destProps;
  }

  /**
   * Collect field property name. Return a map use property lower case name as
   * key, property name as value.
   * 
   * @param fields fields
   * @return field property lower case name and name
   * @see SimpleField#getPropertyName()
   * @see String#toLowerCase()
   */
  private static Map<String, String> collectFieldPropertyNames(
      List<SimpleField<?>> fields) {
    Map<String, String> names = new HashMap<String, String>();
    for (SimpleField<?> f : fields) {
      String propertyName = f.getPropertyName();
      names.put(propertyName.toLowerCase(), propertyName);
    }
    return names;
  }

}
