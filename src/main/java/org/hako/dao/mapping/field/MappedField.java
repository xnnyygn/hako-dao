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

import java.util.HashMap;
import java.util.Map;

import org.hako.util.MapUtils;

import static org.hako.dao.mapping.field.FieldOptions.*;
import static java.lang.Boolean.*;

/**
 * Field.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public interface MappedField<T> {

  public static final Map<FieldOptions, Object> OPTIONS_EMPTY =
      new HashMap<FieldOptions, Object>(0);
  public static final Map<FieldOptions, Object> OPTIONS_DEFAULT = MapUtils
      .apply(FieldOptions.class, PK, FALSE, FK, FALSE, AUTO_INCREMENT, FALSE);

  /**
   * Check if field is primary key.
   * 
   * @return true if is, otherwise false
   * @see FieldOptions#PK
   */
  public boolean isPk();

  /**
   * Check if field is foreign key.
   * 
   * @return true if is, otherwise false
   * @see FieldOptions#FK
   */
  public boolean isFk();

}
