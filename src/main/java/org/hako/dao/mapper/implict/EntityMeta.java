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
package org.hako.dao.mapper.implict;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hako.dao.field.FieldName;

/**
 * Implicit entity.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class EntityMeta {

  private final String tableName;
  private final List<FieldName> fieldNames;

  /**
   * Create.
   * 
   * @param tableName table name
   * @param fieldNames field names
   */
  public EntityMeta(String tableName, List<FieldName> fieldNames) {
    super();
    this.tableName = tableName;
    this.fieldNames = fieldNames;
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
   * Get field names.
   * 
   * @return the fieldNames
   */
  public List<FieldName> getFieldNames() {
    return fieldNames;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }

}
