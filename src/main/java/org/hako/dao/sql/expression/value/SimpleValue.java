package org.hako.dao.sql.expression.value;

import java.util.Arrays;
import java.util.List;

import org.hako.dao.sql.expression.Expression;

public abstract class SimpleValue implements Expression {

  protected final Object value;

  /**
   * Create.
   * 
   * @param value
   */
  public SimpleValue(Object value) {
    super();
    this.value = value;
  }

  public String toPrepared() {
    return "?";
  }

  public List<Object> getParams() {
    return Arrays.asList(value);
  }
  
}
