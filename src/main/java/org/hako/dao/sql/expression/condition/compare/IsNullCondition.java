package org.hako.dao.sql.expression.condition.compare;

import org.hako.dao.sql.expression.Expression;

public class IsNullCondition extends AbstractNullCondition {

  /**
   * Create.
   * 
   * @param expression
   */
  public IsNullCondition(Expression expression) {
    super(expression);
  }

  public String toPrepared() {
    return expression.toPrepared() + " IS NULL";
  }

}
