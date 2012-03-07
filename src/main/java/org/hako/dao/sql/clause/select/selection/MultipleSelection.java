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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Multiple selection.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleSelection implements Selection {

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
   */
  public MultipleSelection(List<Selection> selections) {
    super();
    this.selections = selections;
  }

  public String toPrepared() {
    // TODO refactor me
    StringBuilder builder = new StringBuilder();
    for (Selection selection : selections) {
      builder.append(selection.toPrepared()).append(", ");
    }
    builder.setLength(builder.length() - 2);
    return builder.toString();
  }

  public List<Object> getParams() {
    // TODO refactor me
    List<Object> all = new ArrayList<Object>();
    for (Selection selection : selections) {
      all.addAll(selection.getParams());
    }
    return all;
  }

}
