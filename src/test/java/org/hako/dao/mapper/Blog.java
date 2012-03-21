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
package org.hako.dao.mapper;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hako.dao.mapper.Entity;
import org.hako.dao.mapper.Field;
import org.hako.dao.mapper.Id;

/**
 * Entity blog.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Entity(tableName = "blog", tableAlias = "b")
public class Blog {

  private Long id;
  private String title;
  private String content;
  private Timestamp dateCreated;
  private Long userId;
  
  /**
   * Get id.
   * 
   * @return the id
   */
  @Id
  @Field
  public Long getId() {
    return id;
  }

  /**
   * Set id.
   * 
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get title.
   * 
   * @return the title
   */
  @Field
  public String getTitle() {
    return title;
  }

  /**
   * Set title.
   * 
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Get content.
   * 
   * @return the content
   */
  @Field
  public String getContent() {
    return content;
  }

  /**
   * Set content.
   * 
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Get create date.
   * 
   * @return the dateCreated
   */
  @Field
  public Timestamp getDateCreated() {
    return dateCreated;
  }

  /**
   * Set create date.
   * 
   * @param dateCreated the dateCreated to set
   */
  public void setDateCreated(Timestamp dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Get user id.
   * 
   * @return the userId
   */
  @Field
  public Long getUserId() {
    return userId;
  }

  /**
   * Set user id.
   * 
   * @param userId the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }
  
}
