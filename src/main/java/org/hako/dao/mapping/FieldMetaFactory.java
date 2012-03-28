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
package org.hako.dao.mapping;

import static org.apache.commons.lang.StringUtils.defaultIfBlank;
import static org.apache.commons.lang.StringUtils.uncapitalize;

import java.lang.reflect.Method;

import org.hako.dao.mapper.ColumnStrategy;
import org.hako.dao.mapper.Field;
import org.hako.dao.mapper.Id;

/**
 * Factory for {@link FieldMeta}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 * @see #create(Method)
 */
public class FieldMetaFactory {

  private final ColumnStrategy columnStrategy;

  /**
   * Create.
   * 
   * @param columnStrategy
   */
  public FieldMetaFactory(ColumnStrategy columnStrategy) {
    super();
    this.columnStrategy = columnStrategy;
  }

  /**
   * Create field meta from getter method.
   * 
   * @param getter
   * @return field meta
   */
  public FieldMeta create(Method getter) {
    String propertyName = generatePropertyName(getter.getName());
    String columnName = generateOrGetColumnName(getter, propertyName);
    if (getter.isAnnotationPresent(Id.class)) {
      return new PrimaryKeyMeta(columnName, propertyName, getter.getAnnotation(
          Id.class).generated());
    }
    return new NormalFieldMeta(columnName, propertyName);
  }

  /**
   * Generate property name from getter method name.
   * <ul>
   * <li>getAbc => abc</li>
   * <li>isAbc => abc</li>
   * </ul>
   * 
   * @param getterName
   * @return property name from method name
   * @see org.apache.commons.lang.StringUtils#uncapitalize(String)
   */
  private String generatePropertyName(String getterName) {
    if (getterName.startsWith("get")) {
      return uncapitalize(getterName.substring(3));
    }
    // for isXXX
    return uncapitalize(getterName.substring(2));
  }

  /**
   * Generate or get column name. Default column name was generated from
   * property name using strategy in
   * {@link ColumnStrategy#generateNameFrom(String)}. User defined column name
   * will be get from {@link Field#columnName()}. User defined one was
   * preferred.
   * 
   * @param getter
   * @param propertyName
   * @return column name
   */
  private String generateOrGetColumnName(Method getter, String propertyName) {
    return defaultIfBlank(getter.getAnnotation(Field.class).columnName(),
        columnStrategy.generateNameFrom(propertyName));
  }

}
