package org.hako.dao.sql.clause.select;

import org.hako.Some;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.AkaTable;
import org.hako.dao.sql.clause.select.table.SimpleTable;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.condition.Condition;

public class SelectClauseBuilder {

  private final SelectClauseBean bean = new SelectClauseBean();

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

  public SelectClause toSelectClause() throws IllegalArgumentException {
    return new SelectClause(bean);
  }

}
