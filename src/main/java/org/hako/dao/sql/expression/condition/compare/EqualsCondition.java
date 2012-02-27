package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class EqualsCondition extends AbstractCompareCondition {

  /**
   * Create.
   * 
   * @param leftOperand
   * @param rightOperand
   */
  public EqualsCondition(Expression leftOperand, Expression rightOperand) {
    super(leftOperand, "=", rightOperand);
  }

}
