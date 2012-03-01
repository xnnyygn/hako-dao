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
package org.hako.dao.user;

import org.hako.dao.mapping.field.SimpleField;

/**
 * User.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class UserDao {

  public final static String TABLE_NAME = "user";
  public final static String TABLE_ALIAS = "u";
  public final static SimpleField<Long> FIELD_ID = new SimpleField<Long>(TABLE_ALIAS, "id",
      true);
  public final static SimpleField<String> FIELD_NAME = new SimpleField<String>(TABLE_ALIAS,
      "name", false);

}
