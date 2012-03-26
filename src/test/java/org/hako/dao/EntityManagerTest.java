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
package org.hako.dao;

import java.util.Map;

import org.hako.dao.mapper.Blog;
import org.hako.dao.mapper.Comment;
import org.hako.dao.mapper.EntityMapper;
import org.hako.dao.mapping.EntityMeta;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of {@link EntityManager}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManagerTest {

  private static EntityManager entityManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    EntityMapper entityMapper = new EntityMapper();
    Map<Class<?>, EntityMeta> entityMetaMap =
        entityMapper.map(Blog.class, Comment.class);
    entityManager =
        new EntityManager(H2MemDbClientFlyweight.get(), entityMetaMap);
  }

  @Test
  public void testGet() {
    System.out.println(entityManager.get(Blog.class, 1l));
  }

}
