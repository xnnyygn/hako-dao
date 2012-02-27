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
package org.hako.dao;

import java.util.Arrays;
import java.util.Map;

import org.hako.dao.db.client.DbClient;

/**
 * DAO of {@link Blog}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BlogDao extends Entity<Blog, Long> {

  public final static Field<Long> FIELD_ID = new Field<Long>("id", true);
  public final static Field<String> FIELD_TITLE = new Field<String>("title",
      false);
  public final static Field<String> FIELD_CONTENT = new Field<String>(
      "content", false);
  public final static Field<Long> FIELD_USER_ID = new Field<Long>("user_id",
      "userId", false);

  /**
   * Create.
   * 
   * @param client
   */
  @SuppressWarnings("unchecked")
  public BlogDao(DbClient client) {
    super(client, "BLOG", Arrays.asList((Field<?>) FIELD_ID, FIELD_TITLE,
        FIELD_CONTENT, FIELD_USER_ID));
  }

  @Override
  protected Blog convert(Map<String, Object> props) {
    return new Blog(props);
  }

}
