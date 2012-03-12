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
package org.hako.dao.sql.clause.select.table;

/**
 * Table factory.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Tables {

  public static SimpleTable createSimple(String tableName) {
    return new SimpleTable(tableName);
  }

  public static SchemaTable createSchemaTable(String schema, String tableName) {
    return new SchemaTable(schema, tableName);
  }

  public static AkaTable createAkaTable(Table table, String alias) {
    return new AkaTable(table, alias);
  }

  public static AkaTable createSimpleAkaTable(String tableName, String alias) {
    return new AkaTable(new SimpleTable(tableName), alias);
  }

}
