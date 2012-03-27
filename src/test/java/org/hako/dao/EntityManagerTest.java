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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.hako.dao.mapper.Blog;
import org.hako.dao.mapper.Comment;
import org.hako.dao.mapper.EntityMapper;
import org.hako.dao.mapping.EntityMeta;
import org.hako.dao.restriction.Restrictions;
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

  @Test
  public void testCount() {
    System.out.println(entityManager.count(Blog.class));
  }

  @Test
  public void testCountBy() {
    System.out.println(entityManager.countBy(Blog.class,
        Restrictions.eq("userId", 1l)));
  }

  @Test
  public void testFindBy() {
    System.out.println(entityManager.findBy(Blog.class,
        Restrictions.eq("id", 1l), Restrictions.eq("userId", 1l)));
  }

  @Test
  public void testList() {
    System.out.println(entityManager.list(Blog.class, new ListParams(
        "dateCreated", false)));
  }

  @Test
  public void testListBy() {
    System.out.println(entityManager.listBy(Blog.class, new ListParams(
        "dateCreated", false), Restrictions.eq("userId", 1l)));
  }

  @Test
  public void testSave() {
    Blog blog = new Blog();
    blog.setTitle("foo");
    blog.setContent("bar");
    blog.setDateCreated(new Timestamp(System.currentTimeMillis()));
    blog.setUserId(1l);
    entityManager.save(blog);
  }

  @Test
  public void testUpdate() {
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put("title", "foo");
    entityManager.update(Blog.class, properties, 1l);
  }

  @Test
  public void testDeleteById() {
    entityManager.deleteById(Blog.class, 1l);
  }

}
