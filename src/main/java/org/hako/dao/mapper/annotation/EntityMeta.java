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
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.expression.TableColumnName;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.ConditionBuilder;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.Values;

/**
 * Entity meta.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityMeta {

  private final String tableName;
  private final String tableAlias;
  private final Fields fields;

  /**
   * Create.
   * 
   * @param tableName table name
   * @param tableAlias table alias
   * @param fields
   */
  public EntityMeta(String tableName, String tableAlias, List<FieldMeta> fields) {
    super();
    this.tableName = tableName;
    this.tableAlias = tableAlias;
    this.fields = new Fields(fields);
  }

  /**
   * Get table name.
   * 
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Get table alias.
   * 
   * @return the tableAlias
   */
  public String getTableAlias() {
    return tableAlias;
  }

  /**
   * Create selection for all fields.
   * 
   * @return selection
   */
  public Selection createAllFieldsSelection() {
    MultipleSelectionBuilder builder = new MultipleSelectionBuilder();
    for (FieldMeta f : fields.getAllFields()) {
      builder.addExpressionAka(
          new TableColumnName(tableAlias, f.getColumnName()),
          f.getPropertyName());
    }
    return builder.toMultipleSelection();
  }

  public Condition createPkCondition(Object id) {
    List<FieldMeta> pkFields = fields.getPkFields();
    int pkFieldCount = pkFields.size();
    if (pkFieldCount == 0) {
      // no primary key
      throw new IllegalStateException("no primary key");
    } else if (pkFieldCount == 1) {
      return Conditions.eq(new TableColumnName(tableAlias, pkFields.get(0)
          .getColumnName()), Values.create(id));
    } else {
      ConditionBuilder builder = new ConditionBuilder();
      Map<String, Object> props = toProperties(id);
      for (FieldMeta f : pkFields) {
        String propertyName = f.getPropertyName();
        if (!props.containsKey(propertyName)) {
          throw new IllegalArgumentException("property [" + propertyName
              + "] is required");
        }
        builder.add(Conditions.eq(
            new TableColumnName(tableAlias, f.getColumnName()),
            Values.create(props.get(propertyName))));
      }
      return builder.build();
    }
  }

  private Map<String, Object> toProperties(Object bean) {
    Map<String, Object> props = new HashMap<String, Object>();
    Class<?> beanClass = bean.getClass();
    for (Method m : beanClass.getMethods()) {
      String name = m.getName();
      if (name.startsWith("get") && name.length() > 3
          && m.getParameterTypes().length == 0) {
        Option<Object> valueOpt = getValueByGetterMethod(m, bean);
        if (valueOpt.hasValue()) {
          props
              .put(StringUtils.uncapitalize(name.substring(3)), valueOpt.get());
        }
      }
    }
    for (Field f : beanClass.getFields()) {
      try {
        props.put(f.getName(), f.get(bean));
      } catch (Exception e) {
        // TODO log error
      }
    }
    return props;
  }

  private Option<Object> getValueByGetterMethod(Method getter, Object bean) {
    try {
      return new Some<Object>(getter.invoke(bean));
    } catch (Exception e) {
      // TODO log error
    }
    return new None<Object>();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }

}
