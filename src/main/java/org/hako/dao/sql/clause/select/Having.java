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

import java.util.List;

import org.hako.dao.sql.AbstractSql;
import org.hako.dao.sql.expression.condition.Condition;

/**
 * Having clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class Having extends AbstractSql implements SelectOnlySql {

  private final Condition condition;

  /**
   * Create.
   * 
   * @param condition
   */
  public Having(Condition condition) {
    super();
    this.condition = condition;
  }

  public String toPrepared() {
    return condition.toPrepared();
  }

  public List<Object> getParams() {
    return condition.getParams();
  }

}
