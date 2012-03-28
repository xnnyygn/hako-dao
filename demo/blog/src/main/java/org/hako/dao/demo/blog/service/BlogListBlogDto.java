/**
 * Copyright 2012 XnnYygn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hako.dao.demo.blog.service;

/**
 * Blog DTO in blog list.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BlogListBlogDto {

  private Long id;
  private String title;
  private String content;
  private String tags;
  
  /**
   * Getter method for property <tt>id</tt>.
   * 
   * @return property value of id
   */
  public Long getId() {
    return id;
  }
  /**
   * Setter method for property <tt>id</tt>.
   * 
   * @param id value to be assigned to property id
   */
  public void setId(Long id) {
    this.id = id;
  }
  /**
   * Getter method for property <tt>title</tt>.
   * 
   * @return property value of title
   */
  public String getTitle() {
    return title;
  }
  /**
   * Setter method for property <tt>title</tt>.
   * 
   * @param title value to be assigned to property title
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * Getter method for property <tt>content</tt>.
   * 
   * @return property value of content
   */
  public String getContent() {
    return content;
  }
  /**
   * Setter method for property <tt>content</tt>.
   * 
   * @param content value to be assigned to property content
   */
  public void setContent(String content) {
    this.content = content;
  }
  /**
   * Getter method for property <tt>tags</tt>.
   * 
   * @return property value of tags
   */
  public String getTags() {
    return tags;
  }
  /**
   * Setter method for property <tt>tags</tt>.
   * 
   * @param tags value to be assigned to property tags
   */
  public void setTags(String tags) {
    this.tags = tags;
  }
  
}
