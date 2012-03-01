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

import java.lang.reflect.Field;

import org.hako.dao.mapping.entity.EntityMeta;
import org.hako.dao.mapping.entity.EntityMetaBuilder;
import org.hako.dao.mapping.field.FieldMeta;
import org.hako.dao.mapping.field.MappedField;

/**
 * A mapper using static fields of class to setup entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class StaticMapper {

  /**
   * Setup entity meta.
   * 
   * @param entityClass
   * @return
   */
  public EntityMeta setup(Class<?> entityClass) {
    EntityMetaBuilder builder = new EntityMetaBuilder();
    // setup table name and table alias
    // use lower case class simple name as table name
    // use same name as table alias
    String tableName = entityClass.getSimpleName().toLowerCase();
    builder.updateTableName(tableName, tableName);
    // setup fields
    // find static fields and use variable name as property name
    // use property name as column name
    for (Field f : entityClass.getDeclaredFields()) {
      if (f.getType().isAssignableFrom(MappedField.class)) {
        MappedField<?> field = getMappedField(f);
        String fieldName = f.getName();
        builder.updateFieldMeta(field, toDashSeparatedColumnName(fieldName));
      }
    }
    postSetup(entityClass, builder);
    return builder.build();
  }

  /**
   * Get mapped field instance.
   * 
   * @param classField class field
   * @return mapped field
   * @throws MappingException if failed to get
   * @see Field#get(Object)
   */
  private MappedField<?> getMappedField(Field classField)
      throws MappingException {
    try {
      // since field must be static, the argument of
      // Field#get(Object) is null
      return ((MappedField<?>) classField.get(null));
    } catch (Exception e) {
      throw new MappingException(e);
    }
  }

  /**
   * Invoke postSetup on entity.
   * 
   * @param entityClass entity class
   * @param builder
   */
  private void postSetup(Class<?> entityClass, EntityMetaBuilder builder) {
    try {
      entityClass.getMethod("postSetup", EntityMetaBuilder.class).invoke(null,
          builder);
    } catch (Exception e) {
      // ignore exception
    }
  }

  /**
   * Convert property name to dash separated column name.
   * {@code dateCreated => date_created}.
   * 
   * @param propertyName property name
   * @return dash separated name
   */
  String toDashSeparatedColumnName(String propertyName) {
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
