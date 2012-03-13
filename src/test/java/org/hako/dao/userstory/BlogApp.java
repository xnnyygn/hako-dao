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

import org.hako.dao.mapper.AnnotationMapper;
import org.hako.dao.mapper.Blog;
import org.hako.dao.mapper.BlogTags;
import org.hako.dao.mapper.Comment;
import org.hako.dao.mapper.Tag;
import org.hako.dao.mapper.User;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.sql.clause.delete.DeleteClauseBuilder;
import org.hako.dao.sql.clause.insert.InsertClauseBuilder;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.update.UpdateClauseBuilder;
import org.hako.dao.sql.expression.ColumnName;
import org.hako.dao.sql.expression.Expression;
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

  private static AnnotationMapper mapper = new AnnotationMapper();
  private static EntityMeta entityBlog = mapper.setUp(Blog.class);
  private static EntityMeta entityUser = mapper.setUp(User.class);
  private static EntityMeta entityBlogTags = mapper.setUp(BlogTags.class);
  private static EntityMeta entityTag = mapper.setUp(Tag.class);
  private static EntityMeta entityComment = mapper.setUp(Comment.class);

  @Test
  public void testShowBlogBlogAndUser() {
    // SELECT b.*, u.* FROM blog as b JOIN user as u ON b.user_id = u.id \
    // WHERE b.id = ?
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityBlog.createAsteriskSelection(),
        entityUser.createAsteriskSelection());
    builder.fromJoin(
        entityBlog.createTable(),
        entityUser.createTable(),
        Conditions.eq(entityBlog.createColumnExpression("userId"),
            entityUser.createColumnExpression("id")));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testShowBlogBlogTags() {
    // SELECT t.* FROM tag as t WHERE t.id IN ( SELECT bt.tag_id \
    // FROM blog_tags as bt WHERE bt.blog_id = ? )
    SelectClauseBuilder subBuilder = new SelectClauseBuilder();
    subBuilder.select(entityBlogTags.createColumnExpression("tagId"));
    subBuilder.from(entityBlogTags.createTable());
    subBuilder.where(Conditions.eq(
        entityBlogTags.createColumnExpression("blogId"), Values.create(1l)));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityTag.createAsteriskSelection());
    builder.from(entityTag.createTable());
    builder.where(Conditions.in(entityTag.createColumnExpression("id"),
        subBuilder.toInnerSelectExpr()));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testShowBlogBlogComments() {
    // SELECT c.*, u.* FROM comment as c JOIN user as u ON c.user_id = u.id \
    // WHERE c.blog_id = ? ORDER BY c.date_created DESC
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityComment.createAsteriskSelection(),
        entityUser.createAsteriskSelection());
    builder.fromJoin(entityComment.createTable(), entityUser.createTable(),
        Conditions.eq(entityComment.createColumnExpression("userId"),
            entityUser.createColumnExpression("id")));
    builder.where(Conditions.eq(entityComment.createColumnExpression("blogId"),
        Values.create(1l)));
    builder.addOrderBy(entityComment.createColumnExpression("dateCreated"),
        false);
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testListBlogCount() {
    // SELECT COUNT(b.*) FROM blog as b
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(entityBlog.createTable(false));
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
    commentCountBuilder.from(entityComment.createTable());
    commentCountBuilder.where(Conditions.eq(
        entityComment.createColumnExpression("blogId"),
        entityBlog.createColumnExpression("id")));

    SelectClauseBuilder blogTagsBuilder = new SelectClauseBuilder();
    blogTagsBuilder.select(entityBlogTags.createColumnExpression("tagId"));
    blogTagsBuilder.from(entityBlogTags.createTable());
    blogTagsBuilder.where(Conditions.eq(
        entityBlogTags.createColumnExpression("blogId"),
        entityBlog.createColumnExpression("id")));

    SelectClauseBuilder tagsBuilder = new SelectClauseBuilder();
    tagsBuilder.select(Functions.binaryFun("CONCAT_WS",
        Values.createStatic(" "), entityTag.createColumnExpression("text")));
    tagsBuilder.from(entityTag.createTable());
    tagsBuilder.where(Conditions.in(entityTag.createColumnExpression("id"),
        blogTagsBuilder.toInnerSelectExpr()));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityBlog.createAsteriskSelection(),
        entityUser.createAsteriskSelection(),
        commentCountBuilder.toInnerSelectSelection(),
        tagsBuilder.toInnerSelectSelection());
    builder.fromJoin(
        entityBlog.createTable(),
        entityUser.createTable(),
        Conditions.eq(entityBlog.createColumnExpression("userId"),
            entityUser.createColumnExpression("id")));
    builder.addOrderBy(entityBlog.createColumnExpression("dateCreated"), false);
    builder.limit(10, 0);
    System.out.println(builder.toSelectClause().toFormatted());
  }

  @Test
  public void testListBlogRightSide() {
    // SELECT DAY(b.date_created) as d FROM blog as b \
    // WHERE b.date_created BETWEEN ? AND ? \
    // GROUP BY d HAVING COUNT(d) > 0
    SelectClauseBuilder builder = new SelectClauseBuilder();
    String dayAlias = "dom";
    ColumnName dayAliasColumn = new ColumnName(dayAlias);
    Expression fieldDateCreated =
        entityBlog.createColumnExpression("dateCreated");
    builder.selectAs(Functions.unaryFun("DAY", fieldDateCreated), dayAlias);
    Timestamp minTimestamp = new Timestamp(System.currentTimeMillis());
    Timestamp maxTimestamp = new Timestamp(System.currentTimeMillis());
    builder.where(Conditions.between(fieldDateCreated,
        Values.create(minTimestamp), Values.create(maxTimestamp)));
    builder.groupBy(dayAliasColumn);
    builder.from(entityBlog.createTable());
    builder.having(Conditions.gt(Functions.count(dayAliasColumn),
        Values.createStatic(0)));
    System.out.println(builder.toSelectClause().toFormatted());
  }

  @Test
  public void testCreatedBlogCountTagByName() {
    // SELECT COUNT(t.id) FROM tag as t WHERE t.name = ?
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(Functions.countRow());
    builder.from(entityTag.createTable());
    builder.where(Conditions.eq(entityTag.createColumnExpression("text"),
        Values.create("foo")));
    System.out.println(builder.toSelectClause());
  }

  @Test
  public void testCreateBlogInsertTag() {
    // INSERT INTO tag(...) VALUES(?...)
    InsertClauseBuilder builder = new InsertClauseBuilder();
    builder.insertInto(entityTag.createTable(false));
    builder.add("text", "bar");
    System.out.println(builder.toInsertClause());
  }

  @Test
  public void testCreateBlogInsertBlog() {
    // INSERT INTO blog(...) VALUES(?...)
    InsertClauseBuilder builder = new InsertClauseBuilder();
    builder.insertInto(entityBlog.createTable(false));
    builder.add("title", "foo");
    builder.add("content", "bar");
    builder.add("date_created", new Timestamp(System.currentTimeMillis()));
    builder.add("user_id", 1l);
    System.out.println(builder.toInsertClause().toFormatted());
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
    commentCountBuilder.from(entityComment.createTable());
    commentCountBuilder.where(Conditions.eq(
        entityComment.createColumnExpression("blogId"),
        entityBlog.createColumnExpression("id")));

    SelectClauseBuilder tagBuilder = new SelectClauseBuilder();
    tagBuilder.select(entityTag.createColumnExpression("id"));
    tagBuilder.from(entityTag.createTable());
    tagBuilder.where(Conditions.eq(entityTag.createColumnExpression("text"),
        Values.create("foo")));

    SelectClauseBuilder blogsWithTagBuilder = new SelectClauseBuilder();
    blogsWithTagBuilder.select(entityBlogTags.createColumnExpression("blogId"));
    blogsWithTagBuilder.from(entityBlogTags.createTable());
    blogsWithTagBuilder.where(Conditions.eq(
        entityBlogTags.createColumnExpression("tagId"),
        tagBuilder.toInnerSelectExpr()));

    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(entityBlog.createAsteriskSelection(),
        entityUser.createAsteriskSelection(),
        commentCountBuilder.toInnerSelectSelection());
    builder.fromJoin(
        entityBlog.createTable(),
        entityUser.createTable(),
        Conditions.eq(entityBlog.createColumnExpression("userId"),
            entityUser.createColumnExpression("id")));
    builder.where(Conditions.in(entityBlog.createColumnExpression("id"),
        blogsWithTagBuilder.toInnerSelectExpr()));
    builder.addOrderBy(entityBlog.createColumnExpression("dateCreated"), false);
    builder.limit(10, 0);
    System.out.println(builder.toSelectClause().toFormatted());
  }

  @Test
  public void testUpdateBlog() {
    // UPDATE blog b set title = ?, ...
    UpdateClauseBuilder builder = new UpdateClauseBuilder();
    builder.update(entityBlog.createTable());
    builder.set("title", "foo");
    builder.set("content", "bar");
    builder.where(Conditions.eq(entityBlog.createColumnExpression("id"),
        Values.create(1l)));
    System.out.println(builder.toUpdateClause().toFormatted());
  }

  @Test
  public void testDeleteBlogDeleteBlog() {
    // DELETE FROM blog WHERE id = ?
    DeleteClauseBuilder builder = new DeleteClauseBuilder();
    builder.deleteFrom(entityBlog.createTable(false));
    builder.where(Conditions.eq(entityBlog.createColumnExpression("id", false),
        Values.create(1l)));
    System.out.println(builder.toDeleteClause());
  }

  @Test
  public void testDeleteBlogTags() {
    // DELETE FROM blog_tags WHERE blog_id = ?
    DeleteClauseBuilder builder = new DeleteClauseBuilder();
    builder.deleteFrom(entityBlogTags.createTable(false));
    builder.where(Conditions.eq(
        entityBlogTags.createColumnExpression("blogId", false),
        Values.create(1l)));
    System.out.println(builder.toDeleteClause());
  }

  @Test
  public void testDeleteBlogComments() {
    // DELETE FROM comment WHERE blog_id = ?
    DeleteClauseBuilder builder = new DeleteClauseBuilder();
    builder.deleteFrom(entityComment.createTable(false));
    builder.where(Conditions.eq(
        entityComment.createColumnExpression("blogId", false),
        Values.create(1l)));
    System.out.println(builder.toDeleteClause().toFormatted());
  }

}
