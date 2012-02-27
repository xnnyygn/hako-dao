package org.hako.dao.sql.expression.condition.logic;

import java.util.List;

import org.hako.dao.sql.clause.select.SelectClause;
import org.hako.dao.sql.expression.condition.Condition;

public class ExistsCondition implements Condition {

  private final SelectClause select;

  /**
   * Create.
   * 
   * @param select
   */
  public ExistsCondition(SelectClause select) {
    super();
    this.select = select;
  }

  public String toPrepared() {
    return "EXISTS ( " + select.toPrepared() + " )";
  }

  public List<Object> getParams() {
    return select.getParams();
  }

}
