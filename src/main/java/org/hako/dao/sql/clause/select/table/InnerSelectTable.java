package org.hako.dao.sql.clause.select.table;

import org.hako.dao.sql.InnerSelect;
import org.hako.dao.sql.clause.select.SelectClause;

public class InnerSelectTable extends InnerSelect implements Table {

  /**
   * Create.
   * 
   * @param select
   */
  public InnerSelectTable(SelectClause select) {
    super(select);
  }

}
