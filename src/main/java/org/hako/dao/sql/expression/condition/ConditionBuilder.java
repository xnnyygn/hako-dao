package org.hako.dao.sql.expression.condition;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;

public class ConditionBuilder {

  private final List<Condition> conditions = new ArrayList<Condition>();
  
  public ConditionBuilder add(Condition condition){
    conditions.add(condition);
    return this;
  }
  
  public MultipleAndCondition build(){
    return new MultipleAndCondition(conditions);
  }
  
}
