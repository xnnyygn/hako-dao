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

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Abstract compare condition.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public abstract class AbstractCompareCondition implements Condition {

  protected final Expression leftOperand;
  protected final String operator;
  protected final Expression rightOperand;

  /**
   * Create.
   * 
   * @param leftOperand
   * @param operator
   * @param rightOperand
   */
  public AbstractCompareCondition(Expression leftOperand, String operator,
      Expression rightOperand) {
    super();
    this.leftOperand = leftOperand;
    this.operator = operator;
    this.rightOperand = rightOperand;
  }

  public String toPrepared() {
    return new StringBuilder(leftOperand.toPrepared()).append(' ')
        .append(operator).append(' ').append(rightOperand.toPrepared())
        .toString();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    all.addAll(leftOperand.getParams());
    all.addAll(rightOperand.getParams());
    return all;
  }

}
