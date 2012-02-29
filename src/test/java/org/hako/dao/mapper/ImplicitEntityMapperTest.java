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
package org.hako.dao.mapper;

import org.hako.dao.mapper.implict.ImplicitEntityBuilder;
import org.hako.dao.mapper.implict.ImplicitEntityMapper;
import org.junit.Test;

/**
 * Test of {@link ImplicitEntityMapper}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ImplicitEntityMapperTest {

  class Blog {
    public User user;
  }

  public static class User {
    
    public String name;

    public static void postSetup(ImplicitEntityBuilder builder) {
      builder.setTableName("user");
    }

  }

  /**
   * Test method for
   * {@link org.hako.dao.mapper.implict.ImplicitEntityMapper#setup(java.lang.Class)}
   * .
   */
  @Test
  public void testSetup() {
    ImplicitEntityMapper mapper = new ImplicitEntityMapper();
    System.out.println(mapper.setup(Blog.class));
  }

}
