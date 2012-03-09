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

/**
 * Simple join table.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SimpleJoinTable extends AbstractTable {

  protected final Table table;
  protected final JoinType joinType;
  protected final Table joinTable;

  /**
   * Create with inner join.
   * 
   * @param table
   * @param joinTable
   */
  public SimpleJoinTable(Table table, Table joinTable) {
    this(table, JoinType.INNER, joinTable);
  }

  /**
   * Create.
   * 
   * @param table
   * @param joinType
   * @param joinTable
   */
  public SimpleJoinTable(Table table, JoinType joinType, Table joinTable) {
    super();
    this.table = table;
    this.joinType = joinType;
    this.joinTable = joinTable;
  }

  public String toPrepared() {
    return table.toPrepared() + " " + joinType.toString() + " JOIN "
        + joinTable.toPrepared();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    all.addAll(table.getParams());
    all.addAll(joinTable.getParams());
    return all;
  }

}
