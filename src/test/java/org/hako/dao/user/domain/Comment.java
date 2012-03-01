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
package org.hako.dao.user.domain;

import java.sql.Timestamp;

import org.hako.dao.mapping.entity.EntityMetaBuilder;
import org.hako.dao.mapping.field.AutoIncreasedPrimaryKey;
import org.hako.dao.mapping.field.MappedField;
import org.hako.dao.mapping.field.NormalField;

/**
 * Domain comment.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Comment {

  public static final MappedField<Long> id =
      new AutoIncreasedPrimaryKey<Long>();
  public static final MappedField<String> content = new NormalField<String>();
  public static final MappedField<Long> userId = new NormalField<Long>();
  public static final MappedField<Timestamp> dateCreated =
      new NormalField<Timestamp>();
  
  public static void postSetup(EntityMetaBuilder builder) {
    builder.updateTableName("COMMENT", "c");
  }
  
}
