package org.hako.dao.sql;

import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.condition.compare.EqualsCondition;
import org.hako.dao.sql.expression.condition.compare.GreaterThanAndEqualCondition;
import org.hako.dao.sql.expression.condition.compare.GreaterThanCondition;
import org.hako.dao.sql.expression.condition.compare.LessThanAndEqualCondition;
import org.hako.dao.sql.expression.condition.compare.LessThanCondition;
import org.hako.dao.sql.expression.condition.compare.NotEqualCondition;
import org.hako.dao.sql.expression.value.LongValue;
import org.junit.Test;

public class ConditionTest {

  @Test
  public void test() {
    ColumnName columnId = new ColumnName("id");
    LongValue idValue = new LongValue(1l);
    System.out.println(new EqualsCondition(columnId, idValue).toPrepared());
    System.out.println(new NotEqualCondition(columnId, idValue).toPrepared());
    System.out.println(new LessThanCondition(columnId, idValue).toPrepared());
    System.out.println(new LessThanAndEqualCondition(columnId, idValue)
        .toPrepared());
    System.out
        .println(new GreaterThanCondition(columnId, idValue).toPrepared());
    System.out.println(new GreaterThanAndEqualCondition(columnId, idValue)
        .toPrepared());
  }

}
