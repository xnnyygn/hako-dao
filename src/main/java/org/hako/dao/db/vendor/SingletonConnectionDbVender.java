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

/**
 * A database vendor always return the same connection, may be useful for
 * in-memory database.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SingletonConnectionDbVender extends SingleDbVendor {

  private final Connection connection;
  
  /**
   * Create.
   * 
   * @param delegate
   */
  public SingletonConnectionDbVender(DbVendor delegate) {
    super();
    connection = delegate.connect();
  }


  public Connection connect() throws ConnectException {
    return connection;
  }


  @Override
  public void releaseQuietly(Connection connection) {
    // do nothing
  }
  
}
