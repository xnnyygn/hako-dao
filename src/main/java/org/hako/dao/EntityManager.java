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
import java.util.Map;

import org.hako.Option;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import static org.hako.dao.BeanFactoryFlyweight.getBeanFactory;

/**
 * Entity manager.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManager {

  private final DbClient client;
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

}
