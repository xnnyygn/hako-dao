package org.hako.dao.sql.clause.select.table;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.sql.expression.condition.Condition;

public class JoinWithConditionTable extends SimpleJoinTable {

  protected final Condition condition;

  /**
   * Create.
   * 
   * @param table
   * @param joinType
   * @param joinTable
   */
  public JoinWithConditionTable(Table table, Table joinTable,
      Condition condition) {
    super(table, joinTable);
    this.condition = condition;
  }

  /**
   * Create.
   * 
   * @param table
   * @param joinType
   * @param joinTable
   */
  public JoinWithConditionTable(Table table, JoinType joinType,
      Table joinTable, Condition condition) {
    super(table, joinType, joinTable);
    this.condition = condition;
  }

  @Override
  public String toPrepared() {
    return super.toPrepared() + " ON " + condition.toPrepared();
  }

  @Override
  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>(super.getParams());
    all.addAll(condition.getParams());
    return all;
  }

}
