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
package org.hako.dao.sql.clause.select;

import java.util.List;

import org.hako.Option;
import org.hako.dao.sql.Clause;
import org.hako.dao.sql.util.MultipleSqlUtils;

/**
 * Select clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SelectClause implements Clause {

  private final SelectClauseBean bean;

  /**
   * Create.
   * 
   * @param bean
   * @throws IllegalArgumentException if selection and table is none in bean
   */
  public SelectClause(SelectClauseBean bean) throws IllegalArgumentException {
    super();
    if (!bean.hasSelection() || !bean.hasTable()) {
      throw new IllegalArgumentException("selection or table cannot be none");
    }
    this.bean = bean;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ").append(bean.getSelection().toPrepared());
    builder.append(" FROM ").append(bean.getTable().toPrepared()).toString();
    if (bean.hasWhereCond()) {
      builder.append(" WHERE ").append(bean.getWhereCond().toPrepared());
    }
    if (bean.hasOrderBy()) {
      builder.append(" ORDER BY ").append(bean.getOrderBy().toPrepared());
    }
    return builder.toString();
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.getParams(bean.getSelection(), bean.getTable(),
        bean.getWhereCondOpt(), bean.getOrderByOpt(), bean.getLimitOpt());
  }

  /**
   * Get limit option.
   * 
   * @return limit option
   */
  public Option<Limit> getLimitOpt() {
    return bean.getLimitOpt();
  }

  /**
   * Generate
   * <code>[SELECT Prepared SQL={@link #toPrepared()}, Parameters={@link #getParams()}]</code>
   * .
   */
  public String toString() {
    StringBuilder builder = new StringBuilder("[SELECT Prepared SQL=");
    builder.append(toPrepared()).append(", Parameters=");
    builder.append(getParams().toString()).append(']');
    return builder.toString();
  }

}
