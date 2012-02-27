package org.hako.dao.sql;

import java.util.List;

import org.hako.dao.sql.clause.select.SelectClause;

public class InnerSelect {
  
  protected final SelectClause select;

  /**
   * Create.
   * 
   * @param select
   */
  public InnerSelect(SelectClause select) {
    super();
    this.select = select;
  }

  public String toPrepared() {
    return "( " + select.toPrepared() + " )";
  }

  public List<Object> getParams() {
    return select.getParams();
  }
  
}
