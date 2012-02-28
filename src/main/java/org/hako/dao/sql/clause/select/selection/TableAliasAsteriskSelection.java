package org.hako.dao.sql.clause.select.selection;

import org.hako.dao.sql.TableAliasAsterisk;

/**
 * Table alias asterisk.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 * 
 */
public class TableAliasAsteriskSelection extends TableAliasAsterisk implements
    Selection {

  /**
   * Create.
   * 
   * @param tableAlias
   */
  public TableAliasAsteriskSelection(String tableAlias) {
    super(tableAlias);
  }

}
