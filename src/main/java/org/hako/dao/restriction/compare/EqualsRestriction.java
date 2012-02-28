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
package org.hako.dao.restriction.compare;

import org.hako.dao.Field;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.compare.EqualsCondition;
import org.hako.dao.sql.expression.value.ValueFactory;

/**
 * Equals restriction.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 * 
 */
public class EqualsRestriction extends AbstractCompareRestriction {

  /**
   * Create.
   * 
   * @param field
   * @param value
   */
  public EqualsRestriction(Field<?> field, Object value) {
    super(field, value);
  }

  public Condition toCondition() {
    return new EqualsCondition(new ColumnName(field.getColumnName()),
        ValueFactory.create(value));
  }

}