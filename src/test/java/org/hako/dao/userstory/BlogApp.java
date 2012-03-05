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
package org.hako.dao.userstory;

import org.junit.Test;

/**
 * Blog application.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BlogApp {

  @Test
  public void testShowBlog() {
    // SELECT b.*, u.* FROM blog as b JOIN user as u ON b.user_id = u.id \
    // WHERE b.id = ?
    // SELECT t.* FROM tag as t WHERE t.id IN ( SELECT bt.tag_id \
    // FROM blog_tags as bt WHERE bt.blog_id = ? )
    // SELECT c.*, u.* FROM comment as c JOIN user as u ON c.user_id = u.id \
    // WHERE c.blog_id = ? ORDER BY c.date_created DESC
  }

  @Test
  public void testListBlog() {
    // SELECT COUNT(b.id) FROM blog as b
    // SELECT b.*, u.*, ( SELECT COUNT(c.id) FROM comment as c \
    // WHERE c.blog_id = b.id ), (SELECT CONCAT_WS(' ', t.name) FROM tag as t
    // WHERE t.id IN (SELECT bt.tag_id FROM blog_tags as bt WHERE bt.blog_id =
    // b.id )) FROM blog as b JOIN user as u ON b.user_id =
    // u.id ORDER BY b.date_created DESC (with limit)
    // right side
    // SELECT COUNT(b.id), DAY(b.date_created) as d FROM blog as b \
    // WHERE b.date_created BETWEEN ? AND ? \
    // GROUP BY d HAVING COUNT(d) > 0
  }

  @Test
  public void testCreatedBlog() {
    // tags
    // SELECT COUNT(t.id) FROM tag as t WHERE t.name = ?
    // INSERT INTO tag(...) VALUES(?...)
    // INSERT INTO blog(...) VALUES(?...)
  }

  @Test
  public void testListByTag() {
    // SELECT COUNT(b.id) FROM blog as b
    // SELECT b.*, u.*, ( SELECT COUNT(c.id) FROM comment as c \
    // WHERE c.blog_id = b.id ) FROM blog as b \
    // JOIN user as u ON b.user_id = u.id \
    // WHERE b.id IN ( SELECT bt.blog_id FROM blog_tags bt WHERE bt.tag_id = (
    // SELECT t.id FROM tag as t WHERE t.name = ? ))
    // ORDER BY b.date_created DESC (with limit)
  }

}
