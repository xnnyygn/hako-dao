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
package org.hako.dao.sql;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.walker.PreparedSqlFactory;
import org.hako.dao.sql.walker.SqlFormatter;

/**
 * Core object SQL. A structured object mapping of RDBMS SQL.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public interface Sql {

  /**
   * No parameter.
   */
  public static final List<Object> NO_PARAM = new ArrayList<Object>(0);
  /**
   * Margin used in format SQL.
   */
  public static final String MARGIN = "    ";

  /**
   * Generate prepared SQL.
   * 
   * @return prepared SQL
   */
  public String toPrepared();

  /**
   * Delegate work to {@link PreparedSqlFactory}.
   * 
   * @param factory
   * @return prepared SQL
   * @see PreparedSqlFactory
   */
  public String toPrepared(PreparedSqlFactory factory);

  /**
   * Generate formated SQL.
   * 
   * @return formatted SQL
   */
  public String toFormatted();

  /**
   * Generate formated SQL.
   * 
   * @param depth
   * @return formatted SQL
   */
  public String toFormatted(int depth);

  /**
   * Delegate work to {@link SqlFormatter}.
   * 
   * @param formatter
   * @return formatted SQL
   * @see SqlFormatter
   */
  public String toFormatted(SqlFormatter formatter);

  /**
   * Collect parameters.
   * 
   * @return parameters
   */
  public List<Object> getParams();

}
