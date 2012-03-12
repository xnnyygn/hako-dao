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

import org.hako.dao.mapper.annotation.FieldMeta;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.clause.select.table.TableFactory;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class EntityMeta {

  private final EntityName name;
  private final List<FieldMeta> fields;

  /**
   * Create.
   * 
   * @param name
   * @param fields
   */
  public EntityMeta(EntityName name, List<FieldMeta> fields) {
    super();
    this.name = name;
    this.fields = fields;
  }

  /**
   * Create table.
   * 
   * @param withAlias with alias
   * @return table
   */
  public Table createTable(boolean withAlias) {
    if (withAlias) {
      return TableFactory.createSimple(name.getTableName());
    }
    return TableFactory.createSimpleAkaTable(name.getTableName(),
        name.getAlias());
  }

  /**
   * Create selection of all fields.
   * 
   * @return selection
   */
  public Selection createSelectionOfAllFields() {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (FieldMeta f : fields) {
      builder.addTableColumnNameAka(name.getAlias(), f.getColumnName(),
          f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }

  public Condition createPkCondition(Object id, boolean withAlias) {
    // TODO count primary key fields
    // TODO get primary key fields
    throw new UnsupportedOperationException();
  }

}
