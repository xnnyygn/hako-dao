package org.hako.dao.sql.clause.select.table;


public class SchemaTable extends SimpleTable {

  protected final String schema;

  /**
   * Create.
   * 
   * @param schema
   * @param name
   */
  public SchemaTable(String schema, String name) {
    super(name);
    this.schema = schema;
  }

  public String toPrepared() {
    return schema + "." + name;
  }

}
