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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.ListParams;
import org.hako.dao.demo.blog.domain.Blog;
import org.hako.dao.demo.blog.domain.Comment;
import org.hako.dao.restriction.Restrictions;
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

  private BlogManager blogManager;
  private CommentManager commentManager;

  /**
   * List recent blogs.
   * 
   * @param offset
   * @param max
   * @return blogs
   */
  public List<Blog> listRecent(int offset, int max) {
    return blogManager.list(new ListParams(offset, max, "dateCreated", false));
  }

  /**
   * Save blog.
   * 
   * @param title
   * @param content
   */
  public void save(String title, String content) {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("title", title);
    props.put("content", content);
    props.put("dateCreated", new Timestamp(System.currentTimeMillis()));
    blogManager.save(props);
  }


  /**
   * Get blog by id.
   * 
   * @param id
   * @return some blog or none
   */
  public Option<Map<String, Object>> getForShow(Long id) {
    Option<Blog> blogOption = blogManager.get(id);
    if (blogOption.hasValue()) {
      Blog blog = blogOption.get();
      Map<String, Object> blogProps = new HashMap<String, Object>();
      blogProps.put("id", blog.getId());
      blogProps.put("title", blog.getTitle());
      blogProps.put("content", blog.getContent());
      blogProps.put("dateCreated", blog.getDateCreated());
      blogProps.put("comments", commentManager.listBy(new ListParams(
          "dateCommented", false), Restrictions.eq("blogId", blog.getId())));
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
    commentManager.save(comment);
  }

  /**
   * Setter method for property <tt>blogManager</tt>.
   * 
   * @param blogManager value to be assigned to property blogManager
   */
  @Autowired
  public void setBlogManager(BlogManager blogManager) {
    this.blogManager = blogManager;
  }

  /**
   * Setter method for property <tt>commentManager</tt>.
   * 
   * @param commentManager value to be assigned to property commentManager
   */
  @Autowired
  public void setCommentManager(CommentManager commentManager) {
    this.commentManager = commentManager;
  }

}
