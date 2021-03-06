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
package org.hako.dao.sql.clause.select.selection;

import java.util.Arrays;
import java.util.List;

import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.util.GetParamsUtils;

/**
 * Multiple selection.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleSelection extends AbstractSelection {

  private final List<Selection> selections;

  /**
   * Create.
   * 
   * @param selections
   */
  public MultipleSelection(Selection... selections) {
    this(Arrays.asList(selections));
  }

  /**
   * Create.
   * 
   * @param selections
   * @throws IllegalArgumentException if selections is empty
   */
  public MultipleSelection(List<Selection> selections)
      throws IllegalArgumentException {
    super();
    if (selections.isEmpty()) {
      throw new IllegalArgumentException("selections cannot be empty");
    }
    this.selections = selections;
  }

  public String toPrepared() {
    return new ToPreparedBuilder(selections).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    return new ToFormattedBuilder().append(marginCount, ",\n", selections)
        .toString();
  }

  public List<Object> getParams() {
    return GetParamsUtils.from(selections);
  }

}
