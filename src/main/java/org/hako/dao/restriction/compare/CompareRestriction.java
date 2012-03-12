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
package org.hako.dao.restriction.compare;

import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.restriction.Restriction;
import org.hako.dao.sql.expression.condition.Condition;
import org.hako.dao.sql.expression.condition.compare.CompareCondition;
import org.hako.dao.sql.expression.condition.compare.CompareSymbol;
import org.hako.dao.sql.expression.value.Values;

/**
 * Abstract compare restriction.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class CompareRestriction implements Restriction {

  protected final String propertyName;
  protected final CompareSymbol symbol;
  protected final Object value;

  /**
   * Create.
   * 
   * @param propertyName
   * @param symbol
   * @param value
   */
  public CompareRestriction(String propertyName, CompareSymbol symbol,
      Object value) {
    super();
    this.propertyName = propertyName;
    this.symbol = symbol;
    this.value = value;
  }

  public Condition toCondition(EntityMeta entityMeta, boolean withAlias) {
    return new CompareCondition(entityMeta.createColumnExpression(propertyName,
        withAlias), symbol, Values.create(value));
  }

}
