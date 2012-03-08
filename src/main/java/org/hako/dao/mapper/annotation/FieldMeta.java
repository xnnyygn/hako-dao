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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Field meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class FieldMeta {

  private final String propertyName;
  private final String columnName;
  private final boolean pk;

  /**
   * Create.
   * 
   * @param propertyName
   * @param columnName
   * @param pk
   */
  public FieldMeta(String propertyName, String columnName, boolean pk) {
    super();
    this.propertyName = propertyName;
    this.columnName = columnName;
    this.pk = pk;
  }

  /**
   * Is primary key?
   * 
   * @return the pk
   */
  public boolean isPk() {
    return pk;
  }


  /**
   * Get property name.
   * 
   * @return the propertyName
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Get column name.
   * 
   * @return the columnName
   */
  public String getColumnName() {
    return columnName;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }

}
