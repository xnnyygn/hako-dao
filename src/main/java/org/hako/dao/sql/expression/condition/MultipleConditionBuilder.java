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
package org.hako.dao.sql.expression.condition;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;

/**
 * Builder of {@link MultipleAndCondition}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleConditionBuilder {

  private final List<Condition> conditions = new ArrayList<Condition>();

  /**
   * Add condition.
   * 
   * @param condition
   * @return this
   */
  public MultipleConditionBuilder add(Condition condition) {
    conditions.add(condition);
    return this;
  }

  /**
   * Build multiple and condition.
   * 
   * @return mutliple and condition
   * @see MultipleAndCondition
   */
  public MultipleAndCondition build() {
    return new MultipleAndCondition(conditions);
  }

}
