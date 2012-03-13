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
package org.hako.dao.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hako.Option;
import org.hako.dao.ListParams;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.mapping.FieldMeta;
import org.hako.dao.restriction.Restriction;
import org.hako.dao.sql.clause.delete.DeleteClauseBuilder;
import org.hako.dao.sql.clause.insert.InsertClauseBuilder;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.update.UpdateClauseBuilder;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.function.Functions;
import org.hako.dao.sql.expression.value.Values;
import org.hako.util.object.ObjectUtils;

/**
 * Entity manager.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManager<T, PK> {

  private final DbClient client;
  private final EntityMeta entityMeta;
  private final EntityFactory<T> entityFactory;

  /**
   * Create with client.
   * 
   * @param client
   * @param entityClass entity class
   */
  public EntityManager(DbClient client, Class<T> entityClass) {
    super();
    this.client = client;
    this.entityMeta = new AnnotationMapper().setUp(entityClass);
    this.entityFactory = new EntityFactory<T>(entityClass);
  }

  /**
   * Get entity instance by id.
   * 
   * @param clazz
   * @param id
   * @return some entity instance or none
   */
  public Option<T> get(PK id) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    builder.where(entityMeta.createPkCondition(id));
    return entityFactory
        .create(client.selectSingleRow(builder.toSelectClause()));
  }

  /**
   * Count entity.
   * 
   * @return entity count
   */
  public int count() {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(entityMeta.createTable());
    return ((Number) client.selectObject(builder.toSelectClause()).get())
        .intValue();
  }

  /**
   * Find by restrictions.
   * 
   * @param restrictions
   * @return some entity or none
   */
  public Option<T> findBy(Restriction... restrictions) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    builder.where(createConditions(restrictions));
    return entityFactory
        .create(client.selectSingleRow(builder.toSelectClause()));
  }

  private List<Condition> createConditions(Restriction... restrictions) {
    List<Condition> conditions = new ArrayList<Condition>();
    for (Restriction r : restrictions) {
      conditions.add(r.toCondition(entityMeta, true));
    }
    return conditions;
  }

  /**
   * List entities.
   * 
   * @param params
   * @return this
   */
  public List<T> list(ListParams params) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    builder.addOrderBy(params.toMultipleOrderBy());
    builder.limit(params.getMax(), params.getOffset());
    return entityFactory.create(client.selectMultipleRows(builder
        .toSelectClause()));
  }

  /**
   * Save instance.
   * 
   * @param bean
   * @return count of updated record
   */
  public int save(T bean) {
    return save(ObjectUtils.getProperties(bean));
  }

  /**
   * Save with properties.
   * 
   * @param props properties
   * @return count of updated record
   */
  public int save(Map<String, Object> props) {
    InsertClauseBuilder builder = new InsertClauseBuilder();
    builder.insertInto(entityMeta.createTable(false));
    for (FieldMeta f : entityMeta.getFields()) {
      String key = f.getPropertyName();
      if (props.containsKey(key)) {
        builder.add(f.getColumnName(), props.get(key));
      } else {
        builder.add(f.getColumnName(), Values.NULL);
      }
    }
    return client.insert(builder.toInsertClause());
  }

  /**
   * Update instance.
   * 
   * @param instance
   * @return count of updated entity, usually be {@code 1}
   */
  public int update(T instance) {
    return update(ObjectUtils.getProperties(instance));
  }

  public int update(Map<String, Object> props) {
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(entityMeta.createTable());
    // TODO not primary key fields
    for (FieldMeta f : entityMeta.getFields()) {
      String key = f.getPropertyName();
      if (props.containsKey(key)) {
        builder.set(f.getColumnName(), props.get(key));
      }
    }
    builder.where(entityMeta.createComplexPkCondition(props, false));
    return client.update(builder.toUpdateClause());
  }

  /**
   * Count by restrictions.
   * 
   * @param eq
   * @return
   */
  public int countBy(Restriction... restrictions) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(entityMeta.createTable());
    builder.where(createConditions(restrictions));
    return ((Number) client.selectObject(builder.toSelectClause()).get())
        .intValue();
  }

  /**
   * List entity by restrictions.
   * 
   * @param listParams
   * @param restrictions
   * @return entities
   */
  public List<T> listBy(ListParams listParams, Restriction... restrictions) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    builder.where(createConditions(restrictions));
    builder.addOrderBy(listParams.toMultipleOrderBy());
    builder.limit(listParams.getMax(), listParams.getOffset());
    return entityFactory.create(client.selectMultipleRows(builder
        .toSelectClause()));
  }

  /**
   * Delete entity.
   * 
   * @param id
   * @return count of deleted records
   */
  public int delete(PK id) {
    DeleteClauseBuilder builder = new DeleteClauseBuilder();
    builder.deleteFrom(entityMeta.createTable(false));
    builder.where(entityMeta.createPkCondition(id, false));
    return client.delete(builder.toDeleteClause());
  }

}
