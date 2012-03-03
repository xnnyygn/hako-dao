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
package org.hako.dao.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hako.dao.GenericDao;
import org.hako.dao.ListParams;
import org.hako.dao.db.client.DefaultDbClient;
import org.hako.dao.db.vendor.DbcpVendor;
import org.hako.dao.db.vendor.SingletonConnectionDbVender;
import org.hako.dao.mapping.field.MappedField;
import org.hako.dao.restriction.RestrictionBuilder;
import org.hako.dao.restriction.Restrictions;
import org.hako.dao.user.domain.Blog;
import org.junit.Test;

/**
 * Test of {@link BlogDao}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BlogDaoTest {

  private static GenericDao blogDao = new GenericDao(createDefaultDbClient(),
      Blog.class);

  /**
   * Create default database client instance.
   * 
   * @return instance
   */
  private static DefaultDbClient createDefaultDbClient() {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("driverClassName", "org.h2.Driver");
    props.put("url", "jdbc:h2:mem:blogDev");
    props.put("username", "sa");
    props.put("password", "");
    props.put("connectionInitSqls", loadInitSqls());
    return new DefaultDbClient(new SingletonConnectionDbVender(new DbcpVendor(
        props)), true);
  }

  /**
   * Load initializing SQL from file.
   * 
   * @return SQLs
   */
  private static List<String> loadInitSqls() {
    try {
      return Arrays.asList(IOUtils.toString(new FileReader(
          "src/test/resources/blog-init.sql")));
    } catch (IOException e) {
      // convert to runtime exception
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testGet() {
    assertTrue(blogDao.get(1l).hasValue());
  }

  @Test
  public void testSaveAndDelete() {
    Map<MappedField<?>, Object> props = new HashMap<MappedField<?>, Object>();
    props.put(Blog.title, "title0");
    props.put(Blog.content, "content0");
    props.put(Blog.dateCreated, new Timestamp(System.currentTimeMillis()));
    props.put(Blog.userId, 1l);
    Long id = (Long) blogDao.save(props);
    System.out.println(id);
    System.out.println(blogDao.deleteById(id));
  }

  @Test
  public void testList() {
    System.out.println(blogDao.list(new ListParams(2, 1, Blog.dateCreated,
        false)));
  }

  @Test
  public void testUpdateTitle() {
    String oldTitle = blogDao.get(1l).get().get(Blog.title).get();
    Map<MappedField<?>, Object> props = new HashMap<MappedField<?>, Object>();
    String newTitle = "newTitle";
    props.put(Blog.title, newTitle);
    System.out.println(blogDao.update(props, 1l));
    assertEquals(newTitle, blogDao.get(1l).get().get(Blog.title).get());
    props.put(Blog.title, oldTitle);
    blogDao.update(props, 1l);
  }

  @Test
  public void testCount() {
    System.out.println(blogDao.count());
  }

  @Test
  public void testFind() {
    RestrictionBuilder builder = new RestrictionBuilder();
    builder.add(Restrictions.eq(Blog.id, 4l));
    builder.add(Restrictions.eq(Blog.userId, 1l));
    System.out.println(blogDao.findBy(builder.build()));
  }

  @Test
  public void testFindButNotExist() {
    RestrictionBuilder builder = new RestrictionBuilder();
    builder.add(Restrictions.eq(Blog.id, 4l));
    builder.add(Restrictions.eq(Blog.userId, 2l));
    System.out.println(blogDao.findBy(builder.build()));
  }

  @Test
  public void testListBy() {
    System.out.println(blogDao.listBy(Restrictions.eq(Blog.userId, 2l)));
  }

  @Test
  public void testCountBy() {
    System.out.println(blogDao.countBy(Restrictions.eq(Blog.userId, 1l)));
  }

  // @Test
  // public void testJoin() {
  // System.out.println(dao.listWithUserName());
  // }

}
