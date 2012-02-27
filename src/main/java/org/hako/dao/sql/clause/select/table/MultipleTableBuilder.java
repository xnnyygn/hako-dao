package org.hako.dao.sql.clause.select.table;

import java.util.ArrayList;
import java.util.List;

public class MultipleTableBuilder {

  private final List<Table> tables = new ArrayList<Table>();

  public MultipleTableBuilder add(Table table) {
    tables.add(table);
    return this;
  }
  
  public MultipleTable toMultipleTable(){
    return new MultipleTable(tables);
  }
  
}
