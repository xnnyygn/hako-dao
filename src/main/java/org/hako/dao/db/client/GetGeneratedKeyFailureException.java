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
package org.hako.dao.db.client;

import org.hako.dao.db.DatabaseException;

/**
 * If failed to get auto generated key, this exception will be thrown.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class GetGeneratedKeyFailureException extends DatabaseException {

  private static final long serialVersionUID = -2237564316254338288L;

  /**
   * Create.
   * 
   * @param message
   */
  public GetGeneratedKeyFailureException(String message) {
    super(message);
  }

  /**
   * Create.
   * 
   * @param cause
   */
  public GetGeneratedKeyFailureException(Throwable cause) {
    super(cause);
  }


}
