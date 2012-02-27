package org.hako.dao.sql.clause.select.selection;

import java.util.List;

import org.hako.dao.sql.expression.Expression;

public class ExpressionSelection implements Selection {

  protected final Expression expression;
  
  /**
   * Create.
   * 
   * @param expression
   */
  public ExpressionSelection(Expression expression) {
    super();
    this.expression = expression;
  }

  public String toPrepared() {
    return expression.toPrepared();
  }

  public List<Object> getParams() {
    return expression.getParams();
  }

}
