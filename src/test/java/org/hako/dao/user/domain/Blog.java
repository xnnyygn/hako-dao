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

import org.hako.dao.mapping.entity.EntityMetaBuilder;
import org.hako.dao.mapping.field.AutoIncreasedPrimaryKey;
import org.hako.dao.mapping.field.MappedField;
import org.hako.dao.mapping.field.NormalField;

/**
 * Domain Blog.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class Blog {

  public static final MappedField<Long> id =
      new AutoIncreasedPrimaryKey<Long>();
  public static final MappedField<String> title = new NormalField<String>();
  public static final MappedField<String> content = new NormalField<String>();
  public static final MappedField<String> dateCreated =
      new NormalField<String>();
  public static final MappedField<Long> userId = new NormalField<Long>();

  /**
   * Post setup.
   * 
   * @param builder entity meta builder
   */
  public static void postSetup(EntityMetaBuilder builder) {
    builder.updateTableName("BLOG", "b");
  }
}
