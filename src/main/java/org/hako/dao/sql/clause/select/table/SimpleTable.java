package org.hako.dao.sql.clause.select.table;

import java.util.List;

public class SimpleTable implements Table {

  protected final String name;

  /**
   * Create.
   * 
   * @param name
   */
  public SimpleTable(String name) {
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
