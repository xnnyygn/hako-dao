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
package org.hako.dao.sql.clause.update;

import java.util.List;

import org.hako.dao.sql.AbstractSql;
import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Column expression pairs.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ColumnExpressionPairs extends AbstractSql {

  private List<ColumnExpressionPair> pairs;

  /**
   * Create.
   * 
   * @param pairs
   */
  public ColumnExpressionPairs(List<ColumnExpressionPair> pairs) {
    super();
    this.pairs = pairs;
  }

  public String toPrepared() {
    return new ToPreparedBuilder().append(pairs).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    logMarginCount(marginCount);
    return new ToFormattedBuilder().append(marginCount, ",\n", pairs)
        .toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(pairs);
  }

}
