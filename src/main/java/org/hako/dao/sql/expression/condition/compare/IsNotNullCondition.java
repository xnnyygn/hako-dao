package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class IsNotNullCondition extends AbstractNullCondition {

  /**
   * Create.
   * 
   * @param expression
   */
  public IsNotNullCondition(Expression expression) {
    super(expression);
  }

  public String toPrepared() {
    return expression.toPrepared() + " IS NOT NULL";
  }

}
