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
package org.hako.dao.demo.blog.domain;

import org.hako.dao.mapper.Entity;
import org.hako.dao.mapper.Field;
import org.hako.dao.mapper.Id;

/**
 * Link table between {@link Blog} and {@link Tag}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Entity(tableName = "blog_tags", tableAlias = "bt")
public class BlogTags {

  private Long blogId;
  private Long tagId;

  /**
   * Default constructor.
   */
  public BlogTags() {
  }

  /**
   * Create.
   * 
   * @param blogId
   * @param tagId
   */
  public BlogTags(Long blogId, Long tagId) {
    super();
    this.blogId = blogId;
    this.tagId = tagId;
  }

  /**
   * Getter method for property <tt>blogId</tt>.
   * 
   * @return property value of blogId
   */
  @Id(generated = false)
  @Field
  public Long getBlogId() {
    return blogId;
  }

  /**
   * Setter method for property <tt>blogId</tt>.
   * 
   * @param blogId value to be assigned to property blogId
   */
  public void setBlogId(Long blogId) {
    this.blogId = blogId;
  }

  /**
   * Getter method for property <tt>tagId</tt>.
   * 
   * @return property value of tagId
   */
  @Id(generated = false)
  @Field
  public Long getTagId() {
    return tagId;
  }

  /**
   * Setter method for property <tt>tagId</tt>.
   * 
   * @param tagId value to be assigned to property tagId
   */
  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

}
