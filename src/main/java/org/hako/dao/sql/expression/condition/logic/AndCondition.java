package org.hako.dao.sql.expression.condition.logic;

import org.hako.dao.sql.expression.condition.Condition;

public class AndCondition extends AbstractBinaryLogicCondition {

  /**
   * Create.
   * 
   * @param left
   * @param right
   */
  public AndCondition(Condition left, Condition right) {
    super(left, "AND", right);
  }

}
