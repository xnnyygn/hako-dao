package org.hako.dao.sql.clause.delete;

import java.util.ArrayList;
import java.util.List;

import org.hako.Option;
import org.hako.dao.sql.Clause;
import org.hako.dao.sql.expression.condition.Condition;

public class DeleteClause implements Clause {

  private final String tableName;
  private final Option<Condition> whereCondOpt;

  /**
   * Create.
   * 
   * @param tableName
   * @param whereCondOpt
   */
  public DeleteClause(String tableName, Option<Condition> whereCondOpt) {
    super();
    this.tableName = tableName;
    this.whereCondOpt = whereCondOpt;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder("DELETE FROM ");
    builder.append(tableName);
    if (whereCondOpt.hasValue()) {
      builder.append(" WHERE ").append(whereCondOpt.get().toPrepared());
    }
    return builder.toString();
  }

  public List<Object> getParams() {
    return whereCondOpt.hasValue() ? whereCondOpt.get().getParams()
        : new ArrayList<Object>(0);
  }

}
