package org.hako.dao.sql.expression.condition.logic;

import org.hako.dao.sql.expression.condition.Condition;

public class OrCondition extends AbstractBinaryLogicCondition {

  public OrCondition(Condition left, Condition right) {
    super(left, "OR", right);
  }

}
