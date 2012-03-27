/**
 * Copyright 2012 XnnYygn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hako.dao;

/**
 * If not found entity meta for class, this exception will be thrown.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class MappingNotFoundException extends HakoDaoException {

  private static final long serialVersionUID = 6451185834625735555L;

  /**
   * Create.
   * 
   * @param message
   */
  public MappingNotFoundException(String message) {
    super(message);
  }

}