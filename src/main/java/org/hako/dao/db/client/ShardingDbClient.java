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
package org.hako.dao.db.client;

import java.sql.PreparedStatement;

import org.hako.dao.db.DatabaseException;
import org.hako.dao.db.vendor.DbVendor;
import org.hako.dao.sql.Clause;
import org.hako.dao.sql.clause.select.SelectClause;

/**
 * A database client provide simple sharding feature.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ShardingDbClient extends DefaultDbClient {

  /**
   * Create.
   * 
   * @param connector
   * @param printSql
   */
  public ShardingDbClient(DbVendor connector, boolean printSql) {
    super(connector, printSql);
  }

  @Override
  protected PreparedStatement createPreparedStatement(Clause clause,
      boolean generateKey) throws DatabaseException {
    // TODO get named parameters from clause
    // TODO change table of clause
    return super.createPreparedStatement(clause, generateKey);
  }

  protected Clause updateClauseTable(Clause clause)
      throws IllegalArgumentException {
    // TODO update table of clause
    if (clause instanceof SelectClause) {

    }
    throw new IllegalArgumentException("unknown type of clause [" + clause
        + "]");
  }

}
