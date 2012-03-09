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
package org.hako.dao.sql.expression.value;

import java.util.Arrays;
import java.util.List;

import org.hako.dao.sql.AbstractSql;
import org.hako.dao.sql.expression.Expression;

/**
 * Dynamic value.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public abstract class DynamicValue extends AbstractSql implements Expression {

  protected final Object value;

  /**
   * Create.
   * 
   * @param value
   */
  public DynamicValue(Object value) {
    super();
    this.value = value;
  }

  public String toPrepared() {
    return "?";
  }

  /**
   * Generate plain string.
   * 
   * @return string
   */
  public String toPlain() {
    return String.valueOf(value);
  }

  public List<Object> getParams() {
    return Arrays.asList(value);
  }

}
