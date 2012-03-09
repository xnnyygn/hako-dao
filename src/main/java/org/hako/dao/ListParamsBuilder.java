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
import java.util.List;

import org.hako.dao.ListParams.SortBy;

/**
 * Builder of {@link ListParams}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ListParamsBuilder {

  private final int offset;
  private final int max;
  private final List<SortBy> sortBys = new ArrayList<SortBy>();

  /**
   * Create with max {@code 10}, offset {0}.
   * 
   * @see #ListParamsBuilder(int, int)
   */
  public ListParamsBuilder() {
    this(10, 0);
  }

  /**
   * Create with offset {@code 0}.
   * 
   * @param max
   */
  public ListParamsBuilder(int max) {
    this(max, 0);
  }

  /**
   * Create.
   * 
   * @param offset
   * @param max
   */
  public ListParamsBuilder(int offset, int max) {
    super();
    this.offset = offset;
    this.max = max;
  }

  /**
   * Add order by with asc {@code true}.
   * 
   * @param name
   * @return this
   * @see #addSortBy(String, boolean)
   */
  public ListParamsBuilder addSortBy(String name) {
    return addSortBy(name, true);
  }

  /**
   * Add orderBy.
   * 
   * @param name
   * @param asc
   * @return this
   */
  public ListParamsBuilder addSortBy(String name, boolean asc) {
    sortBys.add(new SortBy(name, asc));
    return this;
  }

  /**
   * Build.
   * 
   * @return {@link ListParams} instance
   * @see ListParams#ListParams(int, int, List)
   */
  public ListParams toListParams() {
    return new ListParams(max, offset, sortBys);
  }
}
