/**
 * Copyright 2012 XnnYygn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.hako.dao.sql;

import org.hako.dao.sql.clause.select.selection.TableAliasAsteriskSelection;

/**
 * Table alias asterisk.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class TableAliasAsterisk extends Asterisk {

  protected final String tableAlias;

  /**
   * Create.
   * 
   * @param tableAlias
   */
  public TableAliasAsterisk(String tableAlias) {
    super();
    this.tableAlias = tableAlias;
  }

  @Override
  public String toPrepared() {
    return tableAlias + "." + super.toPrepared();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result =
        prime * result + ((tableAlias == null) ? 0 : tableAlias.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof TableAliasAsteriskSelection)) return false;
    TableAliasAsteriskSelection other = (TableAliasAsteriskSelection) obj;
    if (tableAlias == null) {
      if (other.tableAlias != null) return false;
    } else if (!tableAlias.equals(other.tableAlias)) return false;
    return true;
  }

}
