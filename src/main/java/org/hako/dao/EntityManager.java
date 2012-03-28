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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hako.Option;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapper.EntityFactory;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.mapping.FieldMeta;
import org.hako.dao.restriction.Restriction;
import org.hako.dao.sql.clause.delete.DeleteClauseBuilder;
import org.hako.dao.sql.clause.insert.InsertClauseBuilder;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.update.UpdateClauseBuilder;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.Conditions;
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
public class EntityManager {

  private final DbClient client;
  private final EntityMappings entityMappings;

  /**
   * Create.
   * 
   * @param client
   * @param entityMappings
   */
  public EntityManager(DbClient client, EntityMappings entityMappings) {
    super();
    this.client = client;
    this.entityMappings = entityMappings;
  }

  /**
   * Get bean factory by class.
   * 
   * @param clazz
   * @return entity factory for class
   */
  private <T> EntityFactory<T> getBeanFactory(Class<T> clazz) {
    return BeanFactoryFlyweight.getBeanFactory(clazz);
  }

  /**
   * Get instance of specified entity class by id.
   * 
   * @param clazz
   * @param id
   * @return some instance or none
   * @see BeanFactoryFlyweight#getBeanFactory(Class)
   */
  public <T> Option<T> get(Class<T> clazz, Serializable id) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    builder.where(entityMeta.createPkCondition(id));
    return getBeanFactory(clazz).create(
        client.selectSingleRow(builder.toSelectClause()));
  }

  /**
   * Count entities in tables.
   * 
   * @param clazz
   * @return count
   * @see #countBy(Class, Restriction...)
   */
  public int count(Class<?> clazz) {
    return countBy(clazz);
  }

  /**
   * Count entities with restrictions.
   * 
   * @param clazz
   * @param restrictions
   * @return count
   */
  public int countBy(Class<?> clazz, Restriction... restrictions) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(entityMeta.createTable());
    if (restrictions.length > 0) {
      builder.where(createConditions(Arrays.asList(restrictions), entityMeta));
    }
    return client.selectObject(builder.toSelectClause(), Number.class).get()
        .intValue();
  }

  /**
   * Find instance by restrictions.
   * 
   * @param clazz
   * @param restrictions
   * @return some instance or none
   */
  public <T> Option<T> findBy(Class<T> clazz, Restriction... restrictions) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    builder.where(createConditions(Arrays.asList(restrictions), entityMeta));
    return getBeanFactory(clazz).create(
        client.selectSingleRow(builder.toSelectClause()));
  }

  /**
   * Create condition from restrictions.
   * 
   * @param restrictions
   * @param entityMeta entity meta to convert restriction to condition
   * @return condition
   * @see Restriction#toCondition(EntityMeta, boolean)
   * @see Conditions#from(List)
   */
  private Condition createConditions(List<Restriction> restrictions,
      EntityMeta entityMeta) {
    List<Condition> conditions = new ArrayList<Condition>();
    for (Restriction restriction : restrictions) {
      conditions.add(restriction.toCondition(entityMeta, true));
    }
    return Conditions.from(conditions);
  }

  /**
   * List instances of class by list parameters.
   * 
   * @param clazz
   * @param params list parameters
   * @return instances
   * @see #listBy(Class, ListParams, Restriction...)
   */
  public <T> List<T> list(Class<T> clazz, ListParams params) {
    return listBy(clazz, params);
  }

  /**
   * List instance of class by list parameters and restrictions.
   * 
   * @param clazz
   * @param params
   * @param restrictions
   * @return instances
   */
  public <T> List<T> listBy(Class<T> clazz, ListParams params,
      Restriction... restrictions) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createSelectionOfAllFields());
    builder.from(entityMeta.createTable());
    if (restrictions.length > 0) {
      builder.where(createConditions(Arrays.asList(restrictions), entityMeta));
    }
    builder.limit(params.getMax(), params.getOffset());
    builder.addOrderBy(params.toMultipleOrderBy());
    return getBeanFactory(clazz).create(
        client.selectMultipleRows(builder.toSelectClause()));
  }

  /**
   * Execute query.
   * 
   * @param callback
   * @return result from callback
   */
  public <T> T executeQuery(QueryCallback<T> callback) {
    return callback.doQuery(client, entityMappings);
  }

  /**
   * Save entity.
   * 
   * @param bean
   * @return generated key or updated record count
   * @see #save(Class, Map)
   */
  public Object save(Object bean) {
    // TODO return persisted entity
    return save(bean.getClass(), ObjectUtils.getProperties(bean));
  }

  /**
   * Save entity of specified class with properties.
   * 
   * @param clazz
   * @param properties
   * @return generated key or updated record count
   */
  // TODO support auto generated
  public Object save(Class<?> clazz, Map<String, Object> properties) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    InsertClauseBuilder builder = new InsertClauseBuilder();
    builder.insertInto(entityMeta.createTable(false));
    for (FieldMeta field : entityMeta.getFields()) {
      String name = field.getPropertyName();
      if (properties.containsKey(name)) {
        builder.add(field.getColumnName(), properties.get(name));
      } else {
        // TODO add default value to field meta
        builder.add(field.getColumnName(), Values.NULL);
      }
    }
    if (entityMeta.isKeyGenerated()) {
      return client.insertAndGet(builder.toInsertClause());
    }
    return client.insert(builder.toInsertClause());
  }

  public void update(Class<?> clazz, Map<String, Object> properties,
      Serializable id) {
  }

  /**
   * Update entity.
   * 
   * @param clazz
   * @param properties
   * @param pk primary key
   */
  public void update(Class<?> clazz, Map<String, Object> properties, Object pk) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(entityMeta.createTable());
    for (FieldMeta field : entityMeta.getFields()) {
      String name = field.getPropertyName();
      if (properties.containsKey(name)) {
        builder.set(field.getColumnName(), properties.get(name));
      }
    }
    // TODO add support to no primary key limit
    builder.where(entityMeta.createPkCondition(pk, false));
    client.update(builder.toUpdateClause());
  }

  /**
   * Delete entity by id.
   * 
   * @param clazz
   * @param pk
   */
  public void deleteById(Class<?> clazz, Object pk) {
    EntityMeta entityMeta = entityMappings.findByClass(clazz);
    DeleteClauseBuilder builder = new DeleteClauseBuilder();
    builder.deleteFrom(entityMeta.createTable(false));
    builder.where(entityMeta.createPkCondition(pk, false));
    client.delete(builder.toDeleteClause());
  }

}
