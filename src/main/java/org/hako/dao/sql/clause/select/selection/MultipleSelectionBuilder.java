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

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;

/**
 * Multiple selection builder.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleSelectionBuilder {

  private final List<Selection> selections = new ArrayList<Selection>();

  public MultipleSelectionBuilder add(Selection selection) {
    selections.add(selection);
    return this;
  }

  public MultipleSelectionBuilder addExpression(Expression expression) {
    return add(new ExpressionSelection(expression));
  }

  public MultipleSelectionBuilder addExpressionAka(Expression expression,
      String alias) {
    return add(new ExpressionAkaSelection(expression, alias));
  }

  public MultipleSelectionBuilder addDistinctExpression(Expression expression) {
    return add(new DistinctExpressionSelection(expression));
  }

  public MultipleSelectionBuilder addTableAll(String tableAlias) {
    return add(new TableAliasAsteriskSelection(tableAlias));
  }

  public MultipleSelection toMultipleSelection() {
    return new MultipleSelection(selections);
  }
}
