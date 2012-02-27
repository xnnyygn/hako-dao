package org.hako.dao.sql.expression.condition.logic;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.condition.Condition;

public abstract class AbstractBinaryLogicCondition implements Condition {

  protected final Condition left;
  protected final String operator;
  protected final Condition right;

  /**
   * Create.
   * 
   * @param left
   * @param operator
   * @param right
   */
  public AbstractBinaryLogicCondition(Condition left, String operator, Condition right) {
    super();
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  public String toPrepared() {
    return new StringBuilder(left.toPrepared()).append(' ').append(operator)
        .append(' ').append(right.toPrepared()).toString();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    all.addAll(left.getParams());
    all.addAll(right.getParams());
    return all;
  }

}
