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

import static org.hako.dao.sql.expression.condition.compare.CompareSymbol.EQUAL;
import static org.hako.dao.sql.expression.condition.compare.CompareSymbol.GREATER_THAN;
import static org.hako.dao.sql.expression.condition.compare.CompareSymbol.NOT_EQUAL;

import org.hako.dao.sql.clause.select.SelectClause;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.InnerSelectExpression;
import org.hako.dao.sql.expression.condition.compare.BetweenCondition;
import org.hako.dao.sql.expression.condition.compare.CompareCondition;
import org.hako.dao.sql.expression.condition.compare.CompareSymbol;
import org.hako.dao.sql.expression.condition.compare.InCondition;
import org.hako.dao.sql.expression.condition.compare.LikeCondition;
import org.hako.dao.sql.expression.condition.logic.AndCondition;
import org.hako.dao.sql.expression.condition.logic.OrCondition;

/**
 * Conditions.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Conditions {

  /**
   * Create equal condition.
   * 
   * @param left
   * @param right
   * @return equal condition
   * @see CompareSymbol#EQUAL
   * @see CompareCondition
   */
  public static Condition eq(Expression left, Expression right) {
    return new CompareCondition(left, EQUAL, right);
  }

  /**
   * Create not equal condition.
   * 
   * @param left
   * @param right
   * @return not equal condition
   * @see CompareSymbol#NOT_EQUAL
   * @see CompareCondition
   */
  public static Condition ne(Expression left, Expression right) {
    return new CompareCondition(left, NOT_EQUAL, right);
  }

  /**
   * Create and condition.
   * 
   * @param left
   * @param right
   * @return condition
   */
  public static Condition and(Condition left, Condition right) {
    return new AndCondition(left, right);
  }

  /**
   * Create or condition.
   * 
   * @param left
   * @param right
   * @return or condition
   * @see OrCondition
   */
  public static OrCondition or(Condition left, Condition right) {
    return new OrCondition(left, right);
  }

  /**
   * Create greater than condition.
   * 
   * @param left
   * @param right
   * @return greater than condition
   */
  public static Condition gt(Expression left, Expression right) {
    return new CompareCondition(left, GREATER_THAN, right);
  }

  /**
   * Create in condition.
   * 
   * @param left
   * @param right
   * @return in condition
   * @see InCondition
   */
  public static InCondition in(Expression left, Expression right) {
    return new InCondition(left, right);
  }

  /**
   * Create in select condition.
   * 
   * @param left
   * @param select
   * @return InCondition
   * @see #in(Expression, Expression)
   * @see InnerSelectExpression
   */
  public static InCondition inSelect(Expression left, SelectClause select) {
    return in(left, new InnerSelectExpression(select));
  }

  /**
   * Create between condition.
   * 
   * @param operand
   * @param min
   * @param max
   * @return between condition instance
   * @see BetweenCondition#BetweenCondition(Expression, Expression, Expression)
   */
  public static BetweenCondition between(Expression operand, Expression min,
      Expression max) {
    return new BetweenCondition(operand, min, max);
  }

  /**
   * Create like condition.
   * 
   * @param left
   * @param right
   * @return like condition
   * @see LikeCondition
   */
  public static LikeCondition like(Expression left, Expression right) {
    return new LikeCondition(left, right);
  }

}
