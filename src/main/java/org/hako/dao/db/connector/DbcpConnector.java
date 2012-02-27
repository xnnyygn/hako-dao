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
package org.hako.dao.db.connector;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * Database connector based on <a
 * href="http://commons.apache.org/dbcp/">common-dbcp</a>.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 * @see BasicDataSource
 */
public class DbcpConnector extends DataSourceAdapter {

  /**
   * Create with four usually properties.
   * 
   * @param driverClassName driver class name
   * @param url
   * @param username
   * @param password
   */
  public DbcpConnector(String driverClassName, String url, String username,
      String password) {
    this(applyMap("driverClassName", driverClassName, "url", url, "username",
        username, "password", password));
  }

  /**
   * Create map from varargs.
   * 
   * @param keyOrValues key or values
   * @return map
   * @throws IllegalArgumentException if some key does not have value
   */
  private static Map<String, Object> applyMap(Object... keyOrValues)
      throws IllegalArgumentException {
    int length = keyOrValues.length;
    if (length % 2 != 0) {
      throw new IllegalArgumentException(keyOrValues[length - 1]
          + " must has value");
    }
    Map<String, Object> map = new HashMap<String, Object>();
    for (int i = 0; i < length; i += 2) {
      map.put((String) keyOrValues[i], keyOrValues[i + 1]);
    }
    return map;
  }

  /**
   * Create with properties.
   * 
   * @param props properties
   */
  public DbcpConnector(Map<String, Object> props) {
    super(createBasicDataSource(props));
  }

  /**
   * Create {@link BasicDataSource} instance with properties.
   * 
   * @param props properties
   * @return basic data source
   */
  private static BasicDataSource createBasicDataSource(Map<String, Object> props) {
    BasicDataSource source = new BasicDataSource();
    try {
      for (Map.Entry<String, Object> e : props.entrySet()) {
        BeanUtils.setProperty(source, e.getKey(), e.getValue());
      }
      return source;
    } catch (Exception e) {
      // convert to runtime exception
      throw new RuntimeException(e);
    }
  }

}
