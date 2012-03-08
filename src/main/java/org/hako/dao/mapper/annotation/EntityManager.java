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

import org.hako.Option;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.expression.function.Functions;

/**
 * Entity manager.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManager<T> {

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
  public Option<T> get(Object id) {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityMeta.createAllFieldsSelection());
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

}
