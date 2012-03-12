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

import java.util.List;

import org.hako.Option;
import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.clause.AbstractClause;
import org.hako.dao.sql.clause.select.table.Table;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Update clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UpdateClause extends AbstractClause {

  private final Table table;
  private final List<ColumnExpressionPair> pairs;
  private final Option<Condition> whereCondOpt;


  /**
   * Create.
   * 
   * @param table
   * @param pairs
   * @param whereCondOpt
   */
  public UpdateClause(Table table, List<ColumnExpressionPair> pairs,
      Option<Condition> whereCondOpt) throws IllegalArgumentException {
    super();
    if (pairs.isEmpty()) {
      throw new IllegalArgumentException("column value pair must not be empty");
    }
    this.table = table;
    this.pairs = pairs;
    this.whereCondOpt = whereCondOpt;
  }

  public String toPrepared() {
    ToPreparedBuilder builder = new ToPreparedBuilder("UPDATE ");
    builder.append(table);
    builder.append(" SET ").append(pairs);
    builder.append(" WHERE ", whereCondOpt);
    return builder.toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    ToFormattedBuilder builder = new ToFormattedBuilder("UPDATE ");
    builder.append(table);
    builder.append("\nSET\n").append(1, ",\n", pairs);
    builder.append("\nWHERE\n", 1, whereCondOpt);
    return builder.toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(pairs, whereCondOpt);
  }

  public String toString() {
    return "UPDATE " + super.toString();
  }

}
