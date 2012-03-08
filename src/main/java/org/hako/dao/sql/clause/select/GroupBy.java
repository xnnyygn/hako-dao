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

import java.util.Arrays;
import java.util.List;

import org.hako.dao.sql.Sql;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.util.MultipleSqlUtils;

/**
 * Group by sub clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class GroupBy implements SelectOnlySql {

  private final List<Expression> expressions;

  /**
   * Create.
   * 
   * @param expressions
   */
  public GroupBy(Expression... expressions) {
    this(Arrays.asList(expressions));
  }

  /**
   * Create.
   * 
   * @param expressions
   * @throws IllegalArgumentException if expressions is empty
   */
  public GroupBy(List<Expression> expressions) throws IllegalArgumentException {
    super();
    if (expressions.isEmpty()) {
      throw new IllegalArgumentException("expressions cannot be emtpy");
    }
    this.expressions = expressions;
  }

  public String toPrepared() {
    return MultipleSqlUtils.toPrepared(expressions.toArray(new Sql[0]));
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.getParams(expressions);
  }

}
