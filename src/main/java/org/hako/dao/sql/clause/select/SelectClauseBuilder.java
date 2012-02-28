package org.hako.dao.sql.clause.select;

import java.util.ArrayList;
import java.util.List;

import org.hako.Some;
import org.hako.dao.sql.clause.select.orderby.ExpressionSingleOrderBy;
import org.hako.dao.sql.clause.select.orderby.IndexSingleOrderBy;
import org.hako.dao.sql.clause.select.orderby.MultipleOrderBy;
import org.hako.dao.sql.clause.select.orderby.OrderBy;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.AkaTable;
import org.hako.dao.sql.clause.select.table.SimpleTable;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;

public class SelectClauseBuilder {

  private final SelectClauseBean bean = new SelectClauseBean();
  private final List<OrderBy> orderBys = new ArrayList<OrderBy>();

  public SelectClauseBuilder select(Selection selection) {
    bean.setSelectionOpt(new Some<Selection>(selection));
    return this;
  }

  public SelectClauseBuilder from(String tableName, String alias) {
    return from(new AkaTable(new SimpleTable(tableName), alias));
  }

  public SelectClauseBuilder from(String tableName) {
    return from(new SimpleTable(tableName));
  }

  public SelectClauseBuilder from(Table table) {
    bean.setTableOpt(new Some<Table>(table));
    return this;
  }

  public SelectClauseBuilder where(Condition condition) {
    bean.setWhereCondOpt(new Some<Condition>(condition));
    return this;
  }

  public SelectClauseBuilder addOrderBy(int index, boolean asc,
      boolean nullsFirst) {
    orderBys.add(new IndexSingleOrderBy(index, asc, nullsFirst));
    return this;
  }

  public SelectClauseBuilder addOrderBy(Expression expression, boolean asc,
      boolean nullsFirst) {
    orderBys.add(new ExpressionSingleOrderBy(expression, asc, nullsFirst));
    return this;
  }

  public SelectClause toSelectClause() throws IllegalArgumentException {
    if (!orderBys.isEmpty()) {
      bean.setOrderByOpt(new Some<OrderBy>(new MultipleOrderBy(orderBys)));
    }
    return new SelectClause(bean);
  }

}
