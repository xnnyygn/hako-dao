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
package org.hako.dao.sql.clause.select.orderby;

import java.util.List;

import org.hako.dao.sql.Sql;
import org.hako.dao.sql.util.SqlUtils;

/**
 * Multiple order by.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleOrderBy extends AbstractOrderBy {

  private final List<OrderBy> orderBys;

  /**
   * Create.
   * 
   * @param orderBys
   * @throws IllegalArgumentException if orderBys is empty
   */
  public MultipleOrderBy(List<OrderBy> orderBys)
      throws IllegalArgumentException {
    super();
    if (orderBys.isEmpty()) {
      throw new IllegalArgumentException("orderBys cannot be empty");
    }
    this.orderBys = orderBys;
  }

  public String toPrepared() {
    return SqlUtils.toPrepared(orderBys.toArray(new Sql[0]));
  }

  public List<Object> getParams() {
    return SqlUtils.getParams(orderBys);
  }

}
