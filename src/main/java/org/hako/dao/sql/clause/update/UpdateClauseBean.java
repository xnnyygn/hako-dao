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

import java.util.ArrayList;
import java.util.List;

import org.hako.None;
import org.hako.Option;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Update clause bean.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UpdateClauseBean {

  private Option<String> tableNameOpt = new None<String>();
  private Option<String> tableAliasOpt = new None<String>();
  private List<ColumnExpressionPair> pairs = new ArrayList<ColumnExpressionPair>();
  private Option<Condition> whereCondOpt = new None<Condition>();

  public Option<String> getTableNameOpt() {
    return tableNameOpt;
  }

  public void setTableNameOpt(Option<String> tableNameOpt) {
    this.tableNameOpt = tableNameOpt;
  }

  public boolean hasTableName() {
    return tableNameOpt.hasValue();
  }

  public String getTableName() {
    return tableNameOpt.get();
  }

  public Option<String> getTableAliasOpt() {
    return tableAliasOpt;
  }

  public void setTableAliasOpt(Option<String> tableAliasOpt) {
    this.tableAliasOpt = tableAliasOpt;
  }

  public boolean hasTableAlias() {
    return tableAliasOpt.hasValue();
  }

  public String getTableAlias() {
    return tableAliasOpt.get();
  }

  public List<ColumnExpressionPair> getPairs() {
    return pairs;
  }

  public void setPairs(List<ColumnExpressionPair> pairs) {
    this.pairs = pairs;
  }

  public Option<Condition> getWhereCondOpt() {
    return whereCondOpt;
  }

  public void setWhereCondOpt(Option<Condition> whereCondOpt) {
    this.whereCondOpt = whereCondOpt;
  }

  public boolean hasWhereCond() {
    return whereCondOpt.hasValue();
  }

  public Condition getWhereCond() {
    return whereCondOpt.get();
  }
  
}
