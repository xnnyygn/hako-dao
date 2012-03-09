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
package org.hako.dao.sql.expression.function;

import java.util.List;

import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.util.SqlUtils;

/**
 * Binary function.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BinaryFunction extends AbstractFunction {

  protected final Expression first;
  protected final Expression second;

  /**
   * Create.
   * 
   * @param name
   * @param first
   * @param second
   */
  public BinaryFunction(String name, Expression first, Expression second) {
    super(name);
    this.first = first;
    this.second = second;
  }

  public String toPrepared() {
    return new StringBuilder(name).append('(').append(first.toPrepared())
        .append(", ").append(second.toPrepared()).append(')').toString();
  }

  public List<Object> getParams() {
    return SqlUtils.getParams(first, second);
  }

}
