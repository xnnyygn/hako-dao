package org.hako.dao.sql.expression.condition.compare;

import java.util.List;

import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;

public abstract class AbstractNullCondition implements Condition {

  protected final Expression expression;

  /**
   * Create.
   * 
   * @param expression
   */
  public AbstractNullCondition(Expression expression) {
    super();
    this.expression = expression;
  }

  public List<Object> getParams() {
    return expression.getParams();
  }

}
