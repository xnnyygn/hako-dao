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
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.Values;

/**
 * Like restriction.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class LikeRestriction implements Restriction {

  protected final String propertyName;
  protected final String pattern;

  /**
   * Match mode.
   * 
   * @author xnnyygn
   * @version %I%, %G%
   * @since 1.1.0
   */
  public static enum MatchMode {
    /**
     * means use specified
     */
    FULL,

    /**
     * means add prefix % to string
     */
    STARTS_WITH,

    /**
     * means add suffix % to string
     */
    ENDS_WITH,

    /**
     * means add prefix and suffix % to string
     */
    CONTATINS;
  }

  /**
   * Create.
   * 
   * @param propertyName
   * @param pattern
   * @param mode
   */
  public LikeRestriction(String propertyName, String pattern, MatchMode mode) {
    this.propertyName = propertyName;
    this.pattern = createPattern(pattern, mode);
  }

  /**
   * Create real pattern with source and match mode.
   * 
   * @param source
   * @param mode
   * @return pattern
   */
  private static String createPattern(String source, MatchMode mode) {
    switch (mode) {
      case STARTS_WITH:
        return source + '%';
      case ENDS_WITH:
        return '%' + source;
      case CONTATINS:
        return '%' + source + '%';
      default:
        return source;
    }
  }

  public Condition toCondition(EntityMeta entityMeta, boolean withAlias) {
    return Conditions.like(
        entityMeta.createColumnExpression(propertyName, withAlias),
        Values.create(pattern));
  }

}
