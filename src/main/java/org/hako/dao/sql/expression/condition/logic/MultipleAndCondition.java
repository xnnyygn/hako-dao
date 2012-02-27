package org.hako.dao.sql.expression.condition.logic;

import java.util.List;

import org.hako.dao.sql.Sql;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.util.MultipleSqlUtils;

public class MultipleAndCondition implements Condition {

  private final List<Condition> conditions;

  /**
   * Create.
   * 
   * @param conditions
   */
  public MultipleAndCondition(List<Condition> conditions) {
    super();
    this.conditions = conditions;
  }

  public String toPrepared() {
    return MultipleSqlUtils.toPrepared(" AND ", conditions.toArray(new Sql[0]));
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.getParams(conditions.toArray(new Sql[0]));
  }

}
