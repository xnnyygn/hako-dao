package org.hako.dao.sql.expression;

import java.util.List;


public class ColumnName implements Expression {

  protected final String name;

  /**
   * Create.
   * 
   * @param name
   */
  public ColumnName(String name) {
    super();
    this.name = name;
  }

  public String toPrepared() {
    return name;
  }

  public List<Object> getParams() {
    return NO_PARAM;
  }

}
