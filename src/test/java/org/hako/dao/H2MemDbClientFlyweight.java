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

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.db.client.DefaultDbClient;
import org.hako.dao.db.vendor.DbcpVendor;
import org.hako.dao.db.vendor.SingletonConnectionDbVender;

/**
 * A flyweight object to create a singleton H2 memory database client.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class H2MemDbClientFlyweight {

  private static DbClient client;

  /**
   * Get database client instance.
   * 
   * @return instance
   */
  public static DbClient get() {
    if (client == null) {
      client = createDbClient();
    }
    return client;
  }

  /**
   * Create database client.
   * 
   * @return client
   */
  private static DbClient createDbClient() {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("driverClassName", "org.h2.Driver");
    props.put("url", "jdbc:h2:mem:blogTest");
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
      throw new IllegalStateException(e);
    }
  }

}
