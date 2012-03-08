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

import java.util.List;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.clause.AbstractClause;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Delete clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class DeleteClause extends AbstractClause {

  private final String tableName;
  private final Option<Condition> whereCondOpt;

  /**
   * Delete all from table.
   * 
   * @param tableName table name
   */
  public DeleteClause(String tableName) {
    this(tableName, new None<Condition>());
  }

  /**
   * Delete with condition.
   * 
   * @param tableName table name
   * @param whereCond where condition
   */
  public DeleteClause(String tableName, Condition whereCond) {
    this(tableName, new Some<Condition>(whereCond));
  }

  /**
   * Create.
   * 
   * @param tableName
   * @param whereCondOpt
   */
  public DeleteClause(String tableName, Option<Condition> whereCondOpt) {
    super();
    this.tableName = tableName;
    this.whereCondOpt = whereCondOpt;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder("DELETE FROM ");
    builder.append(tableName);
    appendOptionToPrepared(" WHERE ", whereCondOpt, builder);
    return builder.toString();
  }

  public List<Object> getParams() {
    return whereCondOpt.hasValue() ? whereCondOpt.get().getParams() : NO_PARAM;
  }

  public String toString() {
    return "DELETE " + super.toString();
  }

}
