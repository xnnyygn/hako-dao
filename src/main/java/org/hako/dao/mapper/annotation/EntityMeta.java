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
package org.hako.dao.mapper.annotation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityMeta {

  private final String tableName;
  private final String tableAlias;
  private final List<FieldMeta> fields;

  /**
   * Create.
   * 
   * @param tableName table name
   * @param tableAlias table alias
   * @param fields
   */
  public EntityMeta(String tableName, String tableAlias, List<FieldMeta> fields) {
    super();
    this.tableName = tableName;
    this.tableAlias = tableAlias;
    this.fields = fields;
  }

  /**
   * Get table name.
   * 
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Get table alias.
   * 
   * @return the tableAlias
   */
  public String getTableAlias() {
    return tableAlias;
  }

  /**
   * Get fields.
   * 
   * @return the fields
   */
  public List<FieldMeta> getFields() {
    return fields;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }

}
