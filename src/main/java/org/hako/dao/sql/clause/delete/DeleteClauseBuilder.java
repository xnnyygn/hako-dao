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
package org.hako.dao.sql.clause.delete;

import java.util.Arrays;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;

/**
 * Builder of {@link DeleteClause}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class DeleteClauseBuilder {

  private Option<String> tableNameOpt = new None<String>();
  private Option<Condition> whereCondOpt = new None<Condition>();

  /**
   * Set table name.
   * 
   * @param tableName table name
   * @return this
   */
  public DeleteClauseBuilder deleteFrom(String tableName) {
    tableNameOpt = new Some<String>(tableName);
    return this;
  }

  /**
   * Set where condition.
   * 
   * @param condition
   * @return this
   */
  public DeleteClauseBuilder where(Condition... conditions) {
    whereCondOpt =
        new Some<Condition>(new MultipleAndCondition(Arrays.asList(conditions)));
    return this;
  }

  /**
   * Create delete clause.
   * 
   * @return delete clause
   * @throws IllegalArgumentException if table name not specified
   * @see DeleteClause#DeleteClause(String, Option)
   * @see #deleteFrom(String)
   */
  public DeleteClause toDeleteClause() throws IllegalArgumentException {
    if (!tableNameOpt.hasValue()) {
      throw new IllegalArgumentException("table name must be specified");
    }
    return new DeleteClause(tableNameOpt.get(), whereCondOpt);
  }

}
