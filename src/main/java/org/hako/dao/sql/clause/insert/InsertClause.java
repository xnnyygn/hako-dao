package org.hako.dao.sql.clause.insert;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hako.dao.sql.Clause;
import org.hako.dao.sql.clause.insert.values.Values;

public class InsertClause implements Clause {

  private final String tableName;
  private final List<String> columnNames;
  private final Values values;

  /**
   * Create.
   * 
   * @param tableName
   * @param columnNames
   * @param values
   */
  public InsertClause(String tableName, List<String> columnNames, Values values) {
    super();
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.values = values;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder("INSERT INTO ");
    builder.append(tableName).append(" (")
        .append(StringUtils.join(columnNames, ", ")).append(") ")
        .append(values.toPrepared());
    return builder.toString();
  }

  public List<Object> getParams() {
    return values.getParams();
  }

}
