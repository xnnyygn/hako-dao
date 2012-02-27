package org.hako.dao.sql.clause.update;

import java.util.ArrayList;
import java.util.List;

import org.hako.None;
import org.hako.Option;
import org.hako.dao.sql.expression.condition.Condition;

public class UpdateClauseBean {

  private Option<String> tableNameOpt = new None<String>();
  private Option<String> tableAliasOpt = new None<String>();
  private List<ColumnValuePair> pairs = new ArrayList<ColumnValuePair>();
  private Option<Condition> whereCondOpt = new None<Condition>();

  public Option<String> getTableNameOpt() {
    return tableNameOpt;
  }

  public void setTableNameOpt(Option<String> tableNameOpt) {
    this.tableNameOpt = tableNameOpt;
  }

  public boolean hasTableName() {
    return tableNameOpt.hasValue();
  }

  public String getTableName() {
    return tableNameOpt.get();
  }

  public Option<String> getTableAliasOpt() {
    return tableAliasOpt;
  }

  public void setTableAliasOpt(Option<String> tableAliasOpt) {
    this.tableAliasOpt = tableAliasOpt;
  }

  public boolean hasTableAlias() {
    return tableAliasOpt.hasValue();
  }

  public String getTableAlias() {
    return tableAliasOpt.get();
  }

  public List<ColumnValuePair> getPairs() {
    return pairs;
  }

  public void setPairs(List<ColumnValuePair> pairs) {
    this.pairs = pairs;
  }

  public Option<Condition> getWhereCondOpt() {
    return whereCondOpt;
  }

  public void setWhereCondOpt(Option<Condition> whereCondOpt) {
    this.whereCondOpt = whereCondOpt;
  }

  public boolean hasWhereCond() {
    return whereCondOpt.hasValue();
  }

  public Condition getWhereCond() {
    return whereCondOpt.get();
  }
  
}
