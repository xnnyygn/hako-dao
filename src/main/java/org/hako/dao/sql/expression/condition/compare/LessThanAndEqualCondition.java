package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class LessThanAndEqualCondition extends AbstractCompareCondition {

  /**
   * Create.
   * 
   * @param leftOperand
   * @param rightOperand
   */
  public LessThanAndEqualCondition(Expression leftOperand,
      Expression rightOperand) {
    super(leftOperand, "<=", rightOperand);
  }

}
