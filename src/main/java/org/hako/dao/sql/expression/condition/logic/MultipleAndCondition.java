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
package org.hako.dao.sql.expression.condition.logic;

import java.util.List;

import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.expression.condition.AbstractCondition;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Multiple and condition.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleAndCondition extends AbstractCondition {

  private final List<Condition> conditions;

  /**
   * Create.
   * 
   * @param conditions
   * @throws IllegalArgumentException if conditions is empty
   */
  public MultipleAndCondition(List<Condition> conditions)
      throws IllegalArgumentException {
    super();
    if (conditions.isEmpty()) {
      throw new IllegalArgumentException("conditions cannot be empty");
    }
    this.conditions = conditions;
  }

  public String toPrepared() {
    return new ToPreparedBuilder().append(" AND ", conditions).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    return new ToFormattedBuilder().append(marginCount, "AND \n", conditions)
        .toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(conditions);
  }

}
