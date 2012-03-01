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

import org.hako.dao.ListParams.OrderBy;
import org.hako.dao.mapping.field.SimpleField;

/**
 * Builder of {@link ListParams}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ListParamsBuilder {

  private final int max;
  private final int offset;
  private final List<OrderBy> orderBys = new ArrayList<OrderBy>();

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
   * @param max
   * @param offset
   */
  public ListParamsBuilder(int max, int offset) {
    super();
    this.max = max;
    this.offset = offset;
  }

  /**
   * Add order by with asc {@code true}.
   * 
   * @param columnName column name
   * @return this
   * @see #addOrderBy(String, boolean)
   */
  public ListParamsBuilder addOrderBy(SimpleField<?> field) {
    return addOrderBy(field, true);
  }

  /**
   * Add order by.
   * 
   * @param field
   * @param asc
   * @return this
   */
  public ListParamsBuilder addOrderBy(SimpleField<?> field, boolean asc) {
    orderBys.add(new OrderBy(field, asc));
    return this;
  }

  /**
   * Build.
   * 
   * @return list params instance
   * @see ListParams
   */
  public ListParams toListParams() {
    return new ListParams(max, offset, orderBys);
  }
}
