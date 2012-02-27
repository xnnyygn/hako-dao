package org.hako.dao.sql.clause.insert.values;

import java.util.List;

import org.hako.dao.sql.clause.select.SelectClause;

public class InnerSelectValues implements Values {

  private final boolean direct;
  private final boolean sorted;
  private final SelectClause select;

  public InnerSelectValues(SelectClause select) {
    this(false, false, select);
  }

  /**
   * Create.
   * 
   * @param direct
   * @param sorted
   * @param select
   */
  public InnerSelectValues(boolean direct, boolean sorted, SelectClause select) {
    super();
    this.direct = direct;
    this.sorted = sorted;
    this.select = select;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder();
    if (direct) builder.append("DIRECT ");
    if (sorted) builder.append("SORTED ");
    builder.append(select.toPrepared());
    return builder.toString();
  }

  public List<Object> getParams() {
    return select.getParams();
  }

}
