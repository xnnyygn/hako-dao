package org.hako.dao.sql.clause.select.selection;

import org.hako.dao.sql.expression.Expression;

public class DistinctExpressionSelection extends ExpressionSelection {

  /**
   * Create.
   * 
   * @param expression
   */
  public DistinctExpressionSelection(Expression expression) {
    super(expression);
  }

  @Override
  public String toPrepared() {
    return "DISTINCT " + super.toPrepared();
  }

}
