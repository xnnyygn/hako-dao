package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class NotEqualCondition extends AbstractCompareCondition {

  /**
   * Create.
   * 
   * @param leftOperand
   * @param rightOperand
   */
  public NotEqualCondition(Expression leftOperand, Expression rightOperand) {
    super(leftOperand, "!=", rightOperand);
  }

}
