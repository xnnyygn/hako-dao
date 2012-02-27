package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class GreaterThanAndEqualCondition extends AbstractCompareCondition {

  /**
   * Create.
   * 
   * @param leftOperand
   * @param rightOperand
   */
  public GreaterThanAndEqualCondition(Expression leftOperand,
      Expression rightOperand) {
    super(leftOperand, ">=", rightOperand);
  }

}
