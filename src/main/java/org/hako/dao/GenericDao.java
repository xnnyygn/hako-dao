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
import org.hako.dao.ListParams.OrderBy;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapping.field.SimpleField;
import org.hako.dao.restriction.Restriction;
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
import org.hako.dao.sql.expression.TableColumnName;
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
public abstract class GenericDao<T, PK> {

  protected final DbClient client;
  protected final Entity entity;

  /**
   * Create.
   * 
   * @param client
   * @param entity
   */
  public GenericDao(DbClient client, Entity entity) {
    super();
    this.client = client;
    this.entity = entity;
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
    return get(id, entity.getAllFields());
  }


  public Option<T> get(PK id, SimpleField<?>... fields) {
    return get(id, Arrays.asList(fields));
  }

  public Option<T> get(PK id, List<SimpleField<?>> fields) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(fields));
    builder.from(entity.getTableName(), entity.getTableAlias());
    builder.where(createPkCondition(id));
    return convert(client.selectSingleRow(builder.toSelectClause()));
  }

  /**
   * Create multiple fields selections.
   * 
   * @param fields fields
   * @return multiple selection
   */
  protected MultipleSelection createSelection(List<SimpleField<?>> fields) {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (SimpleField<?> f : fields) {
      builder.addExpressionAka(
          new TableColumnName(f.getTableAlias(), f.getColumnName()),
          f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }

  private Condition createPkCondition(PK id) {
    if (entity.getPkFields().size() == 1) {
      return Conditions.eq(new ColumnName(entity.getPkFields().get(0)
          .getPropertyName()), ValueFactory.create(id));
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
    InsertClauseBuilder builder =
        new InsertClauseBuilder(entity.getTableName());
    for (SimpleField<?> f : entity.getOtherFields()) {
      builder.addColumn(f.getColumnName());
      builder.addValue(ValueFactory.create(props.get(f.getPropertyName())));
    }
    return (PK) client.insertAndGet(builder.toInsertClause());
  }

  public List<T> list() {
    return listBy(new None<Restriction>(), new None<ListParams>());
  }

  public List<T> list(ListParams params) {
    return listBy(new None<Restriction>(), new Some<ListParams>(params));
  }

  public List<T> listBy(Restriction restriction) {
    return listBy(new Some<Restriction>(restriction), new None<ListParams>());
  }

  public List<T> listBy(ListParams params) {
    return listBy(new None<Restriction>(), new Some<ListParams>(params));
  }

  public List<T> listBy(Restriction restriction, ListParams params) {
    return listBy(new Some<Restriction>(restriction), new Some<ListParams>(
        params));
  }

  private List<T> listBy(Option<Restriction> rOpt, Option<ListParams> lpOpt) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(entity.getAllFields()));
    builder.from(entity.getTableName(), entity.getTableAlias());
    // handle where
    if (rOpt.hasValue()) {
      builder.where(rOpt.get().toCondition());
    }
    // handle limit and order by
    if (lpOpt.hasValue()) {
      ListParams params = lpOpt.get();
      builder.limit(params.getMax(), params.getOffset());
      for (OrderBy orderBy : params.getOrderBys()) {
        builder.addOrderBy(
            new ColumnName(orderBy.getField().getPropertyName()),
            orderBy.isAsc());
      }
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
    return client.delete(new DeleteClause(entity.getTableName(),
        createPkCondition(id)));
  }

  public int update(Map<SimpleField<?>, Object> props, PK id) {
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(entity.getTableName());
    Map<String, Expression> valueSetMap = new HashMap<String, Expression>();
    for (Map.Entry<SimpleField<?>, Object> entry : props.entrySet()) {
      valueSetMap.put(entry.getKey().getColumnName(),
          ValueFactory.create(entry.getValue()));
    }
    builder.set(valueSetMap);
    builder.where(createPkCondition(id));
    return client.update(builder.toUpdateClause());
  }

  /**
   * Count entity.
   * 
   * @return count
   */
  public int count() {
    return countBy(new None<Restriction>());
  }

  public int countBy(Restriction restriction) {
    return countBy(new Some<Restriction>(restriction));
  }

  private int countBy(Option<Restriction> rOption) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(new ExpressionSelection(FunctionFactory
        .count(new AsteriskExpression())));
    builder.from(entity.getTableName(), entity.getTableAlias());
    if (rOption.hasValue()) {
      builder.where(rOption.get().toCondition());
    }
    return ((Number) client.selectObject(builder.toSelectClause()).get())
        .intValue();
  }

  public Option<T> findBy(Restriction restriction) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(entity.getAllFields()));
    builder.from(entity.getTableName(), entity.getTableAlias());
    builder.where(restriction.toCondition());
    return convert(client.selectSingleRow(builder.toSelectClause()));
  }

}
