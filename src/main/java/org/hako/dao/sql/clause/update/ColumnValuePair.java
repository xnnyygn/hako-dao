package org.hako.dao.sql.clause.update;

import java.util.List;

import org.hako.dao.sql.expression.Expression;

public class ColumnValuePair implements UpdateOnlySql {

  private final String columnName;
  private final Expression expression;

  /**
   * Create.
   * 
   * @param columnName
   * @param expression
   */
  public ColumnValuePair(String columnName, Expression expression) {
    super();
    this.columnName = columnName;
    this.expression = expression;
  }

  public String toPrepared() {
    return columnName + " = " + expression.toPrepared();
  }

  public List<Object> getParams() {
    return expression.getParams();
  }

}
