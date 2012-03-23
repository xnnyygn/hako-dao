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
 * Domain user.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Entity(tableAlias = "u")
public class User {

  private Long id;
  private String name;
  private String password;

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
   * Getter method for property <tt>name</tt>.
   * 
   * @return property value of name
   */
  @Field
  public String getName() {
    return name;
  }

  /**
   * Setter method for property <tt>name</tt>.
   * 
   * @param name value to be assigned to property name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter method for property <tt>password</tt>.
   * 
   * @return property value of password
   */
  @Field
  public String getPassword() {
    return password;
  }

  /**
   * Setter method for property <tt>password</tt>.
   * 
   * @param password value to be assigned to property password
   */
  public void setPassword(String password) {
    this.password = password;
  }

}
