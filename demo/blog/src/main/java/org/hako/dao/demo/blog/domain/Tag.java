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
 * Domain tag.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Entity(tableAlias = "t")
public class Tag {

  private Long id;
  private String text;

  /**
   * Default constructor.
   */
  public Tag() {
    super();
  }

  /**
   * Create with text.
   * 
   * @param text
   */
  public Tag(String text) {
    this.text = text;
  }

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
   * Getter method for property <tt>text</tt>.
   * 
   * @return property value of text
   */
  @Field
  public String getText() {
    return text;
  }

  /**
   * Setter method for property <tt>text</tt>.
   * 
   * @param text value to be assigned to property text
   */
  public void setText(String text) {
    this.text = text;
  }

}
