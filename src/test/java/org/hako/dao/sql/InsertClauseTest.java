package org.hako.dao.sql;

import java.util.Arrays;

import org.hako.dao.sql.clause.insert.InsertClause;
import org.hako.dao.sql.clause.insert.values.ExpressionValues;
import org.hako.dao.sql.expression.value.DefaultValue;
import org.hako.dao.sql.expression.value.LongValue;
import org.hako.dao.sql.expression.value.StringValue;
import org.junit.Test;

public class InsertClauseTest {

  @Test
  public void testToPrepared() {
    InsertClause clause =
        new InsertClause("BLOG", Arrays.asList("title", "content",
            "date_created", "user_id"), new ExpressionValues(Arrays.asList(
            new StringValue("title0"), new StringValue("content0"),
            new DefaultValue(), new LongValue(1l))));
    System.out.println(clause.toPrepared());
  }

  @Test
  public void testGetParams() {
    InsertClause clause =
        new InsertClause("BLOG", Arrays.asList("title", "content",
            "date_created", "user_id"), new ExpressionValues(Arrays.asList(
            new StringValue("title0"), new StringValue("content0"),
            new DefaultValue(), new LongValue(1l))));
    System.out.println(clause.getParams());
  }

}
