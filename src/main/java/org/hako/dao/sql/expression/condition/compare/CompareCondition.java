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

import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.InnerSelectExpression;
import org.hako.dao.sql.expression.condition.AbstractCondition;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Abstract compare condition.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class CompareCondition extends AbstractCondition {

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
  public CompareCondition(Expression leftOperand, CompareSymbol symbol,
      Expression rightOperand) {
    this(leftOperand, sqlOfSymbol(symbol), rightOperand);
  }

  /**
   * Get SQL of compare symbol.
   * <p>
   * Returned SQL with leading and ending space.
   * </p>
   * 
   * @param symbol
   * @return SQL of symbol
   */
  private static String sqlOfSymbol(CompareSymbol symbol) {
    switch (symbol) {
      case EQUAL:
        return " = ";
      case NOT_EQUAL:
        return " != ";
      case LESS_THAN:
        return " < ";
      case LESS_THAN_AND_EQUAL:
        return " <= ";
      case GREATER_THAN:
        return " > ";
      case GREATER_THAN_AND_EQUAL:
        return " >= ";
      default:
        throw new IllegalArgumentException("no SQL mapping for symbol ["
            + symbol + "]");
    }
  }
  
  /**
   * Create.
   * 
   * @param leftOperand
   * @param operator
   * @param rightOperand
   */
  public CompareCondition(Expression leftOperand, String operator,
      Expression rightOperand) {
    super();
    this.leftOperand = leftOperand;
    this.operator = operator;
    this.rightOperand = rightOperand;
  }


 

  public String toPrepared() {
    return new ToPreparedBuilder().append(leftOperand).append(operator)
        .append(rightOperand).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    logMarginCount(marginCount);
    StringBuilder builder =
        new StringBuilder(leftOperand.toFormatted(marginCount));
    builder.append(operator);
    if (rightOperand instanceof InnerSelectExpression) {
      builder.append('\n');
      builder.append(rightOperand.toFormatted(marginCount));
    } else {
      builder.append(rightOperand.toFormatted(0));
    }
    return builder.toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(leftOperand, rightOperand);
  }

}
