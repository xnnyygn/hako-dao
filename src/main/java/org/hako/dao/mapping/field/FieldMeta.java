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
package org.hako.dao.mapping.field;


/**
 * Mapped field.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class FieldMeta {

  private final String columnName;
  private final String propertyName;
  private final MappedField<?> field;

  /**
   * Create.
   * 
   * @param columnName
   * @param propertyName
   * @param field
   */
  public FieldMeta(String columnName, String propertyName, MappedField<?> field) {
    super();
    this.columnName = columnName;
    this.propertyName = propertyName;
    this.field = field;
  }

  /**
   * Get column name.
   * 
   * @return the columnName
   */
  public String getColumnName() {
    return columnName;
  }

  /**
   * Get mapped field.
   * 
   * @return field
   */
  public MappedField<?> getField() {
    return field;
  }

  /**
   * Get property name.
   * 
   * @return property name
   */
  public String getPropertyName() {
    return propertyName;
  }

  @Override
  public String toString() {
    return "FieldMeta [columnName=" + columnName + ", propertyName="
        + propertyName + ", field=" + field + "]";
  }

}