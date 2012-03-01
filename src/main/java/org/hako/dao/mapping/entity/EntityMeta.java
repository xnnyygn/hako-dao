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

import java.util.List;

import org.hako.dao.mapping.field.FieldMeta;

/**
 * Entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class EntityMeta {

  private final TableName tableName;
  private final List<FieldMeta> fields;

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
  }

  /**
   * @return the tableName
   */
  public TableName getTableName() {
    return tableName;
  }

  /**
   * @return the fields
   */
  public List<FieldMeta> getFields() {
    return fields;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("EntityMeta [tableName=");
    builder.append(tableName);
    builder.append(", fields=");
    builder.append(fields);
    builder.append("]");
    return builder.toString();
  }
  
}
