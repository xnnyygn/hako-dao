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

import static org.apache.commons.lang.StringUtils.defaultIfBlank;
import static org.apache.commons.lang.StringUtils.uncapitalize;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.mapping.EntityName;
import org.hako.dao.mapping.FieldMeta;

/**
 * Entity mapper.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityMapper {

  private static final Log logger = LogFactory.getLog(EntityMapper.class);
  private DataDictionaryStrategy dataDictionaryStrategy =
      new DefaultDataDictionaryStrategy();

  /**
   * Map from class array.
   * 
   * @param classes
   * @return a map use class as key, entity meta as value
   * @see #map(List)
   */
  public Map<Class<?>, EntityMeta> map(Class<?>... classes) {
    return map(Arrays.asList(classes));
  }
  
  /**
   * Map class.
   * 
   * @param clazz
   * @return entity meta
   */
  public EntityMeta map(Class<?> clazz) {
    EntityName entityName = mapEntityName(clazz);
    List<FieldMeta> fields = findFields(clazz);
    return new EntityMeta(entityName, fields);
  }

  /**
   * Map from class list.
   * 
   * @param classes
   * @return a map use class as key, entity meta as value
   */
  public Map<Class<?>, EntityMeta> map(List<Class<?>> classes) {
    Map<Class<?>, EntityMeta> entityMetaMap =
        new HashMap<Class<?>, EntityMeta>();
    for (Class<?> clazz : classes) {
      entityMetaMap.put(clazz, map(clazz));
    }
    return entityMetaMap;
  }

  /**
   * Map entity name from class.
   * 
   * @param clazz
   * @return entity name
   * @see DataDictionaryStrategy#generateEntityNameAndAlias(Class)
   */
  private EntityName mapEntityName(Class<?> clazz) {
    if (logger.isDebugEnabled()) {
      logger.debug("try to map entity [" + clazz.getName() + "]");
    }
    return dataDictionaryStrategy.generateEntityNameAndAlias(clazz);
  }

  /**
   * Find field from class.
   * 
   * @param clazz
   * @return fields
   * @see #mapField(Method)
   */
  private List<FieldMeta> findFields(Class<?> clazz) {
    List<FieldMeta> fields = new ArrayList<FieldMeta>();
    for (Method method : clazz.getMethods()) {
      String methodName = method.getName();
      if (method.getParameterTypes().length == 0 && methodName.length() > 3
          && methodName.startsWith("get")
          && method.isAnnotationPresent(Field.class)) {
        fields.add(mapField(method));
      }
    }
    return fields;
  }

  /**
   * Map field.
   * 
   * @param method
   * @return field
   * @see DataDictionaryStrategy#generateColumnName(String)
   */
  private FieldMeta mapField(Method method) {
    String propertyName = uncapitalize(method.getName().substring(3));
    String defaultColumnName =
        dataDictionaryStrategy.generateColumnName(propertyName);
    String columnName =
        defaultIfBlank(method.getAnnotation(Field.class).columnName(),
            defaultColumnName);
    if (logger.isDebugEnabled()) {
      logger.debug("found field column name [" + columnName
          + "] property name [" + propertyName + "]");
    }
    return new FieldMeta(columnName, propertyName,
        method.isAnnotationPresent(Id.class));
  }

  /**
   * Update data dictionary strategy. Default is
   * {@link DefaultDataDictionaryStrategy}.
   * 
   * @param newDataDictionaryStrategy new strategy
   * @see DefaultDataDictionaryStrategy
   */
  public void updateDataDictionaryStrategy(
      DataDictionaryStrategy newDataDictionaryStrategy) {
    this.dataDictionaryStrategy = newDataDictionaryStrategy;
  }

}
