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
import org.hako.dao.mapping.entity.EntityMeta;
import org.hako.dao.mapping.field.FieldMeta;
import org.hako.dao.mapping.field.MappedField;

/**
 * Record.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Record {

  private final Map<MappedField<?>, Object> mappedFieldProps =
      new HashMap<MappedField<?>, Object>();
  private final Map<String, Object> properyNameProps =
      new HashMap<String, Object>();

  public Record(Map<String, Object> props, EntityMeta entity) {
    super();
    Map<String, FieldMeta> columnAliasNames = entity.collectColumnAliasNames();
    for (Map.Entry<String, Object> p : props.entrySet()) {
      String lowerCaseKey = p.getKey().toLowerCase();
      if (columnAliasNames.containsKey(lowerCaseKey)) {
        FieldMeta meta = columnAliasNames.get(lowerCaseKey);
        this.mappedFieldProps.put(meta.getField(), p.getValue());
        this.properyNameProps.put(meta.getPropertyName(), p.getValue());
      }
    }
  }

  /**
   * Get value.
   * 
   * @param field
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> Option<T> get(MappedField<T> field) {
    if (mappedFieldProps.containsKey(field)) {
      return new Some<T>((T) mappedFieldProps.get(field));
    }
    return new None<T>();
  }

  /**
   * Get by property name.
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
