package org.hako.dao.sql.clause.select.table;

import java.util.List;

public class AkaTable implements Table {

  private final Table table;
  private final String alias;

  /**
   * Create.
   * 
   * @param table
   * @param alias
   */
  public AkaTable(Table table, String alias) {
    super();
    this.table = table;
    this.alias = alias;
  }

  public String toPrepared() {
    return table.toPrepared() + " AS " + alias;
  }

  public List<Object> getParams() {
    return table.getParams();
  }

}
