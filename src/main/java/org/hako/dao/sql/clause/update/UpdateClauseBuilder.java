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
package org.hako.dao.sql.clause.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.clause.select.table.Tables;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;
import org.hako.dao.sql.expression.value.Values;

/**
 * Update clause builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UpdateClauseBuilder {

  private Option<Table> tableOpt = new None<Table>();
  private final List<ColumnExpressionPair> pairs =
      new ArrayList<ColumnExpressionPair>();
  private Option<Condition> whereCondOpt = new None<Condition>();

  /**
   * Set table name.
   * 
   * @param tableName
   * @return this
   */
  public UpdateClauseBuilder update(String tableName) {
    return update(Tables.createSimple(tableName));
  }

  /**
   * Set table name with alias.
   * 
   * @param tableName
   * @param alias
   * @return this
   */
  public UpdateClauseBuilder update(String tableName, String alias) {
    return update(Tables.createSimpleAkaTable(tableName, alias));
  }

  /**
   * Set table.
   * 
   * @param table
   * @return this
   */
  public UpdateClauseBuilder update(Table table) {
    tableOpt = new Some<Table>(table);
    return this;
  }

  /**
   * Set value.
   * 
   * @param columnName
   * @param obj
   * @return this
   */
  public UpdateClauseBuilder set(String columnName, Object obj) {
    return set(columnName, Values.create(obj));
  }

  /**
   * Set value.
   * 
   * @param columnName column name
   * @param expression expression
   * @return this
   */
  public UpdateClauseBuilder set(String columnName, Expression expression) {
    pairs.add(new ColumnExpressionPair(columnName, expression));
    return this;
  }

  /**
   * Set with column names and expressions.
   * 
   * @param columnNames column names
   * @param expressions expressions
   * @return this
   */
  public UpdateClauseBuilder set(String[] columnNames, Expression[] expressions) {
    int count = columnNames.length;
    if (count != expressions.length) {
      throw new IllegalArgumentException("column must has its expression, "
          + "please check expressions count");
    }
    List<ColumnExpressionPair> pairs = new ArrayList<ColumnExpressionPair>();
    for (int i = 0; i < count; i++) {
      pairs.add(new ColumnExpressionPair(columnNames[i], expressions[i]));
    }
    return set(pairs);
  }

  /**
   * Set with map.
   * 
   * @param columnExprMap
   * @return this
   */
  public UpdateClauseBuilder set(Map<String, Expression> columnExprMap) {
    List<ColumnExpressionPair> pairs = new ArrayList<ColumnExpressionPair>();
    for (Map.Entry<String, Expression> entry : columnExprMap.entrySet()) {
      pairs.add(new ColumnExpressionPair(entry.getKey(), entry.getValue()));
    }
    return set(pairs);
  }

  /**
   * Set with pairs.
   * 
   * @param pairs
   * @return this
   */
  public UpdateClauseBuilder set(ColumnExpressionPair... pairs) {
    return set(Arrays.asList(pairs));
  }

  /**
   * Set with pairs.
   * 
   * @param pairs
   * @return this
   */
  public UpdateClauseBuilder set(List<ColumnExpressionPair> pairs) {
    this.pairs.addAll(pairs);
    return this;
  }

  /**
   * Set where condition.
   * 
   * @param conditions
   * @return this
   */
  public UpdateClauseBuilder where(Condition... conditions) {
    // TODO refactor me
    whereCondOpt =
        new Some<Condition>(new MultipleAndCondition(Arrays.asList(conditions)));
    return this;
  }

  /**
   * Create update clause.
   * 
   * @return update clause
   * @throws IllegalArgumentException if table not specified
   */
  public UpdateClause toUpdateClause() throws IllegalArgumentException {
    if (!tableOpt.hasValue()) {
      throw new IllegalArgumentException("table required");
    }
    return new UpdateClause(tableOpt.get(), pairs, whereCondOpt);
  }

}
