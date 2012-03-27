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
import org.hako.dao.restriction.Restriction;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.function.Functions;

/**
 * Entity manager.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManager {

  private final DbClient client;
  // TODO wrap entity meta map with some class
  private final Map<Class<?>, EntityMeta> entityMetaMap;

  /**
   * Create.
   * 
   * @param client
   * @param entityMetaMap
   */
  public EntityManager(DbClient client, Map<Class<?>, EntityMeta> entityMetaMap) {
    super();
    this.client = client;
    this.entityMetaMap = entityMetaMap;
  }

  /**
   * Get entity meta by class. If not found, throw
   * {@link MappingNotFoundException}.
   * 
   * @param clazz
   * @return entity meta of class
   * @throws MappingNotFoundException if entity meta not found
   * @see #entityMetaMap
   */
  private EntityMeta getEntityMetaByClass(Class<?> clazz)
      throws MappingNotFoundException {
    if (!entityMetaMap.containsKey(clazz)) {
      throw new MappingNotFoundException("mapping for class ["
          + clazz.getName() + "] not found");
    }
    return entityMetaMap.get(clazz);
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
    EntityMeta entityMeta = getEntityMetaByClass(clazz);
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
   */
  public int count(Class<?> clazz) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(getEntityMetaByClass(clazz).createTable(false));
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
    EntityMeta entityMeta = getEntityMetaByClass(clazz);
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

}
