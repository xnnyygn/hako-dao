package org.hako.dao.sql.clause.select.selection;

import org.hako.dao.sql.expression.Expression;

public class ExpressionAkaSelection extends ExpressionSelection {

  private final String alias;

  /**
   * Create.
   * 
   * @param expression
   * @param alias
   */
  public ExpressionAkaSelection(Expression expression, String alias) {
    super(expression);
    this.alias = alias;
  }

  @Override
  public String toPrepared() {
    return super.toPrepared() + " AS " + alias;
  }

}
