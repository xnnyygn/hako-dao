package org.hako.dao.sql.clause.select.table;

import java.util.ArrayList;
import java.util.List;

public class SimpleJoinTable implements Table {

  protected final Table table;
  protected final JoinType joinType;
  protected final Table joinTable;

  /**
   * Create with inner join.
   * 
   * @param table
   * @param joinTable
   */
  public SimpleJoinTable(Table table, Table joinTable) {
    this(table, JoinType.INNER, joinTable);
  }

  /**
   * Create.
   * 
   * @param table
   * @param joinType
   * @param joinTable
   */
  public SimpleJoinTable(Table table, JoinType joinType, Table joinTable) {
    super();
    this.table = table;
    this.joinType = joinType;
    this.joinTable = joinTable;
  }

  public String toPrepared() {
    return table.toPrepared() + " " + joinType.toString() + " JOIN "
        + joinTable.toPrepared();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    all.addAll(table.getParams());
    all.addAll(joinTable.getParams());
    return all;
  }

}
