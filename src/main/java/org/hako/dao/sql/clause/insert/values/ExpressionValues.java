package org.hako.dao.sql.clause.insert.values;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;

/**
 * Expression values.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ExpressionValues implements ValueSource {

  private final List<Expression> expressions;

  /**
   * Create.
   * 
   * @param expressions
   * @throws IllegalArgumentException if expressions is empty
   */
  public ExpressionValues(List<Expression> expressions)
      throws IllegalArgumentException {
    super();
    if (expressions.isEmpty()) {
      throw new IllegalArgumentException("expressions cannot be empty");
    }
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
