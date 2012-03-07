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
package org.hako.dao.db.client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hako.dao.db.vendor.DbcpVendor;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.select.selection.MultipleSelectionBuilder;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.Values;
import org.junit.Test;

/**
 * Test of {@link DefaultDbClient}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class DefaultDbClientTest {

  private static DefaultDbClient client = createDefaultDbClient();

  private static DefaultDbClient createDefaultDbClient() {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("driverClassName", "org.h2.Driver");
    props.put("url", "jdbc:h2:mem");
    props.put("username", "sa");
    props.put("password", "");
    props.put("connectionInitSqls", loadInitSqls());
    return new DefaultDbClient(new DbcpVendor(props));
  }

  private static List<String> loadInitSqls() {
    try {
      return Arrays.asList(IOUtils.toString(new FileReader(
          "src/test/resources/blog-init.sql")));
    } catch (IOException e) {
      // convert to runtime exception
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSelectMultipleRows() {
    MultipleSelectionBuilder selectionBuilder = new MultipleSelectionBuilder();
    selectionBuilder.addExpression(new ColumnName("ID"));
    selectionBuilder.addExpression(new ColumnName("TITLE"));
    selectionBuilder.addExpression(new ColumnName("CONTENT"));
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(selectionBuilder.toMultipleSelection());
    builder.from("BLOG");
    System.out.println(client.selectMultipleRows(builder.toSelectClause()));
  }

  @Test
  public void testSelectSingleRowExist() {
    MultipleSelectionBuilder selectionBuilder = new MultipleSelectionBuilder();
    ColumnName columnId = new ColumnName("ID");
    selectionBuilder.addExpression(columnId);
    selectionBuilder.addExpression(new ColumnName("TITLE"));
    selectionBuilder.addExpression(new ColumnName("CONTENT"));
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(selectionBuilder.toMultipleSelection());
    builder.from("BLOG");
    builder.where(Conditions.eq(columnId, Values.create(1l)));
    System.out.println(client.selectSingleRow(builder.toSelectClause()));
  }

  @Test
  public void testSelectSingleRowNotExist() {
    MultipleSelectionBuilder selectionBuilder = new MultipleSelectionBuilder();
    ColumnName columnId = new ColumnName("ID");
    selectionBuilder.addExpression(columnId);
    selectionBuilder.addExpression(new ColumnName("TITLE"));
    selectionBuilder.addExpression(new ColumnName("CONTENT"));
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(selectionBuilder.toMultipleSelection());
    builder.from("BLOG");
    builder.where(Conditions.eq(columnId, Values.create(100l)));
    System.out.println(client.selectSingleRow(builder.toSelectClause()));

  }

  @Test
  public void testSelectObject() {
//    System.out.println(client.selectObject(new SelectClause(
//        "SELECT COUNT(*) FROM BLOG")));
  }

}
