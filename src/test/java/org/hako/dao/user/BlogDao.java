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
package org.hako.dao.user;

import java.util.Map;

import org.hako.dao.GenericDao;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.user.domain.Blog;

/**
 * DAO of {@link BlogInstance}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BlogDao extends GenericDao<BlogInstance, Long> {

  /**
   * Create.
   * 
   * @param client
   */
  public BlogDao(DbClient client) {
    super(client, Blog.class);
  }

  @Override
  protected BlogInstance convert(Map<String, Object> props) {
    return new BlogInstance(props);
  }
 
}
