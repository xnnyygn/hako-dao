package org.hako.dao.sql.expression.value;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.Expression;

public class DefaultValue implements Expression {

  public String toPrepared() {
    return "DEFAULT";
  }

  public List<Object> getParams() {
    return new ArrayList<Object>(0);
  }

}
