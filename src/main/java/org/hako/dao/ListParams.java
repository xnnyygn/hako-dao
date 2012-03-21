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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hako.dao.db.client.DefaultDbClient;
import org.hako.dao.sql.clause.select.orderby.MultipleOrderBy;
import org.hako.dao.sql.clause.select.orderby.MultipleOrderByBuilder;
import org.hako.dao.sql.expression.ColumnName;

/**
 * List parameters. If max is less than or equals zero or offset is negative,
 * max or offset will be ignored in
 * {@link DefaultDbClient#selectMultipleRows(org.hako.dao.sql.clause.select.SelectClause)}
 * .
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ListParams {

  /**
   * Sort by.
   * 
   * @author xnnyygn
   * @version %I%, %G%
   * @since 1.0.0
   */
  public static class SortBy {
    private final String name;
    private final boolean asc;

    /**
     * Create.
     * 
     * @param name column name or alias
     * @param asc
     */
    public SortBy(String name, boolean asc) {
      super();
      this.name = name;
      this.asc = asc;
    }

    /**
     * Get name of order by column.
     * 
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * Get ASC.
     * 
     * @return the ASC
     */
    public boolean isAsc() {
      return asc;
    }

  }

  private final int max;
  private final int offset;
  private final List<SortBy> sortBys;

  /**
   * Default constructor, no row limit and offset.
   */
  public ListParams() {
    this(-1, -1);
  }

  /**
   * Create with zero offset, no order by column.
   * 
   * @param max
   */
  public ListParams(int max) {
    this(0, max);
  }

  /**
   * Create with no order by columns.
   * 
   * @param offset
   * @param max
   */
  public ListParams(int offset, int max) {
    this(offset, max, new ArrayList<SortBy>());
  }

  /**
   * Create.
   * 
   * @param offset
   * @param max
   * @param name column name or alias
   * @param asc
   */
  public ListParams(int offset, int max, String name, boolean asc) {
    this(offset, max, Arrays.asList(new SortBy(name, asc)));
  }

  /**
   * Create.
   * 
   * @param offset
   * @param max
   * @param sortBys
   */
  public ListParams(int offset, int max, List<SortBy> sortBys) {
    super();
    this.max = max;
    this.offset = offset;
    this.sortBys = sortBys;
  }

  /**
   * Get max.
   * 
   * @return the max
   */
  public int getMax() {
    return max;
  }

  /**
   * Get offset.
   * 
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * Get multiple sort by.
   * 
   * @return the sortBys
   */
  public List<SortBy> getSortBys() {
    return sortBys;
  }

  /**
   * Convert to multiple order by.
   * 
   * @return orderBys
   */
  public MultipleOrderBy toMultipleOrderBy() {
    MultipleOrderByBuilder builder = new MultipleOrderByBuilder();
    for (SortBy sb : sortBys) {
      builder.add(new ColumnName(sb.getName()), sb.isAsc());
    }
    return builder.build();
  }

}
