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
import org.hako.dao.util.OptionUtils;

/**
 * Base class of user model, providing useful methods to do SQL operations.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class GenericDao {

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
  private Record convert(Map<String, Object> props) {
    Map<String, FieldMeta> columnAliasNames =
        entity.getColumnAliasNameMetaMap();
    Map<FieldMeta, Object> metaValueMap = new HashMap<FieldMeta, Object>();
    for (Map.Entry<String, Object> p : props.entrySet()) {
      String lowerCaseKey = p.getKey().toLowerCase();
      if (columnAliasNames.containsKey(lowerCaseKey)) {
        FieldMeta meta = columnAliasNames.get(lowerCaseKey);
        metaValueMap.put(meta, p.getValue());
      }
    }
    return new Record(metaValueMap);
  }

  /**
   * Convert properties option to record option. If {@code propsOpt} is
   * {@code None}, just return {@code None}, otherwise call
   * {@link #convert(Map)} to convert to record.
   * 
   * @param propsOpt properties option
   * @return record option
   * @see #convert(Map)
   */
  private Option<Record> convert(Option<Map<String, Object>> propsOpt) {
    if (propsOpt.hasValue()) {
      return OptionUtils.some(convert(propsOpt.get()));
    }
    return new None<Record>();
  }

  /**
   * Convert properties list to records. {@link #convert(Map)} do the real
   * convert work.
   * 
   * @param propsList properties list
   * @return records
   * @see #convert(Map)
   */
  private List<Record> convert(List<Map<String, Object>> propsList) {
    List<Record> list = new ArrayList<Record>();
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
  public Option<Record> get(Object id) {
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

  private Condition createPkCondition(Object id, boolean withTableAlias) {
    int count = entity.getPkFields().size();
    if (count == 1) {
      String columnName = entity.getPkFields().get(0).getColumnName();
      Expression pk =
          withTableAlias ? new TableColumnName(
              entity.getTableName().getAlias(), columnName) : new ColumnName(
              columnName);
      return Conditions.eq(pk, ValueFactory.create(id));
    }
    return createComplexPkCondition(id);
  }

  protected Condition createComplexPkCondition(Object id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Save entity.
   * 
   * @param props properties
   * @return id
   */
  public Object save(Map<MappedField<?>, Object> props) {
    InsertClauseBuilder builder =
        new InsertClauseBuilder(entity.getTableName().getName());
    for (FieldMeta f : entity.getNormalFields()) {
      builder.addColumn(f.getColumnName());
      builder.addValue(ValueFactory.create(props.get(f.getField())));
    }
    return client.insertAndGet(builder.toInsertClause());
  }

  public List<Record> list() {
    return listBy(new None<Restriction>(), new None<ListParams>());
  }

  public List<Record> list(ListParams params) {
    return listBy(new None<Restriction>(), new Some<ListParams>(params));
  }

  public List<Record> listBy(Restriction restriction) {
    return listBy(new Some<Restriction>(restriction), new None<ListParams>());
  }

  public List<Record> listBy(ListParams params) {
    return listBy(new None<Restriction>(), new Some<ListParams>(params));
  }

  public List<Record> listBy(Restriction restriction, ListParams params) {
    return listBy(new Some<Restriction>(restriction), new Some<ListParams>(
        params));
  }

  private List<Record> listBy(Option<Restriction> rOpt, Option<ListParams> lpOpt) {
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
  public int deleteById(Object id) {
    return client.delete(new DeleteClause(entity.getTableName().getName(),
        createPkCondition(id, false)));
  }

  /**
   * Update entity.
   * 
   * @param props properties
   * @param id primary key
   * @return updated record
   * @see DbClient#update(org.hako.dao.sql.clause.update.UpdateClause)
   */
  public int update(Map<MappedField<?>, Object> props, Object id) {
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(entity.getTableName().getName());
    Map<String, Expression> valueSetMap = new HashMap<String, Expression>();
    for (Map.Entry<MappedField<?>, Object> entry : props.entrySet()) {
      valueSetMap.put(entity.getColumnName(entry.getKey()).get(),
          ValueFactory.create(entry.getValue()));
    }
    builder.set(valueSetMap);
    builder.where(createPkCondition(id, false));
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

  public Option<Record> findBy(Restriction restriction) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(entity.getFields()));
    TableName tableName = entity.getTableName();
    builder.from(tableName.getName(), tableName.getAlias());
    builder.where(restriction.toCondition(entity));
    return convert(client.selectSingleRow(builder.toSelectClause()));
  }
}
