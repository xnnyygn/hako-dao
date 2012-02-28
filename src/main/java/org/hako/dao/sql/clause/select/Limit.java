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
package org.hako.dao.sql.clause.select;

import java.sql.PreparedStatement;
import java.util.List;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.sql.util.MultipleSqlUtils;

/**
 * Limit. TODO use {@link PreparedStatement#setMaxRows(int)}, {@link PreparedStatement#setFetchSize(int)}
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Limit implements SelectOnlySql {

  private final int max;
  private final Option<Integer> offsetOpt;

  public Limit(int max) {
    this(max, new None<Integer>());
  }

  public Limit(int max, int offset) {
    this(max, new Some<Integer>(Integer.valueOf(offset)));
  }

  /**
   * Create.
   * 
   * @param max
   * @param offsetOpt
   */
  private Limit(int max, Option<Integer> offsetOpt) {
    super();
    this.max = max;
    this.offsetOpt = offsetOpt;
  }

  public String toPrepared() {
    return "LIMIT ?" + (offsetOpt.hasValue() ? " OFFSET ?" : "");
  }

  public List<Object> getParams() {
    return MultipleSqlUtils.mergeParams(max, offsetOpt);
  }

}
