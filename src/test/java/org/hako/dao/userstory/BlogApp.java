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

import org.hako.dao.mapping.entity.TableName;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.select.table.JoinWithConditionTable;
import org.hako.dao.sql.expression.InnerSelectExpression;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.ValueFactory;
import org.junit.Test;

/**
 * Blog application.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BlogApp {

  private static TableName TABLE_BLOG = new TableName("blog", "b");
  private static TableName TABLE_USER = new TableName("user", "u");
  private static TableName TABLE_TAG = new TableName("tag", "t");
  private static TableName TABLE_BLOG_TAGS = new TableName("blog_tags", "bt");
  private static TableName TABLE_COMMENT = new TableName("comment", "c");

  @Test
  public void testShowBlogBlogAndUser() {
    // SELECT b.*, u.* FROM blog as b JOIN user as u ON b.user_id = u.id \
    // WHERE b.id = ?
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder
        .select(TABLE_BLOG.forAliasAsterisk(), TABLE_USER.forAliasAsterisk());
    builder.fromJoin(
        TABLE_BLOG.forAka(),
        TABLE_USER.forAka(),
        Conditions.eq(TABLE_BLOG.forAliasColumn("user_id"),
            TABLE_USER.forAliasColumn("id")));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testShowBlogBlogTags() {
    // SELECT t.* FROM tag as t WHERE t.id IN ( SELECT bt.tag_id \
    // FROM blog_tags as bt WHERE bt.blog_id = ? )
    SelectClauseBuilder subBuilder = new SelectClauseBuilder();
    subBuilder.select(TABLE_BLOG_TAGS.forAliasColumn("tag_id"));
    subBuilder.from(TABLE_BLOG_TAGS.forAka());
    subBuilder.where(Conditions.eq(TABLE_BLOG_TAGS.forAliasColumn("blog_id"),
        ValueFactory.create(1l)));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(TABLE_TAG.forAliasAsterisk());
    builder.from(TABLE_TAG.forAka());
    builder.where(Conditions.inSelect(TABLE_TAG.forAliasColumn("id"),
        subBuilder.toSelectClause()));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testShowBlogBlogComments() {
    // SELECT c.*, u.* FROM comment as c JOIN user as u ON c.user_id = u.id \
    // WHERE c.blog_id = ? ORDER BY c.date_created DESC
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(TABLE_COMMENT.forAliasAsterisk(),
        TABLE_USER.forAliasAsterisk());
    builder.fromJoin(
        TABLE_COMMENT.forAka(),
        TABLE_USER.forAka(),
        Conditions.eq(TABLE_COMMENT.forAliasColumn("user_id"),
            TABLE_USER.forAliasColumn("id")));
    builder.where(Conditions.eq(TABLE_COMMENT.forAliasColumn("blog_id"),
        ValueFactory.create(1l)));
    builder.addOrderBy(TABLE_COMMENT.forAliasColumn("date_created"), false);
    System.out.println(builder.toSelectClause());
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
