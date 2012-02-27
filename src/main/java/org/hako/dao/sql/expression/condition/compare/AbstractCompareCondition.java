package org.hako.dao.sql.expression.condition.compare;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;

public abstract class AbstractCompareCondition implements Condition {

  protected final Expression leftOperand;
  protected final String operator;
  protected final Expression rightOperand;

  /**
   * @param leftOperand
   * @param operator
   * @param rightOperand
   */
  public AbstractCompareCondition(Expression leftOperand, String operator,
      Expression rightOperand) {
    super();
    this.leftOperand = leftOperand;
    this.operator = operator;
    this.rightOperand = rightOperand;
  }

  public String toPrepared() {
    return new StringBuilder(leftOperand.toPrepared()).append(' ')
        .append(operator).append(' ').append(rightOperand.toPrepared())
        .toString();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    all.addAll(leftOperand.getParams());
    all.addAll(rightOperand.getParams());
    return all;
  }

}
