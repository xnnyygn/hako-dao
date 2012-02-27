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

import org.hako.dao.HakoDaoException;

/**
 * If connector cannot find, this exception will be thrown.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 * @see {@link MultipleDbConnector#connect(Object)}
 */
public class NoSuchConnectorException extends HakoDaoException {

  private static final long serialVersionUID = 3341712976526193988L;

  /**
   * Create.
   * 
   * @param message
   */
  public NoSuchConnectorException(String message) {
    super(message);
  }
  
}
