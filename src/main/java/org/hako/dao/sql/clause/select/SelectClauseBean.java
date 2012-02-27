package org.hako.dao.sql.clause.select;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.selection.AsteriskSelection;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.condition.Condition;

public class SelectClauseBean {

  private Option<Selection> selectionOpt = new Some<Selection>(new AsteriskSelection());
  private Option<Table> tableOpt = new None<Table>();
  private Option<Condition> whereCondOpt = new None<Condition>();

  public Option<Selection> getSelectionOpt() {
    return selectionOpt;
  }

  public void setSelectionOpt(Option<Selection> selectionOpt) {
    this.selectionOpt = selectionOpt;
  }

  public boolean hasSelection() {
    return selectionOpt.hasValue();
  }

  public Selection getSelection() {
    return selectionOpt.get();
  }

  public Option<Table> getTableOpt() {
    return tableOpt;
  }

  public Table getTable() {
    return tableOpt.get();
  }

  public boolean hasTable() {
    return tableOpt.hasValue();
  }

  public void setTableOpt(Option<Table> tableOpt) {
    this.tableOpt = tableOpt;
  }

  public boolean hasWhereCond() {
    return whereCondOpt.hasValue();
  }

  public Condition getWhereCond() {
    return whereCondOpt.get();
  }

  public Option<Condition> getWhereCondOpt() {
    return whereCondOpt;
  }

  public void setWhereCondOpt(Option<Condition> whereCondOpt) {
    this.whereCondOpt = whereCondOpt;
  }

}
