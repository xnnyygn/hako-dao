package org.hako.dao.sql;

import org.hako.dao.sql.clause.update.UpdateClause;
import org.hako.dao.sql.clause.update.UpdateClauseBuilder;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.compare.EqualsCondition;
import org.hako.dao.sql.expression.value.ValueFactory;
import org.junit.Test;

public class UpdateClauseTest {

  @Test
  public void test1() {
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update("BLOG", "b");
    builder.set(new String[] {"title", "content"}, new Expression[] {
        ValueFactory.create("title0"), ValueFactory.create("content0")});
    builder.where(new EqualsCondition(new ColumnName("id"), ValueFactory
        .create(1l)));
    UpdateClause clause = builder.toUpdateClause();
    System.out.println(clause.toPrepared());
    System.out.println(clause.getParams());
  }

  @Test
  public void test2() {
    // UPDATE customers SET salesperson = "Mike" WHERE state = "NH"
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update("customers");
    builder.set("saleperson", ValueFactory.create("Mike"));
    builder.where(new EqualsCondition(new ColumnName("state"), ValueFactory
        .create("NH")));
    UpdateClause clause = builder.toUpdateClause();
    System.out.println(clause.toPrepared());
    System.out.println(clause.getParams());
  }

}
