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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.OptionUtils;
import org.hako.Some;
import org.hako.dao.ListParams.OrderBy;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapper.StaticMapper;
import org.hako.dao.mapping.entity.EntityMeta;
import org.hako.dao.mapping.entity.TableName;
import org.hako.dao.mapping.field.FieldMeta;
import org.hako.dao.mapping.field.MappedField;
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
  protected final EntityMeta entity;

  /**
   * Create.
   * 
   * @param client
   * @param entity
   */
  public GenericDao(DbClient client, Class<?> entityClass) {
    super();
    this.client = client;
    this.entity = new StaticMapper().setup(entityClass);
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
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(entity.getFields()));
    TableName tableName = entity.getTableName();
    builder.from(tableName.getName(), tableName.getAlias());
    builder.where(createPkCondition(id, true));
    return convert(client.selectSingleRow(builder.toSelectClause()));
  }

  /**
   * Create multiple fields selections.
   * 
   * @param fields fields
   * @return multiple selection
   */
  protected MultipleSelection createSelection(List<FieldMeta> fields) {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (FieldMeta f : fields) {
      builder.addExpressionAka(new TableColumnName(entity.getTableName()
          .getAlias(), f.getColumnName()), entity.getTableName().getAlias()
          + "_" + f.getColumnName());
    }
    return builder.toMultipleSelection();
  }

  private Condition createPkCondition(PK id, boolean withTableAlias) {
    if (entity.getPkFields().size() == 1) {
      String columnName = entity.getPkFields().get(0).getColumnName();
      Expression pk =
          withTableAlias ? new TableColumnName(
              entity.getTableName().getAlias(), columnName) : new ColumnName(
              columnName);
      return Conditions.eq(pk, ValueFactory.create(id));
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
  public PK save(Map<MappedField<?>, Object> props) {
    InsertClauseBuilder builder =
        new InsertClauseBuilder(entity.getTableName().getName());
    for (FieldMeta f : entity.getNormalFields()) {
      builder.addColumn(f.getColumnName());
      builder.addValue(ValueFactory.create(props.get(f.getField())));
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
    builder.select(createSelection(entity.getFields()));
    TableName tableName = entity.getTableName();
    builder.from(tableName.getName(), tableName.getAlias());
    // handle where
    if (rOpt.hasValue()) {
      builder.where(rOpt.get().toCondition(entity));
    }
    // handle limit and order by
    if (lpOpt.hasValue()) {
      ListParams params = lpOpt.get();
      builder.limit(params.getMax(), params.getOffset());
      for (OrderBy orderBy : params.getOrderBys()) {
        Option<String> nameOpt = entity.getColumnAliasName(orderBy.getField());
        if (nameOpt.hasValue()) {
          builder.addOrderBy(new ColumnName(nameOpt.get()), orderBy.isAsc());
        }
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
    return client.delete(new DeleteClause(entity.getTableName().getName(),
        createPkCondition(id, false)));
  }

  public int update(Map<SimpleField<?>, Object> props, PK id) {
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(entity.getTableName().getName());
    Map<String, Expression> valueSetMap = new HashMap<String, Expression>();
    for (Map.Entry<SimpleField<?>, Object> entry : props.entrySet()) {
      valueSetMap.put(entry.getKey().getColumnName(),
          ValueFactory.create(entry.getValue()));
    }
    builder.set(valueSetMap);
    builder.where(createPkCondition(id, true));
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
    TableName tableName = entity.getTableName();
    builder.from(tableName.getName(), tableName.getAlias());
    if (rOption.hasValue()) {
      builder.where(rOption.get().toCondition(entity));
    }
    return ((Number) client.selectObject(builder.toSelectClause()).get())
        .intValue();
  }

  public Option<T> findBy(Restriction restriction) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(entity.getFields()));
    TableName tableName = entity.getTableName();
    builder.from(tableName.getName(), tableName.getAlias());
    builder.where(restriction.toCondition(entity));
    return convert(client.selectSingleRow(builder.toSelectClause()));
  }
}
