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
import org.hako.dao.sql.clause.insert.values.ValueSource;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.value.Values;

/**
 * Insert clause builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class InsertClauseBuilder {

  private Option<String> tableNameOpt = new None<String>();
  private final List<String> columnNames = new ArrayList<String>();
  private Option<ValueSource> valuesOpt = new None<ValueSource>();
  private final List<Expression> expressions = new ArrayList<Expression>();

  /**
   * Default constructor.
   */
  public InsertClauseBuilder(){
    super();
  }
  
  /**
   * Create with table name.
   *  
   * @param tableName table name
   * @see #insertInto(String)
   */
  public InsertClauseBuilder(String tableName){
    super();
    insertInto(tableName);
  }
  
  /**
   * Set insert into table name.
   * 
   * @param tableName table name
   * @return this
   */
  public InsertClauseBuilder insertInto(String tableName) {
    tableNameOpt = new Some<String>(tableName);
    return this;
  }

  public InsertClauseBuilder addColumn(String columnName) {
    columnNames.add(columnName);
    return this;
  }

  public InsertClauseBuilder values(ValueSource values) {
    valuesOpt = new Some<ValueSource>(values);
    return this;
  }

  public InsertClauseBuilder addValue(Expression expression) {
    expressions.add(expression);
    return this;
  }

  /**
   * Add column name, value pair.
   * 
   * @param columnName column name
   * @param obj value
   * @return this
   */
  public InsertClauseBuilder add(String columnName, Object obj) {
    return add(columnName, Values.create(obj));
  }

  /**
   * Add column name, expression pair.
   * 
   * @param columnName column name
   * @param expression
   * @return this
   */
  public InsertClauseBuilder add(String columnName, Expression expression) {
    return addColumn(columnName).addValue(expression);
  }

  /**
   * Create insert clause.
   * 
   * @return insert clause
   * @throws IllegalArgumentException if table name not specified
   * @see #insertInto(String)
   * @see InsertClause#InsertClause(String, List, ValueSource)
   */
  public InsertClause toInsertClause() throws IllegalArgumentException {
    if (!tableNameOpt.hasValue()) {
      throw new IllegalArgumentException("table name must not be blank");
    }
    return new InsertClause(tableNameOpt.get(), columnNames,
        valuesOpt.hasValue() ? valuesOpt.get() : new ExpressionValues(
            expressions));
  }

}
