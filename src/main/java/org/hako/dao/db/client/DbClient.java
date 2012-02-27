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

import java.util.List;
import java.util.Map;

import org.hako.Option;
import org.hako.dao.HakoDaoException;
import org.hako.dao.sql.clause.delete.DeleteClause;
import org.hako.dao.sql.clause.insert.InsertClause;
import org.hako.dao.sql.clause.select.SelectClause;
import org.hako.dao.sql.clause.update.UpdateClause;

/**
 * Database client. Do basic SQL operations such as SELECT, INSERT and so on.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public interface DbClient {

  /**
   * Select multiple rows and get list of properties.
   * 
   * @param clause
   * @return list of properties
   * @throws HakoDaoException if dao error occurred
   */
  public List<Map<String, Object>> selectMultipleRows(SelectClause clause) throws HakoDaoException;

  /**
   * Select single row and get properties.
   * 
   * @param clause
   * @return some properties or none
   */
  public Option<Map<String, Object>> selectSingleRow(SelectClause clause);

  /**
   * Select single value result. For instance, count rows.
   * 
   * @param clause
   * @return some object or none
   */
  public Option<Object> selectObject(SelectClause clause);

  /**
   * Perform SQL insert operation.
   * 
   * @param clause
   * @return count of inserted records
   */
  public int insert(InsertClause clause);

  /**
   * Insert and get auto generated value, for example, auto incremented id.
   * 
   * @param clause
   * @return auto generated value
   */
  public Object insertAndGet(InsertClause clause);

  /**
   * Perform SQL update operation.
   * 
   * @param clause
   * @return count of updated records
   */
  public int update(UpdateClause clause);

  /**
   * Perform SQL delete operation.
   * 
   * @param clause
   * @return count of deleted records
   */
  public int delete(DeleteClause clause);
  
}
