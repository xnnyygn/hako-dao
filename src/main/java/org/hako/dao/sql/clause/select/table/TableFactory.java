package org.hako.dao.sql.clause.select.table;

public class TableFactory {

  public static SimpleTable createSimple(String tableName) {
    return new SimpleTable(tableName);
  }

  public static SchemaTable createSchemaTable(String schema, String tableName) {
    return new SchemaTable(schema, tableName);
  }

  public static AkaTable createAkaTable(Table table, String alias) {
    return new AkaTable(table, alias);
  }

  public static AkaTable createSimpleAkaTable(String tableName, String alias) {
    return new AkaTable(new SimpleTable(tableName), alias);
  }

}
