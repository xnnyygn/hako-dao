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

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hako.dao.ListParams;
import org.hako.dao.SimpleField;
import org.hako.dao.db.client.DefaultDbClient;
import org.hako.dao.db.connector.DbcpConnector;
import org.hako.dao.restriction.RestrictionBuilder;
import org.hako.dao.restriction.Restrictions;
import org.junit.Test;

/**
 * Test of {@link BlogDao}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BlogDaoTest {

  private static BlogDao dao = new BlogDao(createDefaultDbClient());

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
    return new DefaultDbClient(new DbcpConnector(props), true);
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
    System.out.println(dao.get(1l));
  }

  @Test
  public void testSaveAndDelete() {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("title", "title0");
    props.put("content", "content0");
    props.put("dateCreated", new Timestamp(System.currentTimeMillis()));
    props.put("userId", 1l);
    Long id = dao.save(props);
    System.out.println(id);
    System.out.println(dao.deleteById(id));
  }

  @Test
  public void testList() {
    System.out.println(dao.list(new ListParams(2, 1,
        BlogDao.FIELD_DATE_CREATED, false)));
  }

  @Test
  public void testUpdateTitle() {
    String oldTitle = dao.get(1l, BlogDao.FIELD_TITLE).get().getTitle();
    Map<SimpleField<?>, Object> props = new HashMap<SimpleField<?>, Object>();
    props.put(BlogDao.FIELD_TITLE, "title1");
    System.out.println(dao.update(props, 1l));
    assertEquals("title1", dao.get(1l, BlogDao.FIELD_TITLE).get().getTitle());
    props.put(BlogDao.FIELD_TITLE, oldTitle);
    dao.update(props, 1l);
  }

  @Test
  public void testCount() {
    System.out.println(dao.count());
  }

  @Test
  public void testFindBy() {
    RestrictionBuilder builder = new RestrictionBuilder();
    builder.add(Restrictions.eq(BlogDao.FIELD_ID, 4l));
    builder.add(Restrictions.eq(BlogDao.FIELD_USER_ID, 1l));
    System.out.println(dao.findBy(builder.build()));
  }

  @Test
  public void testFindByNotExist() {
    RestrictionBuilder builder = new RestrictionBuilder();
    builder.add(Restrictions.eq(BlogDao.FIELD_ID, 4l));
    builder.add(Restrictions.eq(BlogDao.FIELD_USER_ID, 2l));
    System.out.println(dao.findBy(builder.build()));
  }

  @Test
  public void testListBy() {
    System.out.println(dao.listBy(Restrictions.eq(BlogDao.FIELD_USER_ID, 2l)));
  }

  @Test
  public void testCountBy() {
    System.out.println(dao.countBy(Restrictions.eq(BlogDao.FIELD_USER_ID, 1l)));
  }

  @Test
  public void testJoin() {
    System.out.println(dao.listWithUserName());
  }

}
