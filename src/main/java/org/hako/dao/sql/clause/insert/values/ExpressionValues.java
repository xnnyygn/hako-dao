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
package org.hako.dao.sql.clause.insert.values;

import java.util.List;

import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.util.SqlUtils;

/**
 * Expression values.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ExpressionValues extends AbstractValueSource {

  private final List<Expression> expressions;

  /**
   * Create.
   * 
   * @param expressions
   * @throws IllegalArgumentException if expressions is empty
   */
  public ExpressionValues(List<Expression> expressions)
      throws IllegalArgumentException {
    super();
    if (expressions.isEmpty()) {
      throw new IllegalArgumentException("expressions cannot be empty");
    }
    this.expressions = expressions;
  }

  public String toPrepared() {
    return new ToPreparedBuilder("VALUES (").append(expressions).append(')')
        .toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    logMarginCount(marginCount);
    return new ToFormattedBuilder().append(marginCount, ",\n", expressions)
        .toString();
  }

  public List<Object> getParams() {
    return SqlUtils.getParams(expressions);
  }

}
