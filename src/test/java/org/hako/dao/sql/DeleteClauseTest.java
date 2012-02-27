package org.hako.dao.sql;

import java.sql.Timestamp;

import org.hako.Some;
import org.hako.dao.sql.clause.delete.DeleteClause;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.compare.EqualsCondition;
import org.hako.dao.sql.expression.condition.compare.LessThanCondition;
import org.hako.dao.sql.expression.condition.logic.OrCondition;
import org.hako.dao.sql.expression.value.LongValue;
import org.hako.dao.sql.expression.value.TimestampValue;
import org.junit.Test;

public class DeleteClauseTest {

  @Test
  public void testToPrepared() {
    DeleteClause clause =
        new DeleteClause("BLOG",
            new Some<Condition>(new OrCondition(new EqualsCondition(
                new ColumnName("id"), new LongValue(1l)),
                new LessThanCondition(new ColumnName("date_created"),
                    new TimestampValue(
                        new Timestamp(System.currentTimeMillis()))))));
    System.out.println(clause.toPrepared());
    System.out.println(clause.getParams());
  }

  @Test
  public void testGetParams() {
  }

}
