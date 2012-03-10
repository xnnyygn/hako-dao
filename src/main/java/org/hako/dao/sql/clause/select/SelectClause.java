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
import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.clause.AbstractClause;
import org.hako.dao.sql.util.SqlUtils;

/**
 * Select clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SelectClause extends AbstractClause {

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
    builder.append(" FROM ").append(bean.getTable().toPrepared());
    appendOptionToPrepared(" WHERE ", bean.getWhereCondOpt(), builder);
    appendOptionToPrepared(" GROUP BY ", bean.getGroupByOpt(), builder);
    appendOptionToPrepared(" HAVING ", bean.getHavingOpt(), builder);
    appendOptionToPrepared(" ORDER BY ", bean.getOrderByOpt(), builder);
    return builder.toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    ToFormattedBuilder builder = new ToFormattedBuilder();
    int newMarginCount = marginCount + 1;
    builder.append("SELECT\n", newMarginCount, bean.getSelection(), "\n");
    builder.append("FROM\n", newMarginCount, bean.getTable(), "\n");
    builder.append("WHERE\n", newMarginCount, bean.getWhereCondOpt(), "\n");
    builder.append("GROUP BY\n", newMarginCount, bean.getGroupByOpt(), "\n");
    builder.append("HAVING\n", newMarginCount, bean.getHavingOpt(), "\n");
    builder.append("ORDER BY\n", newMarginCount, bean.getOrderByOpt(), "\n");
    return builder.toString();
  }

  public List<Object> getParams() {
    return SqlUtils.getParams(bean.getSelection(), bean.getTable(),
        bean.getWhereCondOpt(), bean.getGroupByOpt(), bean.getOrderByOpt(),
        bean.getLimitOpt());
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
    return "SELECT " + super.toString();
  }

}
