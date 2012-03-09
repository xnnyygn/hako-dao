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

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.condition.AbstractCondition;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Abstract binary logic condition.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public abstract class AbstractBinaryLogicCondition extends AbstractCondition {

  protected final Condition left;
  protected final String operator;
  protected final Condition right;

  /**
   * Create.
   * 
   * @param left
   * @param operator
   * @param right
   */
  public AbstractBinaryLogicCondition(Condition left, String operator,
      Condition right) {
    super();
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  public String toPrepared() {
    return new StringBuilder(left.toPrepared()).append(' ').append(operator)
        .append(' ').append(right.toPrepared()).toString();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    all.addAll(left.getParams());
    all.addAll(right.getParams());
    return all;
  }

}
