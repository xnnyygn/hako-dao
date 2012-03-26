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

import org.hako.dao.mapping.EntityName;

/**
 * Default data dictionary strategy.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class DefaultDataDictionaryStrategy implements DataDictionaryStrategy {

  /**
   * Use lower case of class simple name as default table name and alias. You
   * can override them by specify table name and alias in annotation entity.
   */
  public EntityName generateEntityNameAndAlias(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Entity.class)) {
      throw new IllegalArgumentException("class must with Entity annotation");
    }
    Entity entityAnno = clazz.getAnnotation(Entity.class);
    String defaultTableName = clazz.getSimpleName().toLowerCase();
    String tableName = defaultIfBlank(entityAnno.tableName(), defaultTableName);
    String alias = defaultIfBlank(entityAnno.tableAlias(), tableName);
    return new EntityName(tableName, alias);
  }

  /**
   * Dash separated style column name.
   */
  public String generateColumnName(String propertyName) {
    StringBuilder builder = new StringBuilder();
    for (char c : propertyName.toCharArray()) {
      if (Character.isUpperCase(c)) {
        builder.append('_').append(Character.toLowerCase(c));
      } else {
        builder.append(c);
      }
    }
    return builder.toString();
  }

}
