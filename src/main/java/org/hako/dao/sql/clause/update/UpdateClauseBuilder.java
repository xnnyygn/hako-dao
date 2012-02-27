package org.hako.dao.sql.clause.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Condition;

public class UpdateClauseBuilder {

  private final UpdateClauseBean bean = new UpdateClauseBean();

  public UpdateClauseBuilder update(String tableName) {
    return update(tableName, new None<String>());
  }

  public UpdateClauseBuilder update(String tableName, String alias) {
    return update(tableName, new Some<String>(alias));
  }

  private UpdateClauseBuilder update(String tableName, Option<String> aliasOpt) {
    bean.setTableNameOpt(new Some<String>(tableName));
    bean.setTableAliasOpt(aliasOpt);
    return this;
  }

  public UpdateClauseBuilder set(String columnName, Expression expression) {
    return set(Arrays.asList(new ColumnValuePair(columnName, expression)));
  }

  public UpdateClauseBuilder set(String[] columnNames, Expression[] expressions) {
    int count = columnNames.length;
    if (count != expressions.length) {
      throw new IllegalArgumentException("column must has its expression, "
          + "please check expressions count");
    }
    List<ColumnValuePair> pairs = new ArrayList<ColumnValuePair>();
    for (int i = 0; i < count; i++) {
      pairs.add(new ColumnValuePair(columnNames[i], expressions[i]));
    }
    return set(pairs);
  }

  public UpdateClauseBuilder set(Map<String, Expression> columnValueMap) {
    List<ColumnValuePair> pairs = new ArrayList<ColumnValuePair>();
    for (Map.Entry<String, Expression> entry : columnValueMap.entrySet()) {
      pairs.add(new ColumnValuePair(entry.getKey(), entry.getValue()));
    }
    return set(pairs);
  }

  public UpdateClauseBuilder set(List<ColumnValuePair> pairs) {
    bean.setPairs(pairs);
    return this;
  }

  public UpdateClauseBuilder where(Condition whereCond) {
    bean.setWhereCondOpt(new Some<Condition>(whereCond));
    return this;
  }

  public UpdateClause toUpdateClause() {
    return new UpdateClause(bean);
  }
}
