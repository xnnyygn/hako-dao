package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class LessThanCondition extends AbstractCompareCondition {

  /**
   * Create.
   * 
   * @param leftOperand
   * @param rightOperand
   */
  public LessThanCondition(Expression leftOperand, Expression rightOperand) {
    super(leftOperand, "<", rightOperand);
  }

}
