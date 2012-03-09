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
package org.hako.dao.sql.clause.select.table;

import java.util.List;

/**
 * Simple table.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SimpleTable extends AbstractTable {

  protected final String name;

  /**
   * Create.
   * 
   * @param name
   */
  public SimpleTable(String name) {
    super();
    this.name = name;
  }

  public String toPrepared() {
    return name;
  }

  public List<Object> getParams() {
    return NO_PARAM;
  }

}
