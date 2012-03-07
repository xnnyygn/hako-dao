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
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.value.Values;

/**
 * Update clause builder.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UpdateClauseBuilder {

  private final List<ColumnExpressionPair> pairs =
      new ArrayList<ColumnExpressionPair>();
  private final UpdateClauseBean bean = new UpdateClauseBean();

  public UpdateClauseBuilder update(String tableName) {
    return update(tableName, new None<String>());
  }

  public UpdateClauseBuilder update(String tableName, String alias) {
    return update(tableName, new Some<String>(alias));
  }

  private UpdateClauseBuilder update(String tableName, Option<String> aliasOpt) {
    bean.setTableNameOpt(new Some<String>(tableName));
    bean.setTableAliasOpt(aliasOpt);
    return this;
  }

  /**
   * Set value.
   * 
   * @param columnName
   * @param obj
   * @return this
   */
  public UpdateClauseBuilder set(String columnName, Object obj) {
    return set(columnName, Values.create(obj));
  }

  public UpdateClauseBuilder set(String columnName, Expression expression) {
    pairs.add(new ColumnExpressionPair(columnName, expression));
    return this;
  }

  @Deprecated
  public UpdateClauseBuilder set(String[] columnNames, Expression[] expressions) {
    int count = columnNames.length;
    if (count != expressions.length) {
      throw new IllegalArgumentException("column must has its expression, "
          + "please check expressions count");
    }
    List<ColumnExpressionPair> pairs = new ArrayList<ColumnExpressionPair>();
    for (int i = 0; i < count; i++) {
      pairs.add(new ColumnExpressionPair(columnNames[i], expressions[i]));
    }
    return set(pairs);
  }

  @Deprecated
  public UpdateClauseBuilder set(Map<String, Expression> columnValueMap) {
    List<ColumnExpressionPair> pairs = new ArrayList<ColumnExpressionPair>();
    for (Map.Entry<String, Expression> entry : columnValueMap.entrySet()) {
      pairs.add(new ColumnExpressionPair(entry.getKey(), entry.getValue()));
    }
    return set(pairs);
  }

  @Deprecated
  public UpdateClauseBuilder set(List<ColumnExpressionPair> pairs) {
    bean.setPairs(pairs);
    return this;
  }

  public UpdateClauseBuilder where(Condition whereCond) {
    bean.setWhereCondOpt(new Some<Condition>(whereCond));
    return this;
  }

  /**
   * Create update clause.
   * 
   * @return update clause
   */
  public UpdateClause toUpdateClause() {
    bean.setPairs(pairs);
    return new UpdateClause(bean);
  }
  
}
