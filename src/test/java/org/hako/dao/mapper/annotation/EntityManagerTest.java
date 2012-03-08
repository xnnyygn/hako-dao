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

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.hako.dao.db.client.DefaultDbClient;
import org.hako.dao.db.vendor.DbcpVendor;
import org.hako.dao.db.vendor.SingletonConnectionDbVender;
import org.junit.Test;

/**
 * Test of {@link EntityManager}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManagerTest {

  private EntityManager<Blog> manager = new EntityManager<Blog>(
      createDefaultDbClient(), Blog.class);

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
  public void testGet() throws UnsupportedOperationException,
      IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    System.out.println(manager.get(1l));
  }

}
