package org.hako.dao.sql;

import org.hako.dao.sql.clause.select.SelectClause;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.select.selection.ExpressionSelection;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.clause.select.selection.TableAsteriskSelection;
import org.hako.dao.sql.clause.select.table.AkaTable;
import org.hako.dao.sql.clause.select.table.JoinType;
import org.hako.dao.sql.clause.select.table.JoinWithConditionTable;
import org.hako.dao.sql.clause.select.table.MultipleTableBuilder;
import org.hako.dao.sql.clause.select.table.SimpleTable;
import org.hako.dao.sql.clause.select.table.TableFactory;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.InnerSelectExpression;
import org.hako.dao.sql.expression.TableColumnName;
import org.hako.dao.sql.expression.condition.ConditionBuilder;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.condition.compare.EqualsCondition;
import org.hako.dao.sql.expression.condition.compare.IsNullCondition;
import org.hako.dao.sql.expression.value.ValueFactory;
import org.junit.Test;

public class SelectClauseBuilderTest {

  @Test
  public void testBuild1() {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.from("BLOG", "b");
    SelectClause select = builder.toSelectClause();
    System.out.println(select.toPrepared());
    System.out.println(select.getParams());
  }

  @Test
  public void testBuild2() {
    // create selection
    MultipleSelectionBuilder selectionBuilder = new MultipleSelectionBuilder();
    ColumnName columnId = new ColumnName("id");
    selectionBuilder.addExpression(columnId);
    selectionBuilder.addExpression(new ColumnName("title"));
    selectionBuilder.addExpression(new ColumnName("content"));
    selectionBuilder.addExpression(new ColumnName("date_created"));
    ColumnName columnUserId = new ColumnName("user_id");
    selectionBuilder.addExpression(columnUserId);
    selectionBuilder.addTableAll("u");
    // create tables
    MultipleTableBuilder tableBuilder = new MultipleTableBuilder();
    tableBuilder.add(TableFactory.createSimpleAkaTable("BLOG", "b"));
    tableBuilder.add(TableFactory.createSimpleAkaTable("USER", "u"));
    // create where condition
    ConditionBuilder conditionBuilder = new ConditionBuilder();
    conditionBuilder.add(Conditions.eq(columnId, ValueFactory.create(1l)));
    conditionBuilder.add(Conditions.ne(columnUserId, ValueFactory.create(2l)));
    // use select builder
    SelectClauseBuilder selectBuilder = new SelectClauseBuilder();
    selectBuilder.select(selectionBuilder.toMultipleSelection());
    selectBuilder.from(tableBuilder.toMultipleTable());
    selectBuilder.where(conditionBuilder.build());
    // create select clause
    SelectClause select = selectBuilder.toSelectClause();
    System.out.println(select.toPrepared());
    System.out.println(select.getParams());
  }

  @Test
  public void testBuild3() {
    // SELECT e1.name FROM emp e1,emp e2
    // WHERE e1.dept_no = e2.dept_no AND e2.name = 'JONES'
    ExpressionSelection selection =
        new ExpressionSelection(new TableColumnName("e1", "name"));
    MultipleTableBuilder tableBuilder = new MultipleTableBuilder();
    tableBuilder.add(TableFactory.createSimpleAkaTable("emp", "e1"));
    tableBuilder.add(TableFactory.createSimpleAkaTable("emp", "e2"));
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(selection);
    builder.from(tableBuilder.toMultipleTable());
    ConditionBuilder conditionBuilder = new ConditionBuilder();
    conditionBuilder.add(Conditions.eq(new TableColumnName("e1", "dept_no"),
        new TableColumnName("e2", "dept_no")));
    conditionBuilder.add(Conditions.eq(new TableColumnName("e2", "name"),
        ValueFactory.create("JONES")));
    builder.where(conditionBuilder.build());
    SelectClause clause = builder.toSelectClause();
    System.out.println(clause.toPrepared());
    System.out.println(clause.getParams());
  }

  @Test
  public void testBuild4() {
    // SELECT name FROM emp WHERE dept_no =
    // (SELECT dept_no FROM emp WHERE name = 'JONES')
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(new ExpressionSelection(new ColumnName("name")));
    builder.from(new SimpleTable("emp"));
    SelectClauseBuilder innerBuilder = new SelectClauseBuilder();
    innerBuilder.select(new ExpressionSelection(new ColumnName("dept_no")));
    innerBuilder.from(new SimpleTable("emp"));
    innerBuilder.where(new EqualsCondition(new ColumnName("name"), ValueFactory
        .create("JONES")));
    builder.where(new EqualsCondition(new ColumnName("dept_no"),
        new InnerSelectExpression(innerBuilder.toSelectClause())));
    SelectClause select = builder.toSelectClause();
    System.out.println(select.toPrepared());
    System.out.println(select.getParams());
  }

  @Test
  public void testBuild5() {
    /*
     * SELECT DISTINCT customers.customer_id, customers.customer_name FROM
     * customers INNER JOIN orders ON customers.customer_id = orders.customer_id
     */
    MultipleSelectionBuilder selectionBuilder = new MultipleSelectionBuilder();
    TableColumnName columnCustomerId = new TableColumnName("c", "customer_id");
    selectionBuilder.addDistinctExpression(columnCustomerId);
    selectionBuilder.addExpression(new TableColumnName("c", "customer_name"));
    SelectClauseBuilder selectBuilder = new SelectClauseBuilder();
    selectBuilder.select(selectionBuilder.toMultipleSelection());
    selectBuilder.from(new JoinWithConditionTable(new AkaTable(new SimpleTable(
        "customers"), "c"), JoinType.INNER, new SimpleTable("orders"),
        new EqualsCondition(columnCustomerId, new TableColumnName("orders",
            "customer_id"))));
    SelectClause select = selectBuilder.toSelectClause();
    System.out.println(select.toPrepared());
    System.out.println(select.getParams());
  }

  @Test
  public void testBuild6() {
    /*
     * SELECT customers.* FROM customers LEFT JOIN orders ON
     * customers.customer_id = orders.customer_id WHERE orders.customer_id IS
     * NULL
     */
    SelectClauseBuilder selectBuilder = new SelectClauseBuilder();
    selectBuilder.select(new TableAsteriskSelection("c"));
    selectBuilder.from(new JoinWithConditionTable(new AkaTable(new SimpleTable(
        "customers"), "c"), JoinType.LEFT, new SimpleTable("orders"),
        new IsNullCondition(new TableColumnName("orders", "customer_id"))));
    SelectClause select = selectBuilder.toSelectClause();
    System.out.println(select.toPrepared());
    System.out.println(select.getParams());
  }

}
