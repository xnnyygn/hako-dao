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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hako.dao.sql.util.ToFormattedUtils;
import org.hako.dao.sql.walker.PreparedSqlFactory;
import org.hako.dao.sql.walker.SqlFormatter;


/**
 * Abstract implement of SQL.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public abstract class AbstractSql implements Sql {

  private static final Log logger = LogFactory.getLog(AbstractSql.class);

  public String toPrepared(PreparedSqlFactory factory) {
    return factory.from(this);
  }

  public String toFormatted() {
    return toFormatted(0);
  }

  public String toFormatted(int marginCount) {
    logMarginCount(marginCount);
    return ToFormattedUtils.margins(marginCount).append(toPrepared())
        .toString();
  }

  /**
   * Log format depth.
   * 
   * @param marginCount
   */
  protected void logMarginCount(int marginCount) {
    if (logger.isDebugEnabled()) {
      logger.debug(getClass().getSimpleName() + " format margin count ["
          + marginCount + "]");
    }
  }

  public String toFormatted(SqlFormatter formatter) {
    return formatter.format(this);
  }

}
