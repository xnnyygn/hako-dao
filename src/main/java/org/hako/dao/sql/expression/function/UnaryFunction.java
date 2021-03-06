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
package org.hako.dao.sql.expression.function;

import java.util.List;

import org.hako.dao.sql.expression.Expression;

/**
 * Unary function.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UnaryFunction extends AbstractFunction {

  protected final Expression expression;

  /**
   * Create.
   * 
   * @param name
   * @param expression
   */
  public UnaryFunction(String name, Expression expression) {
    super(name);
    this.expression = expression;
  }

  public String toPrepared() {
    return new StringBuilder(name).append('(').append(expression.toPrepared())
        .append(')').toString();
  }

  public List<Object> getParams() {
    return expression.getParams();
  }

}
