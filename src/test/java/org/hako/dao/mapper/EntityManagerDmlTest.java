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

import org.hako.dao.H2MemDbClientFlyweight;
import org.junit.Test;

/**
 * Test of {@link EntityManager}, save, update and delete.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManagerDmlTest {

  private EntityManager<Blog, Long> manager = new EntityManager<Blog, Long>(
      H2MemDbClientFlyweight.get(), Blog.class);

  @Test
  public void testSave() {
    Blog b = new Blog();
    b.setTitle("foo");
    b.setContent("bar");
    b.setUserId(1l);
    b.setDateCreated(new Timestamp(System.currentTimeMillis()));
    manager.save(b);
  }

  @Test
  public void testUpdate() {
    Blog instance = new Blog();
    instance.setId(1l);
    instance.setTitle("FIRST2");
    manager.update(instance, true);
  }

  @Test
  public void testDelete() {
    manager.delete(2l);
  }

}
