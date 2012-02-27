package org.hako.dao.sql.clause.update;

import java.util.List;

import org.hako.dao.sql.Clause;
import org.hako.dao.sql.Sql;
import org.hako.dao.sql.util.MultipleSqlUtils;

public class UpdateClause implements Clause {

  private final UpdateClauseBean bean;

  /**
   * Create.
   * 
   * @param bean
   * @throws IllegalArgumentException if no table name or empty column value
   *         pair in bean
   */
  public UpdateClause(UpdateClauseBean bean) throws IllegalArgumentException {
    super();
    if (!bean.hasTableName() || bean.getPairs().isEmpty()) {
      throw new IllegalArgumentException("table name must be specified and "
          + "column value pair must not be empty");
    }
    this.bean = bean;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder();
    builder.append("UPDATE ").append(bean.getTableName());
    if (bean.hasTableAlias()) {
      builder.append(" AS ").append(bean.getTableAlias());
    }
    builder.append(" SET ").append(
        MultipleSqlUtils.toPrepared(bean.getPairs().toArray(new Sql[0])));
    if (bean.hasWhereCond()) {
      builder.append(" WHERE ").append(bean.getWhereCond().toPrepared());
    }
    return builder.toString();
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.getParams(bean.getPairs(), bean.getWhereCondOpt());
  }

}
