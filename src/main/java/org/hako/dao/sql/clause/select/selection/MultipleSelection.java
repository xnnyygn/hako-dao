package org.hako.dao.sql.clause.select.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    StringBuilder builder = new StringBuilder();
    for (Selection selection : selections) {
      builder.append(selection.toPrepared()).append(", ");
    }
    builder.setLength(builder.length() - 2);
    return builder.toString();
  }

  public List<Object> getParams() {
    List<Object> all = new ArrayList<Object>();
    for (Selection selection : selections) {
      all.addAll(selection.getParams());
    }
    return all;
  }

}
