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

/**
 * Field.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Field<T> {

  private final String columnName;
  private final String propertyName;
  private final boolean pk;

  /**
   * Create.
   * 
   * @param name column name and property name
   * @param pk is primary key
   */
  public Field(String name, boolean pk) {
    this(name, name, pk);
  }

  /**
   * Create.
   * 
   * @param columnName
   * @param propertyName
   * @param pk is primary key?
   */
  public Field(String columnName, String propertyName, boolean pk) {
    super();
    this.columnName = columnName;
    this.propertyName = propertyName;
    this.pk = pk;
  }

  /**
   * @return the columnName
   */
  public String getColumnName() {
    return columnName;
  }

  /**
   * @return the propertyName
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * If field is primary key.
   * 
   * @return true if is, otherwise false
   */
  public boolean isPk() {
    return pk;
  }

}
