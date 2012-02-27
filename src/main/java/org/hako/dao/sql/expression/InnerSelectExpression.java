package org.hako.dao.sql.expression;

import org.hako.dao.sql.InnerSelect;
import org.hako.dao.sql.clause.select.SelectClause;

public class InnerSelectExpression extends InnerSelect implements Expression {

  /**
   * Create.
   * 
   * @param select
   */
  public InnerSelectExpression(SelectClause select) {
    super(select);
  }

}
