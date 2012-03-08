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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.expression.TableColumnName;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.Values;

/**
 * Entity manager.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManager {

  private final DbClient client;
  private final AnnotationMapper mapper = new AnnotationMapper();

  /**
   * Create with client.
   * 
   * @param client
   */
  public EntityManager(DbClient client) {
    super();
    this.client = client;
  }


  /**
   * Get entity instance by id.
   * 
   * @param clazz
   * @param id
   * @return some entity instance or none
   */
  public <T> Option<T> get(Class<T> clazz, Object id) {
    EntityMeta entityMeta = mapper.setUp(clazz);
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(allFields(entityMeta));
    builder.from(entityMeta.getTableName(), entityMeta.getTableAlias());
    builder.where(Conditions.eq(new TableColumnName(entityMeta.getTableAlias(),
        "id"), Values.create(id)));
    return setupEntity(clazz, client.selectSingleRow(builder.toSelectClause()));
  }

  private <T> Option<T> setupEntity(Class<T> clazz,
      Option<Map<String, Object>> propsOpt) {
    if (propsOpt.hasValue()) {
      try {
        return new Some<T>(copyPropertisIgnoreCase(propsOpt.get(),
            clazz.newInstance()));
      } catch (Exception e) {
        // TODO log error
      }
    }
    return new None<T>();
  }

  private <T> T copyPropertisIgnoreCase(Map<String, Object> props, T instance) {
    List<Method> setterMethods =
        filterSingleArgSetterMethods(instance.getClass());
    Map<String, Object> lowerCaseKeysProps = lowerCaseKeys(props);
    for (Method m : setterMethods) {
      String lowerCasePropertyName =
          StringUtils.uncapitalize(m.getName().substring(3)).toLowerCase();
      if (lowerCaseKeysProps.containsKey(lowerCasePropertyName)) {
        setProperty(m, instance, lowerCaseKeysProps.get(lowerCasePropertyName));
      }
    }
    return instance;
  }

  private <T> T setProperty(Method m, T instance, Object value) {
    try {
      m.invoke(instance, value);
    } catch (Exception e) {
      // log error
    }
    return instance;
  }


  private Map<String, Object> lowerCaseKeys(Map<String, Object> props) {
    Map<String, Object> destMap = new HashMap<String, Object>();
    for (Map.Entry<String, Object> e : props.entrySet()) {
      destMap.put(e.getKey().toLowerCase(), e.getValue());
    }
    return destMap;
  }

  private List<Method> filterSingleArgSetterMethods(Class<?> clazz) {
    List<Method> methods = new ArrayList<Method>();
    for (Method m : clazz.getMethods()) {
      String name = m.getName();
      if (name.startsWith("set") && name.length() > 3
          && m.getParameterTypes().length == 1) {
        methods.add(m);
      }
    }
    return methods;
  }

  private Selection allFields(EntityMeta entityMeta) {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (FieldMeta f : entityMeta.getFields()) {
      builder.addExpressionAka(new TableColumnName(entityMeta.getTableAlias(),
          f.getColumnName()), f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }
}
