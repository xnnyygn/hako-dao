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

import java.sql.Timestamp;

import org.hako.dao.mapping.entity.TableName;
import org.hako.dao.sql.clause.insert.InsertClauseBuilder;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.update.UpdateClauseBuilder;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.function.Functions;
import org.hako.dao.sql.expression.value.Values;
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
    builder.select(TABLE_BLOG.forAliasAsteriskSel(),
        TABLE_USER.forAliasAsteriskSel());
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
        Values.create(1l)));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(TABLE_TAG.forAliasAsteriskSel());
    builder.from(TABLE_TAG.forAka());
    builder.where(Conditions.in(TABLE_TAG.forAliasColumn("id"),
        subBuilder.toInnerSelectExpr()));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testShowBlogBlogComments() {
    // SELECT c.*, u.* FROM comment as c JOIN user as u ON c.user_id = u.id \
    // WHERE c.blog_id = ? ORDER BY c.date_created DESC
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(TABLE_COMMENT.forAliasAsteriskSel(),
        TABLE_USER.forAliasAsteriskSel());
    builder.fromJoin(
        TABLE_COMMENT.forAka(),
        TABLE_USER.forAka(),
        Conditions.eq(TABLE_COMMENT.forAliasColumn("user_id"),
            TABLE_USER.forAliasColumn("id")));
    builder.where(Conditions.eq(TABLE_COMMENT.forAliasColumn("blog_id"),
        Values.create(1l)));
    builder.addOrderBy(TABLE_COMMENT.forAliasColumn("date_created"), false);
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testListBlogCount() {
    // SELECT COUNT(b.*) FROM blog as b
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(TABLE_BLOG.forAka());
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testListBlogListBlog() {
    // SELECT b.*, u.*, ( SELECT COUNT(c.id) FROM comment as c \
    // WHERE c.blog_id = b.id ), (SELECT CONCAT_WS(' ', t.name) FROM tag as t
    // WHERE t.id IN (SELECT bt.tag_id FROM blog_tags as bt WHERE bt.blog_id =
    // b.id )) FROM blog as b JOIN user as u ON b.user_id =
    // u.id ORDER BY b.date_created DESC (with limit)
    SelectClauseBuilder commentCountBuilder = new SelectClauseBuilder();
    commentCountBuilder.select(Functions.countRow());
    commentCountBuilder.from(TABLE_COMMENT.forAka());
    commentCountBuilder.where(Conditions.eq(
        TABLE_COMMENT.forAliasColumn("blog_id"),
        TABLE_BLOG.forAliasColumn("id")));

    SelectClauseBuilder blogTagsBuilder = new SelectClauseBuilder();
    blogTagsBuilder.select(TABLE_BLOG_TAGS.forAliasColumn("tag_id"));
    blogTagsBuilder.from(TABLE_BLOG_TAGS.forAka());
    blogTagsBuilder.where(Conditions.eq(
        TABLE_BLOG_TAGS.forAliasColumn("blog_id"),
        TABLE_BLOG.forAliasColumn("id")));

    SelectClauseBuilder tagsBuilder = new SelectClauseBuilder();
    tagsBuilder.select(Functions.binaryFun("CONCAT_WS",
        Values.createStatic(" "), TABLE_TAG.forAliasColumn("text")));
    tagsBuilder.from(TABLE_TAG.forAka());
    tagsBuilder.where(Conditions.in(TABLE_TAG.forAliasColumn("id"),
        blogTagsBuilder.toInnerSelectExpr()));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(TABLE_BLOG.forAliasAsteriskSel(),
        TABLE_USER.forAliasAsteriskSel(),
        commentCountBuilder.toInnerSelectSelection(),
        tagsBuilder.toInnerSelectSelection());
    builder.fromJoin(
        TABLE_BLOG.forAka(),
        TABLE_USER.forAka(),
        Conditions.eq(TABLE_BLOG.forAliasColumn("user_id"),
            TABLE_USER.forAliasColumn("id")));
    builder.addOrderBy(TABLE_BLOG.forAliasColumn("date_created"), false);
    builder.limit(10, 0);
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testListBlogRightSide() {
    // SELECT DAY(b.date_created) as d FROM blog as b \
    // WHERE b.date_created BETWEEN ? AND ? \
    // GROUP BY d HAVING COUNT(d) > 0
    SelectClauseBuilder builder = new SelectClauseBuilder();
    String dayAlias = "dom";
    ColumnName dayAliasColumn = new ColumnName(dayAlias);
    builder.selectAs(
        Functions.unaryFun("DAY", TABLE_BLOG.forAliasColumn("date_created")),
        dayAlias);
    builder.groupBy(dayAliasColumn);
    builder.from(TABLE_BLOG.forAka());
    builder.having(Conditions.gt(dayAliasColumn, Values.createStatic(0)));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testCreatedBlogCountTagByName() {
    // SELECT COUNT(t.id) FROM tag as t WHERE t.name = ?
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(TABLE_TAG.forAka());
    builder.where(Conditions.eq(TABLE_TAG.forAliasColumn("text"),
        Values.create("foo")));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testCreateBlogInsertTag() {
    // INSERT INTO tag(...) VALUES(?...)
    InsertClauseBuilder builder = new InsertClauseBuilder(TABLE_TAG.getName());
    builder.add("text", "bar");
    System.out.println(builder.toInsertClause());
  }

  @Test
  public void testCreateBlogInsertBlog() {
    // INSERT INTO blog(...) VALUES(?...)
    InsertClauseBuilder builder = new InsertClauseBuilder(TABLE_BLOG.getName());
    builder.add("title", "foo");
    builder.add("content", "bar");
    builder.add("date_created", new Timestamp(System.currentTimeMillis()));
    builder.add("user_id", 1l);
    System.out.println(builder.toInsertClause());
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
    SelectClauseBuilder commentCountBuilder = new SelectClauseBuilder();
    commentCountBuilder.select(Functions.countRow());
    commentCountBuilder.from(TABLE_COMMENT.forAka());
    commentCountBuilder.where(Conditions.eq(
        TABLE_COMMENT.forAliasColumn("blog_id"),
        TABLE_BLOG.forAliasColumn("id")));

    SelectClauseBuilder tagBuilder = new SelectClauseBuilder();
    tagBuilder.select(TABLE_TAG.forAliasColumn("id"));
    tagBuilder.from(TABLE_TAG.forAka());
    tagBuilder.where(Conditions.eq(TABLE_TAG.forAliasColumn("text"),
        Values.create("foo")));

    SelectClauseBuilder blogsWithTagBuilder = new SelectClauseBuilder();
    blogsWithTagBuilder.select(TABLE_BLOG_TAGS.forAliasColumn("blog_id"));
    blogsWithTagBuilder.from(TABLE_BLOG_TAGS.forAka());
    blogsWithTagBuilder.where(Conditions.eq(
        TABLE_BLOG_TAGS.forAliasColumn("tag_id"),
        tagBuilder.toInnerSelectExpr()));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(TABLE_BLOG.forAliasAsteriskSel(),
        TABLE_USER.forAliasAsteriskSel(),
        commentCountBuilder.toInnerSelectSelection());
    builder.fromJoin(
        TABLE_BLOG.forAka(),
        TABLE_USER.forAka(),
        Conditions.eq(TABLE_BLOG.forAliasColumn("user_id"),
            TABLE_USER.forAliasColumn("id")));
    builder.where(Conditions.in(TABLE_BLOG.forAliasColumn("id"),
        blogsWithTagBuilder.toInnerSelectExpr()));
    builder.addOrderBy(TABLE_BLOG.forAliasColumn("date_created"), false);
    builder.limit(10, 0);
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testUpdateBlog() {
    // UPDATE blog b set title = ?, ...
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(TABLE_BLOG.getName(), TABLE_BLOG.getAlias());
    builder.set("title", "foo");
    builder.set("content", "bar");
    builder.where(Conditions.eq(TABLE_BLOG.forAliasColumn("id"),
        Values.create(1l)));
    System.out.println(builder.toUpdateClause());
  }

}
