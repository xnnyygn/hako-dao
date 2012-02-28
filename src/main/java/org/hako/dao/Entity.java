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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.OptionUtils;
import org.hako.Some;
import org.hako.Tuple2;
import org.hako.TupleUtils;
import org.hako.dao.ListParams.OrderBy;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.sql.clause.delete.DeleteClause;
import org.hako.dao.sql.clause.insert.InsertClauseBuilder;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.select.selection.ExpressionSelection;
import org.hako.dao.sql.clause.select.selection.MultipleSelection;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.update.UpdateClauseBuilder;
import org.hako.dao.sql.expression.AsteriskExpression;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.function.FunctionFactory;
import org.hako.dao.sql.expression.value.ValueFactory;

/**
 * Base class of user model, providing useful methods to do SQL operations.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public abstract class Entity<T, PK> {

  private final DbClient client;
  private final String tableName;
  protected final List<Field<?>> pkFields;
  private final List<Field<?>> otherFields;
  private final List<Field<?>> allFields;

  /**
   * Create.
   * 
   * @param client
   * @param tableName
   * @param pkFields
   * @param otherFields
   * @throws IllegalArgumentException if {@code fields} is empty
   */
  public Entity(DbClient client, String tableName, List<Field<?>> fields)
      throws IllegalArgumentException {
    super();
    if (fields.isEmpty()) {
      throw new IllegalArgumentException("fields cannot be empty");
    }
    this.client = client;
    this.tableName = tableName;
    // split fields
    Tuple2<List<Field<?>>, List<Field<?>>> splitedFields =
        splitFieldsByIsPk(fields);
    this.pkFields = splitedFields._1;
    this.otherFields = splitedFields._2;
    this.allFields = fields;
  }

  private Tuple2<List<Field<?>>, List<Field<?>>> splitFieldsByIsPk(
      List<Field<?>> fields) {
    List<Field<?>> pkFields = new ArrayList<Field<?>>();
    List<Field<?>> otherFields = new ArrayList<Field<?>>();
    for (Field<?> f : fields) {
      if (f.isPk()) {
        pkFields.add(f);
      } else {
        otherFields.add(f);
      }
    }
    return TupleUtils.create(pkFields, otherFields);
  }

  /**
   * Convert properties to entity.
   * 
   * @param props properties
   * @return entity instance
   */
  protected abstract T convert(Map<String, Object> props);

  private Option<T> convert(Option<Map<String, Object>> propsOpt) {
    return propsOpt instanceof Some<?> ? OptionUtils.some(convert(propsOpt
        .get())) : new None<T>();
  }

  public List<T> convert(List<Map<String, Object>> propsList) {
    List<T> list = new ArrayList<T>();
    for (Map<String, Object> props : propsList) {
      list.add(convert(props));
    }
    return list;
  }


  /**
   * Get entity by id.
   * 
   * @param id id
   * @return some entity instance of none
   */
  public Option<T> get(PK id) {
    return get(id, allFields);
  }


  public Option<T> get(PK id, Field<?>... fields) {
    return get(id, Arrays.asList(fields));
  }

  public Option<T> get(PK id, List<Field<?>> fields) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(fields));
    builder.from(tableName);
    builder.where(createPkCondition(id));
    return convert(client.selectSingleRow(builder.toSelectClause()));
  }

  /**
   * Create multiple fields selections.
   * 
   * @param fields fields
   * @return multiple selection
   */
  private MultipleSelection createSelection(List<Field<?>> fields) {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (Field<?> f : fields) {
      builder.addExpressionAka(new ColumnName(f.getColumnName()),
          f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }

  private Condition createPkCondition(PK id) {
    if (pkFields.size() == 1) {
      return Conditions.eq(new ColumnName(pkFields.get(0).getPropertyName()),
          ValueFactory.create(id));
    }
    return createComplexPkCondition(id);
  }

  protected Condition createComplexPkCondition(PK id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Save entity.
   * 
   * @param props properties
   * @return id
   */
  @SuppressWarnings("unchecked")
  public PK save(Map<String, Object> props) {
    InsertClauseBuilder builder = new InsertClauseBuilder(tableName);
    for (Field<?> f : otherFields) {
      builder.addColumn(f.getColumnName());
      builder.addValue(ValueFactory.create(props.get(f.getPropertyName())));
    }
    return (PK) client.insertAndGet(builder.toInsertClause());
  }

  public List<T> list(ListParams params) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(allFields));
    builder.from(tableName);
    builder.limit(params.getMax(), params.getOffset());
    for (OrderBy orderBy : params.getOrderBys()) {
      builder.addOrderBy(new ColumnName(orderBy.getField().getPropertyName()),
          orderBy.isAsc());
    }
    return convert(client.selectMultipleRows(builder.toSelectClause()));
  }

  /**
   * Delete entity by id.
   * 
   * @param id id
   * @return deleted records
   * @see DbClient#delete(DeleteClause)
   */
  public int deleteById(PK id) {
    return client.delete(new DeleteClause(tableName, createPkCondition(id)));
  }

  public int update(Map<Field<?>, Object> props, PK id) {
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(tableName);
    Map<String, Expression> valueSetMap = new HashMap<String, Expression>();
    for (Map.Entry<Field<?>, Object> entry : props.entrySet()) {
      valueSetMap.put(entry.getKey().getColumnName(),
          ValueFactory.create(entry.getValue()));
    }
    builder.set(valueSetMap);
    builder.where(createPkCondition(id));
    return client.update(builder.toUpdateClause());
  }

  public int count() {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(new ExpressionSelection(FunctionFactory
        .count(new AsteriskExpression())));
    builder.from(tableName);
    return ((Number) client.selectObject(builder.toSelectClause()).get())
        .intValue();
  }

}
