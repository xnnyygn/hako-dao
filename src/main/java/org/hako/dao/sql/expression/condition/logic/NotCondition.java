package org.hako.dao.sql.expression.condition.logic;

import java.util.List;

import org.hako.dao.sql.expression.condition.Condition;

public class NotCondition implements Condition {

  private final Condition condition;

  /**
   * Create.
   * 
   * @param condition
   */
  public NotCondition(Condition condition) {
    super();
    this.condition = condition;
  }

  public String toPrepared() {
    return "NOT " + condition.toPrepared();
  }

  public List<Object> getParams() {
    return condition.getParams();
  }

}
