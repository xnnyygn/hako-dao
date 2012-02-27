package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class GreaterThanCondition extends AbstractCompareCondition {

  /**
   * Create.
   * 
   * @param leftOperand
   * @param rightOperand
   */
  public GreaterThanCondition(Expression leftOperand, Expression rightOperand) {
    super(leftOperand, ">", rightOperand);
  }

}
