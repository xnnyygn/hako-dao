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
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.mapping.field.FieldMeta;
import org.hako.dao.mapping.field.MappedField;

/**
 * Entity meta builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class EntityMetaBuilder {

  private final Map<MappedField<?>, FieldMeta> fields =
      new HashMap<MappedField<?>, FieldMeta>();
  private Option<TableName> tableNameOpt = new None<TableName>();

  /**
   * Update table name and alias.
   * 
   * @param name table name
   * @param alias table alias
   * @return this
   */
  public EntityMetaBuilder updateTableName(String name, String alias) {
    tableNameOpt = new Some<TableName>(new TableName(name, alias));
    return this;
  }

  /**
   * Update field meta.
   * 
   * @param field
   * @param meta
   * @return this
   */
  public EntityMetaBuilder updateFieldMeta(MappedField<?> field, String columnName) {
    fields.put(field, new FieldMeta(columnName, field));
    return this;
  }

  /**
   * Build entity meta.
   * 
   * @return entity meta instance
   */
  public EntityMeta build() {
    return new EntityMeta(tableNameOpt.get(), new ArrayList<FieldMeta>(
        fields.values()));
  }

}
