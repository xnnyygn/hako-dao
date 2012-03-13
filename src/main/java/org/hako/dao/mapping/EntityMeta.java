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
package org.hako.dao.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.selection.TableAliasAsteriskSelection;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.clause.select.table.Tables;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.TableColumnName;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.condition.MultipleConditionBuilder;
import org.hako.dao.sql.expression.value.Values;
import org.hako.util.object.ObjectUtils;

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
   * Create table with alias.
   * 
   * @return table
   */
  public Table createTable() {
    return createTable(true);
  }

  /**
   * Create table.
   * 
   * @param withAlias with alias
   * @return table
   */
  public Table createTable(boolean withAlias) {
    if (withAlias) {
      return Tables.createSimpleAkaTable(name.getTableName(), name.getAlias());
    }
    return Tables.createSimple(name.getTableName());
  }

  /**
   * Create selection of all fields.
   * 
   * @return selection
   */
  public Selection createSelectionOfAllFields() {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (FieldMeta f : fields) {
      builder.addExpressionAka(createColumnExpression(f, true),
          f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }

  /**
   * Create table alias asterisk selection. e.g {@code b.*}.
   * 
   * @return table alias asterisk selection
   * @see TableAliasAsteriskSelection
   */
  public Selection createAsteriskSelection() {
    return new TableAliasAsteriskSelection(name.getAlias());
  }

  /**
   * Create column expression with table alias.
   * 
   * @param propertyName
   * @return expression
   * @see #createColumnExpression(String, boolean)
   */
  public Expression createColumnExpression(String propertyName) {
    return createColumnExpression(propertyName, true);
  }

  /**
   * Create column expression.
   * 
   * @param propertyName
   * @param withAlias
   * @return expression
   */
  public Expression createColumnExpression(String propertyName,
      boolean withAlias) {
    for (FieldMeta f : fields) {
      if (f.getPropertyName().equals(propertyName)) {
        return createColumnExpression(f, withAlias);
      }
    }
    throw new IllegalArgumentException("no such property [" + propertyName
        + "] in entity [" + name.getTableName() + "]");
  }

  /**
   * Create column expression.
   * 
   * @param field
   * @param withAlias
   * @return column expression
   */
  private Expression createColumnExpression(FieldMeta field, boolean withAlias) {
    if (withAlias) {
      return new TableColumnName(name.getAlias(), field.getColumnName());
    }
    return new ColumnName(field.getColumnName());
  }

  /**
   * Create primary key condition with alias.
   * 
   * @param id
   * @return condition
   * @throws IllegalStateException
   */
  public Condition createPkCondition(Object id) throws IllegalStateException {
    return createPkCondition(id, true);
  }

  /**
   * Create primary key condition.
   * 
   * @param id
   * @param withAlias
   * @return condition
   * @throws IllegalStateException
   */
  public Condition createPkCondition(Object id, boolean withAlias)
      throws IllegalStateException {
    List<FieldMeta> pkFields = filterPkFields();
    switch (pkFields.size()) {
      case 0:
        throw new IllegalStateException("no primary key field");
      case 1:
        return Conditions.eq(
            createColumnExpression(pkFields.get(0), withAlias),
            Values.create(id));
      default:
        return createComplexPkCondition(ObjectUtils.getProperties(id), withAlias,
            pkFields);
    }
  }

  /**
   * Create complex primary key condition.
   * 
   * @param properties
   * @param withAlias
   * @param pkFields
   * @return condition
   */
  private Condition createComplexPkCondition(Map<String, Object> properties,
      boolean withAlias, List<FieldMeta> pkFields) {
    MultipleConditionBuilder builder = new MultipleConditionBuilder();
    for (FieldMeta f : pkFields) {
      String propertyName = f.getPropertyName();
      if (!properties.containsKey(propertyName)) {
        throw new IllegalArgumentException("property [" + propertyName
            + "] required");
      }
      builder.add(Conditions.eq(createColumnExpression(f, withAlias),
          Values.create(properties.get(propertyName))));
    }
    return builder.build();
  }

  /**
   * Create complex primary key condition.
   * 
   * @param properties
   * @param withAlias
   * @return condition
   */
  public Condition createComplexPkCondition(Map<String, Object> properties,
      boolean withAlias) {
    return createComplexPkCondition(properties, withAlias, filterPkFields());
  }

  /**
   * Filter primary key fields.
   * 
   * @return primary key fields
   */
  private List<FieldMeta> filterPkFields() {
    List<FieldMeta> fields = new ArrayList<FieldMeta>();
    for (FieldMeta f : this.fields) {
      if (f.isPk()) {
        fields.add(f);
      }
    }
    return fields;
  }

  /**
   * Get fields.
   * 
   * @return the fields
   */
  public List<FieldMeta> getFields() {
    return fields;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }
}
