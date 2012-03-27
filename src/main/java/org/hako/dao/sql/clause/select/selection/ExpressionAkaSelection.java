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
package org.hako.dao.sql.clause.select.selection;

import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.expression.Expression;

/**
 * Expression aka selection.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ExpressionAkaSelection extends ExpressionSelection {

  private final String alias;

  /**
   * Create.
   * 
   * @param expression
   * @param alias
   */
  public ExpressionAkaSelection(Expression expression, String alias) {
    super(expression);
    this.alias = alias;
  }

  @Override
  public String toPrepared() {
    return new ToPreparedBuilder(super.toPrepared()).append(" AS ")
        .append(alias).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    return new ToFormattedBuilder(super.toFormatted(marginCount))
        .append(" AS ").append(alias).toString();
  }

}
