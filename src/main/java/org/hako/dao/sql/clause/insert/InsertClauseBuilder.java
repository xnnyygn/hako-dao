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
package org.hako.dao.sql.clause.insert;

import java.util.ArrayList;
import java.util.List;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.insert.values.ExpressionValues;
import org.hako.dao.sql.clause.insert.values.Values;
import org.hako.dao.sql.expression.Expression;

/**
 * Insert clause builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class InsertClauseBuilder {

  private final String tableName;
  private final List<String> columnNames = new ArrayList<String>();
  private Option<Values> valuesOpt = new None<Values>();
  private final List<Expression> expressions = new ArrayList<Expression>();

  /**
   * Create.
   * 
   * @param tableName
   */
  public InsertClauseBuilder(String tableName) {
    super();
    this.tableName = tableName;
  }

  public InsertClauseBuilder addColumn(String columnName) {
    columnNames.add(columnName);
    return this;
  }

  public InsertClauseBuilder values(Values values) {
    valuesOpt = new Some<Values>(values);
    return this;
  }

  public InsertClauseBuilder addValue(Expression expression) {
    expressions.add(expression);
    return this;
  }

  public InsertClause toInsertClause() {
    return new InsertClause(tableName, columnNames, valuesOpt.hasValue()
        ? valuesOpt.get() : new ExpressionValues(expressions));
  }

}