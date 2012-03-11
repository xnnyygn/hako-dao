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

import org.hako.Some;
import org.hako.dao.sql.clause.select.orderby.ExpressionSingleOrderBy;
import org.hako.dao.sql.clause.select.orderby.IndexSingleOrderBy;
import org.hako.dao.sql.clause.select.orderby.MultipleOrderBy;
import org.hako.dao.sql.clause.select.orderby.OrderBy;
import org.hako.dao.sql.clause.select.selection.ExpressionSelection;
import org.hako.dao.sql.clause.select.selection.MultipleExpressionSelection;
import org.hako.dao.sql.clause.select.selection.Selection;
import org.hako.dao.sql.clause.select.selection.Selections;
import org.hako.dao.sql.clause.select.table.AkaTable;
import org.hako.dao.sql.clause.select.table.JoinWithConditionTable;
import org.hako.dao.sql.clause.select.table.SimpleTable;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.AkaExpression;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.InnerSelectExpression;
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
    if (expressions.length == 1) {
      return select(new ExpressionSelection(expressions[0]));
    }
    return select(new MultipleExpressionSelection(Arrays.asList(expressions)));
  }

  /**
   * Select single column as.
   * 
   * @param expression
   * @param alias
   * @return this
   */
  public SelectClauseBuilder selectAs(Expression expression, String alias) {
    return select(new ExpressionSelection(new AkaExpression(expression, alias)));
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
    bean.setSelectionOpt(new Some<Selection>(Selections.create(selections)));
    return this;
  }

  /**
   * Set from table with alias.
   * 
   * @param tableName table name
   * @param alias
   * @return this
   */
  public SelectClauseBuilder from(String tableName, String alias) {
    return from(new AkaTable(new SimpleTable(tableName), alias));
  }

  /**
   * Set from table.
   * 
   * @param tableName
   * @return this
   */
  public SelectClauseBuilder from(String tableName) {
    return from(new SimpleTable(tableName));
  }

  /**
   * Set from table.
   * 
   * @param table
   * @return this
   */
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
    Condition c =
        conditions.length == 1 ? conditions[0] : new MultipleAndCondition(
            Arrays.asList(conditions));
    bean.setWhereCondOpt(new Some<Condition>(c));
    return this;
  }

  /**
   * Add having clause.
   * 
   * @param conditions
   * @return this
   */
  public SelectClauseBuilder having(Condition... conditions) {
    bean.setHavingOpt(new Some<Having>(new Having(new MultipleAndCondition(
        Arrays.asList(conditions)))));
    return this;
  }

  /**
   * Add group by clause.
   * 
   * @param expressions
   * @return this
   */
  public SelectClauseBuilder groupBy(Expression... expressions) {
    bean.setGroupByOpt(new Some<GroupBy>(new GroupBy(expressions)));
    return this;
  }

  /**
   * Add order by.
   * 
   * @param index
   * @param asc
   * @return this
   */
  public SelectClauseBuilder addOrderBy(int index, boolean asc) {
    orderBys.add(new IndexSingleOrderBy(index, asc));
    return this;
  }

  /**
   * Add order by.
   * 
   * @param expression
   * @param asc
   * @return this
   */
  public SelectClauseBuilder addOrderBy(Expression expression, boolean asc) {
    orderBys.add(new ExpressionSingleOrderBy(expression, asc));
    return this;
  }

  /**
   * Limit.
   * 
   * @param max
   * @param offset
   * @return this
   */
  public SelectClauseBuilder limit(int max, int offset) {
    bean.setLimitOpt(new Some<Limit>(new Limit(max, offset)));
    return this;
  }

  /**
   * Limit.
   * 
   * @param max
   * @return this
   */
  public SelectClauseBuilder limit(int max) {
    return limit(max, 0);
  }

  /**
   * Create select clause.
   * 
   * @return select clause
   * @throws IllegalArgumentException
   * @see SelectClause
   */
  public SelectClause toSelectClause() throws IllegalArgumentException {
    if (!orderBys.isEmpty()) {
      OrderBy orderBy =
          orderBys.size() == 1 ? orderBys.get(0)
              : new MultipleOrderBy(orderBys);
      bean.setOrderByOpt(new Some<OrderBy>(orderBy));
    }
    return new SelectClause(bean);
  }

  /**
   * Create inner select expression.
   * 
   * @return inner select expression
   * @throws IllegalArgumentException
   * @see InnerSelectExpression
   */
  public InnerSelectExpression toInnerSelectExpr()
      throws IllegalArgumentException {
    return new InnerSelectExpression(toSelectClause());
  }

  /**
   * Create inner select selection.
   * 
   * @return expression selection
   * @see ExpressionSelection
   */
  public ExpressionSelection toInnerSelectSelection() {
    return new ExpressionSelection(toInnerSelectExpr());
  }

}
