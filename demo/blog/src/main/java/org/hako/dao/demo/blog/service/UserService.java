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

import org.hako.Option;
import org.hako.dao.EntityManager;
import org.hako.dao.demo.blog.domain.User;
import org.hako.dao.restriction.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for user.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Service
public class UserService {

  private EntityManager entityManager;

  /**
   * Find user by name and password.
   * 
   * @param name
   * @param password
   * @return some user or none
   */
  public Option<User> findByNameAndPassword(String name, String password) {
    return entityManager.findBy(
        User.class, 
        Restrictions.eq("name", name),
        Restrictions.eq("password", password));
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
