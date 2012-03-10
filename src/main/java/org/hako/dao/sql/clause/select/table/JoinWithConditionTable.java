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
package org.hako.dao.sql.clause.select.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Join with condition table.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class JoinWithConditionTable extends SimpleJoinTable {

  protected final Condition condition;

  /**
   * Create.
   * 
   * @param table
   * @param joinType
   * @param joinTable
   */
  public JoinWithConditionTable(Table table, Table joinTable,
      Condition condition) {
    super(table, joinTable);
    this.condition = condition;
  }

  /**
   * Create.
   * 
   * @param table
   * @param joinType
   * @param joinTable
   */
  public JoinWithConditionTable(Table table, JoinType joinType,
      Table joinTable, Condition condition) {
    super(table, joinType, joinTable);
    this.condition = condition;
  }

  @Override
  public String toPrepared() {
    // TODO add ToPreparedBuilder
    return new StringBuilder(super.toPrepared()).append(" ON ")
        .append(condition.toPrepared()).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    // TODO use string builder
    int niceDisplayOfCondition = marginCount + 2;
    return super.toFormatted(marginCount) + '\n'
        + StringUtils.repeat(MARGIN, niceDisplayOfCondition) + "ON "
        + condition.toFormatted(0);
  }

  @Override
  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>(super.getParams());
    all.addAll(condition.getParams());
    return all;
  }

}
