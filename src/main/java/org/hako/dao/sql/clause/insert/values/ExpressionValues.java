package org.hako.dao.sql.clause.insert.values;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;

public class ExpressionValues implements Values {

  private final List<Expression> expressions;

  /**
   * Create.
   * 
   * @param expressions
   */
  public ExpressionValues(List<Expression> expressions) {
    super();
    this.expressions = expressions;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder("VALUES (");
    for (Expression expr : expressions) {
      builder.append(expr.toPrepared()).append(", ");
    }
    builder.setLength(builder.length() - 2);
    builder.append(")");
    return builder.toString();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    for (Expression expr : expressions) {
      all.addAll(expr.getParams());
    }
    return all;
  }

}
