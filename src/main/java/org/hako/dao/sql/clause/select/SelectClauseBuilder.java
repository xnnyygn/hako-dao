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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.orderby.ExpressionSingleOrderBy;
import org.hako.dao.sql.clause.select.orderby.IndexSingleOrderBy;
import org.hako.dao.sql.clause.select.orderby.MultipleOrderBy;
import org.hako.dao.sql.clause.select.orderby.OrderBy;
import org.hako.dao.sql.clause.select.selection.MultipleExpressionSelection;
import org.hako.dao.sql.clause.select.selection.MultipleSelection;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.table.AkaTable;
import org.hako.dao.sql.clause.select.table.JoinWithConditionTable;
import org.hako.dao.sql.clause.select.table.SimpleTable;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;

/**
 * Select clause builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SelectClauseBuilder {

  private final SelectClauseBean bean = new SelectClauseBean();
  private final List<OrderBy> orderBys = new ArrayList<OrderBy>();

  /**
   * Select multiple expressions.
   * 
   * @param expressions
   * @return this
   */
  public SelectClauseBuilder select(Expression... expressions) {
    return select(new MultipleExpressionSelection(Arrays.asList(expressions)));
  }

  /**
   * Set selection.
   * 
   * @param selections
   * @return this
   */
  public SelectClauseBuilder select(Selection... selections) {
    return select(Arrays.asList(selections));
  }

  /**
   * Set selection.
   * 
   * @param selections
   * @return this
   */
  public SelectClauseBuilder select(List<Selection> selections) {
    bean.setSelectionOpt(createSelection(selections));
    return this;
  }

  /**
   * Create selections.
   * 
   * @param selections
   * @return some selection or none
   */
  private Option<Selection> createSelection(List<Selection> selections) {
    switch (selections.size()) {
      case 0:
        return new None<Selection>();
      case 1:
        return new Some<Selection>(selections.get(0));
      default:
        return new Some<Selection>(new MultipleSelection(selections));
    }
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

  /**
   * Simple form and join.
   * 
   * @param leftTable
   * @param rightTable
   * @param condition
   * @return this
   */
  public SelectClauseBuilder fromJoin(Table leftTable, Table rightTable,
      Condition condition) {
    return from(new JoinWithConditionTable(leftTable, rightTable, condition));
  }

  /**
   * Add where clause.
   * 
   * @param conditions conditions
   * @return this
   */
  public SelectClauseBuilder where(Condition... conditions) {
    bean.setWhereCondOpt(createCondition(Arrays.asList(conditions)));
    return this;
  }

  /**
   * Create conditions.
   * 
   * @param conditions
   * @return some condition or not
   * @see MultipleAndCondition
   */
  private Option<Condition> createCondition(List<Condition> conditions) {
    switch (conditions.size()) {
      case 0:
        return new None<Condition>();
      case 1:
        return new Some<Condition>(conditions.get(0));
      default:
        return new Some<Condition>(new MultipleAndCondition(conditions));
    }
  }

  public SelectClauseBuilder addOrderBy(int index, boolean asc) {
    orderBys.add(new IndexSingleOrderBy(index, asc));
    return this;
  }

  public SelectClauseBuilder addOrderBy(Expression expression, boolean asc) {
    orderBys.add(new ExpressionSingleOrderBy(expression, asc));
    return this;
  }

  public SelectClauseBuilder limit(int max, int offset) {
    bean.setLimitOpt(new Some<Limit>(new Limit(max, offset)));
    return this;
  }

  public SelectClauseBuilder limit(int max) {
    return limit(max, 0);
  }

  public SelectClause toSelectClause() throws IllegalArgumentException {
    if (!orderBys.isEmpty()) {
      bean.setOrderByOpt(new Some<OrderBy>(new MultipleOrderBy(orderBys)));
    }
    return new SelectClause(bean);
  }

}
