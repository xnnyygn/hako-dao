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

import org.hako.dao.sql.expression.AsteriskExpression;
import org.hako.dao.sql.expression.Expression;

/**
 * Function factory.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Functions {

  /**
   * Create count function.
   * 
   * @param expression
   * @return
   */
  public static Count count(Expression expression) {
    return new Count(expression);
  }

  /**
   * Count row.
   * 
   * @return count
   * @see AsteriskExpression
   * @since 1.1.0
   */
  public static Count countRow() {
    return count(new AsteriskExpression());
  }

  /**
   * Create unary function.
   * 
   * @param name
   * @param expression
   * @return unary function
   * @since 1.1.0
   */
  public static UnaryFunction unaryFun(String name, Expression expression) {
    return new UnaryFunction(name, expression);
  }

  /**
   * Create binary function.
   * 
   * @param name
   * @param first
   * @param second
   * @return binary function
   * @see BinaryFunction
   * @since 1.1.0
   */
  public static BinaryFunction binaryFun(String name, Expression first,
      Expression second) {
    return new BinaryFunction(name, first, second);
  }

}
