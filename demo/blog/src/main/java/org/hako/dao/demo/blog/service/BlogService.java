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
package org.hako.dao.demo.blog.service;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.EntityManager;
import org.hako.dao.EntityMappings;
import org.hako.dao.ListParams;
import org.hako.dao.QueryCallback;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.demo.blog.domain.Blog;
import org.hako.dao.demo.blog.domain.BlogTags;
import org.hako.dao.demo.blog.domain.Comment;
import org.hako.dao.demo.blog.domain.Tag;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.restriction.Restrictions;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.condition.Conditions;
import org.hako.dao.sql.expression.value.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Blog service.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Service
public class BlogService {

  private EntityManager entityManager;

  /**
   * List recent blogs.
   * 
   * @param offset
   * @param max
   * @return blogs
   */
  public List<Blog> listRecent(int offset, int max) {
    ListParams params = new ListParams(offset, max, "dateCreated", false);
    return entityManager.list(Blog.class, params);
  }

  /**
   * Save blog.
   * 
   * @param title
   * @param content
   * @param tags
   */
  public void save(String title, String content, String tags) {
    // save blog
    // TODO move to a method
    Blog blog = new Blog();
    blog.setTitle(title);
    blog.setContent(content);
    blog.setDateCreated(new Timestamp(System.currentTimeMillis()));
    Long blogId = (Long) entityManager.save(blog);
    // save blog tags
    saveBlogTags(blogId, findOrCreateTags(tags));
  }

  /**
   * Save blog tags.
   * 
   * @param blogId
   * @param tagIds
   * @see BlogTags
   */
  private void saveBlogTags(Long blogId, List<Long> tagIds) {
    for (Long tagId : tagIds) {
      entityManager.save(new BlogTags(blogId, tagId));
    }
  }

  /**
   * Find or create tags. If cannot find tag by text, create and save tag. Return
   * id of tags.
   * 
   * @param tags comma separated tags
   * @return id of tags
   */
  private List<Long> findOrCreateTags(String tags) {
    List<Long> ids = new ArrayList<Long>();
    for (String tag : filterNotBlankTags(tags)) {
      ids.add(findOrCreateTag(tag));
    }
    return ids;
  }

  /**
   * Find tag by text and create if not found
   * 
   * @param text
   * @return
   */
  private Long findOrCreateTag(String text) {
    Option<Long> tagIdOption = findTagByText(text);
    if (tagIdOption.hasValue()) {
      return tagIdOption.get();
    }
    return (Long) entityManager.save(new Tag(text));
  }
  
  /**
   * Find tag by text.
   * 
   * @param text
   * @return some id or none
   */
  private Option<Long> findTagByText(final String text) {
    return entityManager.executeQuery(new QueryCallback<Option<Long>>() {
      public Option<Long> doQuery(DbClient client, EntityMappings entityMappings) {
        SelectClauseBuilder builder = new SelectClauseBuilder();
        EntityMeta tagMeta = entityMappings.findByClass(Tag.class);
        builder.select(tagMeta.createColumnExpression("id"));
        builder.from(tagMeta.createTable());
        Expression textColumn = tagMeta.createColumnExpression("text");
        builder.where(Conditions.eq(textColumn, Values.create(text)));
        return client.selectObject(builder.toSelectClause(), Long.class);
      }
    });
  }

  /**
   * Separated tags by comma and filter not blank ones.
   * 
   * @param tags
   * @return not blank tags
   * @see StringUtils#isNotBlank(String)
   */
  private List<String> filterNotBlankTags(String tags) {
    List<String> notBlankTags = new ArrayList<String>();
    for (String tag : tags.split("\\,")) {
      if (isNotBlank(tag)) {
        notBlankTags.add(tag);
      }
    }
    return notBlankTags;
  }

  /**
   * Get blog by id.
   * 
   * @param id
   * @return some blog or none
   */
  public Option<Map<String, Object>> getForShow(Long id) {
    Option<Blog> blogOption = entityManager.get(Blog.class, id);
    if (blogOption.hasValue()) {
      Blog blog = blogOption.get();
      Map<String, Object> blogProps = new HashMap<String, Object>();
      blogProps.put("id", blog.getId());
      blogProps.put("title", blog.getTitle());
      blogProps.put("content", blog.getContent());
      blogProps.put("dateCreated", blog.getDateCreated());
      blogProps.put("comments", entityManager.listBy(Comment.class,
          new ListParams("dateCommented", false),
          Restrictions.eq("blogId", blog.getId())));
      return new Some<Map<String, Object>>(blogProps);
    }
    return new None<Map<String, Object>>();
  }

  /**
   * Save comment.
   * 
   * @param content
   * @param username
   * @param blogId
   * @see CommentManager#save(Comment)
   */
  public void saveComment(String content, String username, Long blogId) {
    Comment comment = new Comment();
    comment.setBlogId(blogId);
    comment.setContent(content);
    comment.setDateCommented(new Timestamp(System.currentTimeMillis()));
    comment.setUsername(username);
    entityManager.save(comment);
  }

  /**
   * Setter method for property <tt>entityManager</tt>.
   * 
   * @param entityManager value to be assigned to property entityManager
   */
  @Autowired
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

}
