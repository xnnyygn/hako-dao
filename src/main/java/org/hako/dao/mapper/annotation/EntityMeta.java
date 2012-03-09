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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.clause.select.table.TableFactory;
import org.hako.dao.sql.expression.TableColumnName;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.ConditionBuilder;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;
import org.hako.dao.sql.expression.value.Values;
import org.hako.util.BeanUtils;

/**
 * Entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityMeta {

  private final String tableName;
  private final String tableAlias;
  private final Fields fields;

  /**
   * Create.
   * 
   * @param tableName table name
   * @param tableAlias table alias
   * @param fields
   */
  public EntityMeta(String tableName, String tableAlias, List<FieldMeta> fields) {
    super();
    this.tableName = tableName;
    this.tableAlias = tableAlias;
    this.fields = new Fields(fields);
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
   * Get table alias.
   * 
   * @return the tableAlias
   */
  public String getTableAlias() {
    return tableAlias;
  }

  /**
   * Create table.
   * 
   * @return table
   * @see TableFactory#createSimpleAkaTable(String, String)
   */
  public Table createTable() {
    return TableFactory.createSimpleAkaTable(tableName, tableAlias);
  }

  /**
   * Create selection for all fields.
   * 
   * @return selection
   */
  public Selection createAllFieldsSelection() {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (FieldMeta f : fields.getAllFields()) {
      builder.addExpressionAka(
          new TableColumnName(tableAlias, f.getColumnName()),
          f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }

  /**
   * Create primary key condition.
   * 
   * @param id id
   * @return condition
   * @throws IllegalStateException if no primary key
   */
  public Condition createPkCondition(Object id) throws IllegalStateException {
    List<FieldMeta> pkFields = fields.getPkFields();
    int pkFieldCount = pkFields.size();
    if (pkFieldCount == 0) {
      throw new IllegalStateException("no primary key");
    } else if (pkFieldCount == 1) {
      return Conditions.eq(new TableColumnName(tableAlias, pkFields.get(0)
          .getColumnName()), Values.create(id));
    }
    return createComplexPkConditions(BeanUtils.getProperties(id), pkFields);
  }

  /**
   * Create complex primary key conditions.
   * 
   * @param id id object
   * @param pkFields primary key fields
   * @return condition
   */
  public MultipleAndCondition createComplexPkConditions(
      Map<String, Object> props) {
    return createComplexPkConditions(props, fields.getPkFields());
  }

  /**
   * Create complex primary key conditions.
   * 
   * @param id id object
   * @param pkFields primary key fields
   * @return condition
   */
  public MultipleAndCondition createComplexPkConditions(
      Map<String, Object> props, List<FieldMeta> pkFields) {
    ConditionBuilder builder = new ConditionBuilder();
    for (FieldMeta f : pkFields) {
      String propertyName = f.getPropertyName();
      if (!props.containsKey(propertyName)) {
        throw new IllegalArgumentException("property [" + propertyName
            + "] is required");
      }
      builder.add(Conditions.eq(
          new TableColumnName(tableAlias, f.getColumnName()),
          Values.create(props.get(propertyName))));
    }
    return builder.build();
  }

  /**
   * Create table column name by property name.
   * <p>
   * Table column name consists of table alias and column name. Column name was
   * retrieved from {@link Fields#getAllFields()} by property name.
   * </p>
   * 
   * @param propertyName
   * @return table column name
   * @throws IllegalArgumentException if no column name found by property name
   * @see #getColumnNameOfProperty(String)
   */
  public TableColumnName createTableColumnName(String propertyName)
      throws IllegalArgumentException {
    Option<String> columnNameOpt = getColumnNameOfProperty(propertyName);
    if (!columnNameOpt.hasValue()) {
      throw new IllegalArgumentException("no such property [" + propertyName
          + "] of entity");
    }
    return new TableColumnName(tableAlias, columnNameOpt.get());
  }

  /**
   * Get column name of property by property name.
   * 
   * @param propertyName property name
   * @return some column name or none
   */
  public Option<String> getColumnNameOfProperty(String propertyName) {
    for (FieldMeta f : fields.getAllFields()) {
      if (f.getPropertyName().equals(propertyName)) {
        return new Some<String>(f.getColumnName());
      }
    }
    return new None<String>();
  }

  /**
   * Get fields that not auto generated.
   * 
   * @return fields
   * @see Fields#getOtherFields()
   */
  public List<FieldMeta> getNotGeneratedFields() {
    // TODO FIXME
    return fields.getOtherFields();
  }

  // TODO javadoc
  public List<FieldMeta> getPkFields() {
    return fields.getPkFields();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }

}
