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

/**
 * Int single order by.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class IndexSingleOrderBy extends AbstractSingleOrderBy {

  private final int index;

  /**
   * Create.
   * 
   * @param index
   * @param asc
   * @param nullsFirst
   */
  public IndexSingleOrderBy(int index, boolean asc, boolean nullsFirst) {
    super(asc, nullsFirst);
    this.index = index;
  }

  @Override
  public String toPrepared() {
    return index + " " + super.toPrepared();
  }

  public List<Object> getParams() {
    return NO_PARAM;
  }
  
}
