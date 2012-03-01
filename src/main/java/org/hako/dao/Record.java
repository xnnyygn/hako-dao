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
package org.hako.dao;

import java.util.HashMap;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.mapping.field.FieldMeta;
import org.hako.dao.mapping.field.MappedField;

/**
 * A wrapper class of result line from database query.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Record {

  /**
   * A map use mapped field as key, field value as value.
   */
  private final Map<MappedField<?>, Object> mappedFieldProps =
      new HashMap<MappedField<?>, Object>();
  /**
   * A map use property name (variable name of field definition) as key, field
   * value as value.
   */
  private final Map<String, Object> properyNameProps =
      new HashMap<String, Object>();

  /**
   * Create with meta value map.
   * 
   * @param metaValueMap
   */
  public Record(Map<FieldMeta, Object> metaValueMap) {
    super();
    for (Map.Entry<FieldMeta, Object> entry : metaValueMap.entrySet()) {
      FieldMeta meta = entry.getKey();
      Object value = entry.getValue();
      this.mappedFieldProps.put(meta.getField(), value);
      this.properyNameProps.put(meta.getPropertyName(), value);
    }
  }

  /**
   * Get value by mapped field.
   * 
   * @param field field
   * @return some value or not
   */
  @SuppressWarnings("unchecked")
  public <T> Option<T> get(MappedField<T> field) {
    if (mappedFieldProps.containsKey(field)) {
      return new Some<T>((T) mappedFieldProps.get(field));
    }
    return new None<T>();
  }

  /**
   * Get value by property name.
   * 
   * @param propertyName property name
   * @return some value or none
   */
  public Option<Object> get(String propertyName) {
    if (properyNameProps.containsKey(propertyName)) {
      return new Some<Object>(properyNameProps.get(propertyName));
    }
    return new None<Object>();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Record [");
    builder.append(properyNameProps);
    builder.append("]");
    return builder.toString();
  }

}
