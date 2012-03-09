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
package org.hako.dao.sql.walker;

import org.hako.dao.mapping.entity.TableName;
import org.hako.dao.sql.clause.select.SelectClause;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.function.Functions;
import org.hako.dao.sql.expression.value.Values;
import org.junit.Test;

/**
 * Test of {@link DefaultSqlFormatter}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class DefaultSqlFormatterTest {

  private static TableName TABLE_BLOG = new TableName("blog", "b");
  private static TableName TABLE_USER = new TableName("user", "u");
  private static TableName TABLE_TAG = new TableName("tag", "t");
  private static TableName TABLE_BLOG_TAGS = new TableName("blog_tags", "bt");
  private static TableName TABLE_COMMENT = new TableName("comment", "c");
  private DefaultSqlFormatter formatter = new DefaultSqlFormatter();

  @Test
  public void testFormatSelectClause() {
    System.out.println(formatter.format(createComplexSelectClause()));
  }

  private SelectClause createComplexSelectClause() {
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
    return builder.toSelectClause();
  }

}
