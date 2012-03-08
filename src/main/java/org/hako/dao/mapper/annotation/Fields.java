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
package org.hako.dao.mapper.annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Fields.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class Fields {

  private final List<FieldMeta> fields;

  /**
   * Create.
   * 
   * @param fields
   */
  public Fields(List<FieldMeta> fields) {
    super();
    this.fields = fields;
  }

  /**
   * Get fields.
   * 
   * @return the fields
   */
  public List<FieldMeta> getAllFields() {
    return fields;
  }

  /**
   * Get primary key fields.
   * 
   * @return primary key fields
   */
  public List<FieldMeta> getPkFields() {
    List<FieldMeta> fields = new ArrayList<FieldMeta>();
    for (FieldMeta f : this.fields) {
      if (f.isPk()) {
        fields.add(f);
      }
    }
    return fields;
  }

  /**
   * Get other fields, aka not primary key fields.
   * 
   * @return other fields
   */
  public List<FieldMeta> getOtherFields() {
    List<FieldMeta> fields = new ArrayList<FieldMeta>();
    for (FieldMeta f : this.fields) {
      if (!f.isPk()) {
        fields.add(f);
      }
    }
    return fields;
  }

}
