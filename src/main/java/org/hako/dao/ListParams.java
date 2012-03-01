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
package org.hako.dao;

import java.util.Arrays;
import java.util.List;

import org.hako.dao.db.client.DefaultDbClient;

/**
 * List parameters. If max is less than or equals zero or offset is negative,
 * max or offset will be ignored in
 * {@link DefaultDbClient#selectMultipleRows(org.hako.dao.sql.clause.select.SelectClause)}
 * ,
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ListParams {

  /**
   * Order by.
   * 
   * @author xnnyygn
   * @version %I%, %G%
   * @since 1.0.0
   */
  public static class OrderBy {
    private final SimpleField<?> field;
    private final boolean asc;

    /**
     * Create.
     * 
     * @param field
     * @param asc
     */
    public OrderBy(SimpleField<?> field, boolean asc) {
      super();
      this.field = field;
      this.asc = asc;
    }

    /**
     * @return the field
     */
    public SimpleField<?> getField() {
      return field;
    }

    /**
     * Get asc.
     * 
     * @return the asc
     */
    public boolean isAsc() {
      return asc;
    }

  }

  private final int max;
  private final int offset;
  private final List<OrderBy> orderBys;

  /**
   * Create.
   * 
   * @param max
   * @param offset
   * @param field
   * @param asc
   */
  public ListParams(int max, int offset, SimpleField<?> field, boolean asc) {
    this(max, offset, Arrays.asList(new OrderBy(field, asc)));
  }

  /**
   * Create.
   * 
   * @param max
   * @param offset
   * @param orderBys
   */
  public ListParams(int max, int offset, List<OrderBy> orderBys) {
    super();
    this.max = max;
    this.offset = offset;
    this.orderBys = orderBys;
  }

  /**
   * @return the max
   */
  public int getMax() {
    return max;
  }

  /**
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * @return the orderBys
   */
  public List<OrderBy> getOrderBys() {
    return orderBys;
  }

}
