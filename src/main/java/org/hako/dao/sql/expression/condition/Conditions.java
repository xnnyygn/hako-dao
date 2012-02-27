package org.hako.dao.sql.expression.condition;

import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.compare.EqualsCondition;
import org.hako.dao.sql.expression.condition.compare.NotEqualCondition;
import org.hako.dao.sql.expression.condition.logic.OrCondition;

public class Conditions {

  public static EqualsCondition eq(Expression left, Expression right) {
    return new EqualsCondition(left, right);
  }

  public static NotEqualCondition ne(Expression left, Expression right) {
    return new NotEqualCondition(left, right);
  }
  
  public static OrCondition or(Condition left, Condition right){
    return new OrCondition(left, right);
  }
  
}
