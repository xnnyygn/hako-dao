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

/**
 * Table name.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class TableName {

  private final String name;
  private final String alias;

  /**
   * Create.
   * 
   * @param name
   * @param alias
   */
  public TableName(String name, String alias) {
    super();
    this.name = name;
    this.alias = alias;
  }

  /**
   * Get name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Get alias.
   * 
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TableName [name=");
    builder.append(name);
    builder.append(", alias=");
    builder.append(alias);
    builder.append("]");
    return builder.toString();
  }
  
}
