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

import org.hako.dao.ListParams;
import org.hako.dao.demo.blog.domain.Blog;

/**
 * Blog service.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BlogService {

  private BlogManager blogManager;


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
   * Setter method for property <tt>blogManager</tt>.
   * 
   * @param blogManager value to be assigned to property blogManager
   */
  public void setBlogManager(BlogManager blogManager) {
    this.blogManager = blogManager;
  }

}
