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

import java.sql.Timestamp;

import org.hako.dao.mapper.Entity;
import org.hako.dao.mapper.Field;
import org.hako.dao.mapper.Id;

/**
 * Domain comment.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Entity(tableAlias = "c")
public class Comment {

  private Long id;
  private String content;
  private String username;
  private Long blogId;
  private Timestamp dateCommented;

  /**
   * Getter method for property <tt>id</tt>.
   * 
   * @return property value of id
   */
  @Id
  @Field
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
   * Getter method for property <tt>content</tt>.
   * 
   * @return property value of content
   */
  @Field
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
   * Getter method for property <tt>username</tt>.
   * 
   * @return property value of username
   */
  @Field
  public String getUsername() {
    return username;
  }

  /**
   * Setter method for property <tt>username</tt>.
   * 
   * @param username value to be assigned to property username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Getter method for property <tt>blogId</tt>.
   * 
   * @return property value of blogId
   */
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
   * Getter method for property <tt>dateCommented</tt>.
   * 
   * @return property value of dateCommented
   */
  @Field
  public Timestamp getDateCommented() {
    return dateCommented;
  }

  /**
   * Setter method for property <tt>dateCommented</tt>.
   * 
   * @param dateCommented value to be assigned to property dateCommented
   */
  public void setDateCommented(Timestamp dateCommented) {
    this.dateCommented = dateCommented;
  }

}
