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
package org.hako.dao.field;

/**
 * Field name, consist of column name and property name.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class FieldName {

  private final String columnName;
  private final String prpertyName;

  /**
   * Create with same column and property name.
   * 
   * @param name
   */
  public FieldName(String name){
    this(name, name);
  }
  
  /**
   * Create.
   * 
   * @param columnName
   * @param prpertyName
   */
  public FieldName(String columnName, String prpertyName) {
    super();
    this.columnName = columnName;
    this.prpertyName = prpertyName;
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
   * Get property name.
   * 
   * @return the prpertyName
   */
  public String getPrpertyName() {
    return prpertyName;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FieldName [columnName=");
    builder.append(columnName);
    builder.append(", prpertyName=");
    builder.append(prpertyName);
    builder.append("]");
    return builder.toString();
  }
  
}
