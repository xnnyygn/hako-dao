package org.hako.dao.sql.clause.select;

import java.util.List;

import org.hako.dao.sql.Clause;
import org.hako.dao.sql.util.MultipleSqlUtils;

public class SelectClause implements Clause {

  private final SelectClauseBean bean;

  /**
   * Create.
   * 
   * @param bean
   * @throws IllegalArgumentException if selection and table is none in bean
   */
  public SelectClause(SelectClauseBean bean) throws IllegalArgumentException {
    super();
    if (!bean.hasSelection() || !bean.hasTable()) {
      throw new IllegalArgumentException("selection or table cannot be none");
    }
    this.bean = bean;
  }

  public String toPrepared() {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ").append(bean.getSelection().toPrepared());
    builder.append(" FROM ").append(bean.getTable().toPrepared()).toString();
    if (bean.hasWhereCond()) {
      builder.append(" WHERE ").append(bean.getWhereCond().toPrepared());
    }
    if (bean.hasOrderBy()) {
      builder.append(" ORDER BY ").append(bean.getOrderBy().toPrepared());
    }
    if (bean.hasLimit()) {
      builder.append(' ').append(bean.getLimit().toPrepared());
    }
    return builder.toString();
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.getParams(bean.getSelection(), bean.getTable(),
        bean.getWhereCondOpt(), bean.getOrderByOpt(), bean.getLimitOpt());
  }

}
