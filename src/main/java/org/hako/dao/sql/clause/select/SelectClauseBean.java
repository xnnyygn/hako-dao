package org.hako.dao.sql.clause.select;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.orderby.OrderBy;
import org.hako.dao.sql.clause.select.selection.AsteriskSelection;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.condition.Condition;

public class SelectClauseBean {

  private Option<Selection> selectionOpt = new Some<Selection>(
      new AsteriskSelection());
  private Option<Table> tableOpt = new None<Table>();
  private Option<Condition> whereCondOpt = new None<Condition>();
  private Option<OrderBy> orderByOpt = new None<OrderBy>();
  private Option<Limit> limitOpt = new None<Limit>();

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

  /**
   * @return the orderByOpt
   */
  public Option<OrderBy> getOrderByOpt() {
    return orderByOpt;
  }

  /**
   * @param orderByOpt the orderByOpt to set
   */
  public void setOrderByOpt(Option<OrderBy> orderByOpt) {
    this.orderByOpt = orderByOpt;
  }

  public boolean hasOrderBy() {
    return orderByOpt.hasValue();
  }

  public OrderBy getOrderBy() {
    return orderByOpt.get();
  }

  /**
   * @return the limitOpt
   */
  public Option<Limit> getLimitOpt() {
    return limitOpt;
  }

  /**
   * @param limitOpt the limitOpt to set
   */
  public void setLimitOpt(Option<Limit> limitOpt) {
    this.limitOpt = limitOpt;
  }

  public boolean hasLimit() {
    return limitOpt.hasValue();
  }
  
  public Limit getLimit(){
    return limitOpt.get();
  }

}
