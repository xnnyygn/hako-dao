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
package org.hako.dao.db.vendor;

import java.sql.Connection;
import java.util.Map;


/**
 * A simple multiple database connector of multiple database solution. Use map
 * with object as key, connector as value to store connectors. If cannot find
 * connector, {@link NoSuchVendorException} was thrown.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleDbVendor extends AbstractDbVendor {

  private final Map<Object, DbVendor> connectorMap;

  /**
   * Create with connector map.
   * 
   * @param connectorMap connector map
   */
  public MultipleDbVendor(Map<Object, DbVendor> connectorMap) {
    super();
    this.connectorMap = connectorMap;
  }

  /**
   * Throw {@link UnsupportedOperationException}.
   */
  public Connection connect() throws ConnectException {
    throw new UnsupportedOperationException("please provide database key");
  }

  /**
   * Find connector by key to connector and get the connection.
   */
  public Connection connect(Object key) throws NoSuchVendorException,
      ConnectException {
    if (!connectorMap.containsKey(key)) {
      throw new NoSuchVendorException("no such connector with key [" + key
          + "]");
    }
    return connectorMap.get(key).connect();
  }

}
