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
package org.hako.dao.sql.expression.condition.compare;

import java.util.List;

import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.AbstractCondition;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Between condition.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BetweenCondition extends AbstractCondition {

  private final Expression operand;
  private final Expression min;
  private final Expression max;

  /**
   * Create.
   * 
   * @param operand
   * @param min
   * @param max
   */
  public BetweenCondition(Expression operand, Expression min, Expression max) {
    super();
    this.operand = operand;
    this.min = min;
    this.max = max;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder(operand.toPrepared());
    builder.append(" BETWEEN ");
    builder.append(min.toPrepared());
    builder.append(" AND ");
    builder.append(max.toPrepared());
    return builder.toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(operand, min, max);
  }

}
