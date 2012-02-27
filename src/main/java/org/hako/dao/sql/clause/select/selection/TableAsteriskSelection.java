package org.hako.dao.sql.clause.select.selection;

public class TableAsteriskSelection extends AsteriskSelection {

  private final String tableAlias;

  /**
   * Create.
   * 
   * @param tableAlias
   */
  public TableAsteriskSelection(String tableAlias) {
    super();
    this.tableAlias = tableAlias;
  }

  @Override
  public String toPrepared() {
    return tableAlias + "." + super.toPrepared();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result =
        prime * result + ((tableAlias == null) ? 0 : tableAlias.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof TableAsteriskSelection)) return false;
    TableAsteriskSelection other = (TableAsteriskSelection) obj;
    if (tableAlias == null) {
      if (other.tableAlias != null) return false;
    } else if (!tableAlias.equals(other.tableAlias)) return false;
    return true;
  }

}
