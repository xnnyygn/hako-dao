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
package org.hako.dao.sql.clause;

import org.hako.Option;
import org.hako.dao.sql.AbstractSql;
import org.hako.dao.sql.Clause;
import org.hako.dao.sql.Sql;

/**
 * Abstract clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public abstract class AbstractClause extends AbstractSql implements Clause {

  /**
   * Invoke {@link Sql#toPrepared()} on SQL option with prefix, or just return
   * empty string.
   * 
   * @param prefix
   * @param objOpt object option
   * @return {@code prefix + objOpt.get().toPrepared()} or {@code ""}
   */
  protected String optionToPrepared(String prefix, Option<?> objOpt) {
    if (objOpt.hasValue()) {
      Object obj = objOpt.get();
      if (obj instanceof Sql) {
        return prefix + ((Sql) obj).toPrepared();
      }
    }
    return "";
  }

  /**
   * Append prepared SQL of option or do nothing.
   * 
   * @param prefix
   * @param objOpt object option
   * @param builder
   * @see #optionToPrepared(String, Option)
   */
  protected void appendOptionToPrepared(String prefix, Option<?> objOpt,
      StringBuilder builder) {
    builder.append(optionToPrepared(prefix, objOpt));
  }

  /**
   * Generate <code>SQL=this.toPrepared(), Parameters=this.getParams()</code>.
   */
  public String toString() {
    return "SQL=" + toPrepared() + ", Parameters=" + getParams();
  }
}
