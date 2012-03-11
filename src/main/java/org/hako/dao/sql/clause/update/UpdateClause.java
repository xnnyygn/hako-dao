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
package org.hako.dao.sql.clause.update;

import java.util.List;

import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.clause.AbstractClause;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Update clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UpdateClause extends AbstractClause {

  private final UpdateClauseBean bean;

  /**
   * Create.
   * 
   * @param bean
   * @throws IllegalArgumentException if no table name or empty column value
   *         pair in bean
   */
  public UpdateClause(UpdateClauseBean bean) throws IllegalArgumentException {
    super();
    if (!bean.hasTableName() || bean.getPairs().isEmpty()) {
      throw new IllegalArgumentException("table name must be specified and "
          + "column value pair must not be empty");
    }
    this.bean = bean;
  }

  public String toPrepared() {
    ToPreparedBuilder builder = new ToPreparedBuilder("UPDATE ");
    builder.append(bean.getTableName());
    builder.append(" AS ", bean.getTableAliasOpt());
    builder.append(" SET ").append(bean.getPairs());
    builder.append(" WHERE ", bean.getWhereCondOpt());
    return builder.toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    ToFormattedBuilder builder = new ToFormattedBuilder("UPDATE ");
    builder.append(bean.getTableName());
    builder.append(" AS ", bean.getTableNameOpt());
    builder.append("\nSET\n").append(1, ",\n", bean.getPairs());
    builder.append("\nWHERE\n", 1, bean.getWhereCondOpt());
    return builder.toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(bean.getPairs(), bean.getWhereCondOpt());
  }

  public String toString() {
    return "UPDATE " + super.toString();
  }

}
