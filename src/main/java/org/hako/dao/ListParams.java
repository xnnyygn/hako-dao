/**
 * Copyright 2012 XnnYygn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hako.dao;

import java.util.List;

/**
 * List parameters.
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
  public static class OrderBy{
    private final String column;
    private final boolean asc;
    
    /**
     * Create.
     * 
     * @param column
     * @param asc
     */
    public OrderBy(String column, boolean asc) {
      super();
      this.column = column;
      this.asc = asc;
    }

    /**
     * Get column.
     * 
     * @return the column
     */
    public String getColumn() {
      return column;
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
