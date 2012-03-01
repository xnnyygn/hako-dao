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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Use {@link DriverManager} to get connection.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class DriverManagerDbVendor extends SingleDbVendor {

  private final String url;
  private final String username;
  private final String password;

  /**
   * Create.
   * 
   * @param driverClassName driver class name
   * @param url
   * @param username
   * @param password
   * @throws ClassNotFoundException if class not found
   * @see Class#forName(String)
   */
  public DriverManagerDbVendor(String driverClassName, String url,
      String username, String password) throws ClassNotFoundException {
    super();
    Class.forName(driverClassName);
    this.url = url;
    this.username = username;
    this.password = password;
  }

  public Connection connect() throws ConnectException {
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      throw new ConnectException(e);
    }
  }

}
