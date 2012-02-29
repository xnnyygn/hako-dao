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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hako.dao.field.FieldName;

/**
 * Implicit entity builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ImplicitEntityBuilder {

  private String tableName;
  private final Map<String, FieldName> fieldNameMap =
      new HashMap<String, FieldName>();

  /**
   * Set table name.
   * 
   * @param tableName the tableName to set
   */
  public ImplicitEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }
  
  public ImplicitEntityBuilder addField(String name){
    return addField(name, name);
  }

  public ImplicitEntityBuilder addField(String columnName, String propertyName) {
    fieldNameMap.put(propertyName, new FieldName(columnName, propertyName));
    return this;
  }

  public EntityMeta build() {
    return new EntityMeta(tableName, new ArrayList<FieldName>(
        fieldNameMap.values()));
  }

}
