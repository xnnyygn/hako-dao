package org.hako.dao.sql.expression;

public class TableColumnName extends ColumnName {

  private final String tableAlias;

  /**
   * Create.
   * 
   * @param tableAlias table alias
   * @param name
   */
  public TableColumnName(String tableAlias, String name) {
    super(name);
    this.tableAlias = tableAlias;
  }

  @Override
  public String toPrepared() {
    return tableAlias + "." + super.toPrepared();
  }


}
