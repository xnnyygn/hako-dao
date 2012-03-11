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
package org.hako.dao.mapper.annotation;

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

  private EntityManager<Blog> manager = new EntityManager<Blog>(
      H2MemDbClientFlyweight.get(), Blog.class);

  @Test
  public void testSave() {
    Blog b = new Blog();
    b.title = "foo";
    b.content = "bar";
    b.userId = 1l;
    b.dateCreated = new Timestamp(System.currentTimeMillis());
    manager.save(b);
  }

  @Test
  public void testUpdate() {
    Blog instance = new Blog();
    instance.id = 1l;
    instance.title = "FIRST2";
    manager.update(instance);
  }
  
  @Test
  public void testDelete(){
    // TODO here
     manager.delete(2l);
  }
  
}
