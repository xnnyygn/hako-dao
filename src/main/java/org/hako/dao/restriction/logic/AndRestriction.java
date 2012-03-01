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

import org.hako.dao.mapping.entity.EntityMeta;
import org.hako.dao.restriction.Restriction;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.logic.AndCondition;

/**
 * And restriction.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class AndRestriction extends AbstractBinaryLogicRestriction {

  /**
   * Create.
   * 
   * @param left
   * @param right
   */
  public AndRestriction(Restriction left, Restriction right) {
    super(left, right);
  }

  public Condition toCondition(EntityMeta entity) {
    return new AndCondition(left.toCondition(null), right.toCondition(null));
  }

}
