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

import java.util.List;

/**
 * Aka table.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class AkaTable extends AbstractTable {

  private final Table table;
  private final String alias;

  /**
   * Create.
   * 
   * @param name
   * @param alias
   * @see SimpleTable
   */
  public AkaTable(String name, String alias) {
    this(new SimpleTable(name), alias);
  }

  /**
   * Create.
   * 
   * @param table
   * @param alias
   */
  public AkaTable(Table table, String alias) {
    super();
    this.table = table;
    this.alias = alias;
  }

  public String toPrepared() {
    return table.toPrepared() + " AS " + alias;
  }

  public List<Object> getParams() {
    return table.getParams();
  }

}
