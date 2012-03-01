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
package org.hako.dao.mapping.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.mapping.field.FieldMeta;
import org.hako.dao.mapping.field.MappedField;

/**
 * Entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class EntityMeta {

  private final TableName tableName;
  private final List<FieldMeta> fields; // all fields
  // variables below for fast invoke
  private final List<FieldMeta> pkFields = new ArrayList<FieldMeta>();
  private final List<FieldMeta> normalFields = new ArrayList<FieldMeta>();
  private final Map<MappedField<?>, FieldMeta> fieldMap =
      new HashMap<MappedField<?>, FieldMeta>();

  /**
   * Create.
   * 
   * @param tableName table name, contains low level table name and alias
   * @param fields field
   */
  public EntityMeta(TableName tableName, List<FieldMeta> fields) {
    super();
    this.tableName = tableName;
    this.fields = fields;
    // initialize primary key fields and normal fields
    // initialize fieldMap
    for (FieldMeta f : fields) {
      if (f.getField().isPk()) {
        pkFields.add(f);
      } else {
        normalFields.add(f);
      }
      fieldMap.put(f.getField(), f);
    }
  }

  /**
   * Get table name.
   * 
   * @return the tableName
   */
  public TableName getTableName() {
    return tableName;
  }

  /**
   * Get all field meta.
   * 
   * @return the fields
   */
  public List<FieldMeta> getFields() {
    return fields;
  }

  /**
   * Get field column names.
   * 
   * @return field column names
   */
  public List<String> getFieldColumnNames() {
    List<String> names = new ArrayList<String>();
    for (FieldMeta f : fields) {
      names.add(f.getColumnName());
    }
    return names;
  }

  /**
   * Get primary key fields.
   * 
   * @return primary key fields
   */
  public List<FieldMeta> getPkFields() {
    return pkFields;
  }

  /**
   * Get normal fields.
   * 
   * @return normal fields
   */
  public List<FieldMeta> getNormalFields() {
    return normalFields;
  }

  /**
   * Get field meta.
   * 
   * @param field field
   * @return some field meta or none
   */
  public Option<FieldMeta> getFieldMeta(MappedField<?> field) {
    return fieldMap.containsKey(field) ? new Some<FieldMeta>(
        fieldMap.get(field)) : new None<FieldMeta>();
  }

  /**
   * Get column name.
   * 
   * @param field
   * @return
   */
  public Option<String> getColumnName(MappedField<?> field) {
    return fieldMap.containsKey(field) ? new Some<String>(fieldMap.get(field)
        .getColumnName()) : new None<String>();
  }

  /**
   * Get column alias.
   * 
   * @param field field
   * @return some alias or none
   */
  public Option<String> getColumnAliasName(MappedField<?> field) {
    return fieldMap.containsKey(field) ? new Some<String>(
        createColumnAliasName(fieldMap.get(field).getColumnName()))
        : new None<String>();
  }

  public Map<String, FieldMeta> collectColumnAliasNames() {
    Map<String, FieldMeta> nameMap = new HashMap<String, FieldMeta>();
    for (FieldMeta meta : fields) {
      nameMap.put(createColumnAliasName(meta.getColumnName()).toLowerCase(),
          meta);
    }
    return nameMap;
  }

  private String createColumnAliasName(String columnName) {
    return tableName.getAlias() + "_" + columnName;
  }
}
