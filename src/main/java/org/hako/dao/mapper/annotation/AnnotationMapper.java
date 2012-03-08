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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Annotation mapper.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class AnnotationMapper {

  public EntityMeta setUp(Class<?> clazz) {
    // setup table name and alias
    if (!clazz.isAnnotationPresent(Entity.class)) {
      throw new IllegalArgumentException("class must with Entity annotation");
    }
    Entity entityAnno = clazz.getAnnotation(Entity.class);
    String tableName =
        StringUtils.defaultIfBlank(entityAnno.tableName(),
            clazz.getSimpleName());
    String tableAlias =
        StringUtils.defaultIfBlank(entityAnno.tableAlias(), tableName);
    // setup fields
    List<FieldMeta> fields = new ArrayList<FieldMeta>();
    for (java.lang.reflect.Field f : clazz.getFields()) {
      if (f.isAnnotationPresent(Field.class)) {
        String propertyName = f.getName();
        Field fieldAnno = f.getAnnotation(Field.class);
        String columnName =
            StringUtils.defaultIfBlank(fieldAnno.columnName(),
                toDashSeparated(propertyName));
        fields.add(new FieldMeta(propertyName, columnName, f
            .isAnnotationPresent(Id.class)));
      }
    }
    return new EntityMeta(tableName, tableAlias, fields);
  }

  /**
   * Convert property name to dash separated column name.
   * {@code dateCreated => date_created}.
   * 
   * @param propertyName property name
   * @return dash separated name
   */
  String toDashSeparated(String propertyName) {
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
