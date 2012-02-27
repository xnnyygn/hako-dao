package org.hako.dao.sql.clause.select.table;

import java.util.Arrays;
import java.util.List;

import org.hako.dao.sql.Sql;
import org.hako.dao.sql.util.MultipleSqlUtils;

public class MultipleTable implements Table {

  private final List<Table> tables;

  /**
   * Create.
   * 
   * @param tables
   */
  public MultipleTable(Table... tables) {
    this(Arrays.asList(tables));
  }

  /**
   * Create.
   * 
   * @param tables
   */
  public MultipleTable(List<Table> tables) {
    super();
    this.tables = tables;
  }

  public String toPrepared() {
    return MultipleSqlUtils.toPrepared(tables.toArray(new Sql[0]));
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.getParams(tables.toArray(new Sql[0]));
  }
}
