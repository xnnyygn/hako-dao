/**
 * Copyright 2012 XnnYygn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.hako.dao.sql.clause.select;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.orderby.OrderBy;
import org.hako.dao.sql.clause.select.selection.AsteriskSelection;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Select clause bean.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SelectClauseBean {

  private Option<Selection> selectionOpt = new Some<Selection>(
      new AsteriskSelection());
  private Option<Table> tableOpt = new None<Table>();
  private Option<Condition> whereCondOpt = new None<Condition>();
  private Option<GroupBy> groupByOpt = new None<GroupBy>();
  private Option<Having> havingOpt = new None<Having>();
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

  public Limit getLimit() {
    return limitOpt.get();
  }

  /**
   * Set group by.
   * 
   * @param groupByOpt
   */
  public void setGroupByOpt(Option<GroupBy> groupByOpt) {
    this.groupByOpt = groupByOpt;
  }

  /**
   * Get group by option.
   * 
   * @return group by option
   */
  public Option<GroupBy> getGroupByOpt() {
    return groupByOpt;
  }

  /**
   * Check if has group by.
   * 
   * @return true if has, otherwise false
   */
  public boolean hasGroupBy() {
    return groupByOpt.hasValue();
  }

  /**
   * Get group by.
   * 
   * @return group by
   */
  public GroupBy getGroupBy() {
    return groupByOpt.get();
  }

  /**
   * Get having option.
   * 
   * @return the havingOpt
   */
  public Option<Having> getHavingOpt() {
    return havingOpt;
  }

  /**
   * Set having option.
   * 
   * @param havingOpt the havingOpt to set
   */
  public void setHavingOpt(Option<Having> havingOpt) {
    this.havingOpt = havingOpt;
  }
  
}
