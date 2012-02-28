package org.hako.dao.sql.clause.select.selection;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;

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
