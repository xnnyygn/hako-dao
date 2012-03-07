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
package org.hako.dao.sql.clause.insert.values;

import java.util.List;

import org.hako.dao.sql.clause.select.SelectClause;

/**
 * Inner select values.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class InnerSelectValues implements ValueSource {

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
