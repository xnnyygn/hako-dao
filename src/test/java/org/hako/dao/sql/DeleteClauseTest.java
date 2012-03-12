package org.hako.dao.sql;

import java.util.Date;

import org.hako.Some;
import org.hako.dao.sql.clause.delete.DeleteClause;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.Values;
import org.junit.Test;

public class DeleteClauseTest {

  @Test
  public void testToPrepared() {
    DeleteClause clause =
        new DeleteClause("BLOG", new Some<Condition>(Conditions.eq(
            Conditions.eq(new ColumnName("id"), Values.create(1l)),
            Conditions.eq(new ColumnName("date_created"),
                Values.create(new Date())))));
    System.out.println(clause);
  }

}
