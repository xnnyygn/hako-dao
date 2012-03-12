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
package org.hako.dao.sql.clause.insert;

import java.util.List;

import org.hako.dao.sql.builder.ToFormattedBuilder;
import org.hako.dao.sql.builder.ToPreparedBuilder;
import org.hako.dao.sql.clause.AbstractClause;
import org.hako.dao.sql.clause.insert.values.ValueSource;

/**
 * Insert clause.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class InsertClause extends AbstractClause {

  private final String tableName;
  private final List<String> columnNames;
  private final ValueSource values;

  /**
   * Create.
   * 
   * @param tableName
   * @param columnNames
   * @param values
   */
  public InsertClause(String tableName, List<String> columnNames,
      ValueSource values) {
    super();
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.values = values;
  }

  public String toPrepared() {
    return new ToPreparedBuilder("INSERT INTO ").append(tableName).append(" (")
        .append(columnNames).append(") ").append(values).toString();
  }

  @Override
  public String toFormatted(int marginCount) {
    ToFormattedBuilder builder = new ToFormattedBuilder("INSERT INTO ");
    builder.append(tableName).append("(\n");
    builder.append(1, ",\n", columnNames);
    builder.append("\n) VALUES(\n", 1, values);
    builder.append("\n)");
    return builder.toString();
  }

  public List<Object> getParams() {
    return values.getParams();
  }

  public String toString() {
    return "INSERT " + super.toString();
  }

}
