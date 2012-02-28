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
package org.hako.dao.restriction.logic;

import java.util.ArrayList;
import java.util.List;

import org.hako.dao.restriction.Restriction;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.logic.MultipleAndCondition;

/**
 * Multiple and restriction.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleAndRestriction implements Restriction {

  private final List<Restriction> restrictions;

  /**
   * Create.
   * 
   * @param restrictions
   */
  public MultipleAndRestriction(List<Restriction> restrictions) {
    super();
    this.restrictions = restrictions;
  }

  public Condition toCondition() {
    List<Condition> conditions = new ArrayList<Condition>();
    for (Restriction r : restrictions) {
      conditions.add(r.toCondition());
    }
    return new MultipleAndCondition(conditions);
  }

}
