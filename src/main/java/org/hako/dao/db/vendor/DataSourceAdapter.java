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
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * An adapter of {@link DataSource} to {@link DbVendor}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 * @see DataSource#getConnection()
 */
public class DataSourceAdapter extends SingleDbVendor {

  private final DataSource source;

  /**
   * Create
   * 
   * @param source
   */
  public DataSourceAdapter(DataSource source) {
    super();
    this.source = source;
  }

  public Connection connect() throws ConnectException {
    try {
      return source.getConnection();
    } catch (SQLException e) {
      throw new ConnectException(e);
    }
  }

}
