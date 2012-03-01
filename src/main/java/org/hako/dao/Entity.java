/**
 * Copyright 2012 XnnYygn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hako.dao;

import java.util.ArrayList;
import java.util.List;

import org.hako.Tuple2;
import org.hako.TupleUtils;

/**
 * Entity.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Entity {

  private final String tableName;
  private final String tableAlias;
  private final List<SimpleField<?>> pkFields;
  private final List<SimpleField<?>> otherFields;
  private final List<SimpleField<?>> allFields;
  
  /**
   * Create.
   * 
   * @param tableName
   * @param tableAlias
   * @param fields
   * @throws IllegalArgumentException
   */
  public Entity(String tableName, String tableAlias, List<SimpleField<?>> fields)
      throws IllegalArgumentException {
    super();
    if (fields.isEmpty()) {
      throw new IllegalArgumentException("fields cannot be empty");
    }
    this.tableName = tableName;
    this.tableAlias = tableAlias;
    // split fields
    Tuple2<List<SimpleField<?>>, List<SimpleField<?>>> splitedFields =
        splitFieldsByIsPk(fields);
    this.pkFields = splitedFields._1;
    this.otherFields = splitedFields._2;
    this.allFields = fields;
  }

  private Tuple2<List<SimpleField<?>>, List<SimpleField<?>>> splitFieldsByIsPk(
      List<SimpleField<?>> fields) {
    List<SimpleField<?>> pkFields = new ArrayList<SimpleField<?>>();
    List<SimpleField<?>> otherFields = new ArrayList<SimpleField<?>>();
    for (SimpleField<?> f : fields) {
      if (f.isPk()) {
        pkFields.add(f);
      } else {
        otherFields.add(f);
      }
    }
    return TupleUtils.create(pkFields, otherFields);
  }

  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @return the tableAlias
   */
  public String getTableAlias() {
    return tableAlias;
  }

  /**
   * @return the pkFields
   */
  public List<SimpleField<?>> getPkFields() {
    return pkFields;
  }

  /**
   * @return the otherFields
   */
  public List<SimpleField<?>> getOtherFields() {
    return otherFields;
  }

  /**
   * @return the allFields
   */
  public List<SimpleField<?>> getAllFields() {
    return allFields;
  }
  
}
