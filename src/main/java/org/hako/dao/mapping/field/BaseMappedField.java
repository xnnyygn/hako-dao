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
package org.hako.dao.mapping.field;

import static org.hako.dao.mapping.field.FieldOptions.*;

import java.util.Map;

import org.hako.dao.util.MapUtils;

/**
 * Abstract field.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BaseMappedField<T> implements MappedField<T> {

  private final Map<FieldOptions, Object> options;

  /**
   * Create with options.
   * 
   * @param options
   */
  public BaseMappedField(Map<FieldOptions, Object> options) {
    super();
    this.options = MapUtils.merge(OPTIONS_DEFAULT, options);
  }

  public boolean isPk() {
    return (Boolean) options.get(PK);
  }

  public boolean isFk() {
    return (Boolean) options.get(FK);
  }

}
