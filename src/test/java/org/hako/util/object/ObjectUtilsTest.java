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
package org.hako.util.object;

import java.sql.Timestamp;

import org.hako.dao.mapper.Blog;
import org.hako.util.object.ObjectUtils;
import org.junit.Test;

/**
 * Test of {@link ObjectUtils}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ObjectUtilsTest {

  /**
   * Test method for
   * {@link org.hako.util.object.ObjectUtils#getProperties(java.lang.Object)}.
   */
  @Test
  public void testGetProperties() {
    Blog b = new Blog();
    b.setId(1l);
    b.setTitle("foo");
    b.setContent("bar");
    b.setDateCreated(new Timestamp(System.currentTimeMillis()));
    b.setUserId(1l);
    System.out.println(ObjectUtils.getProperties(b));
  }

  @Test
  public void testGetPropertiesAllowNull() throws Exception {
    Blog b = new Blog();
    b.setId(1l);
    b.setTitle("foo");
    b.setContent("bar");
    b.setUserId(1l);
    // dateCreated must be null
    System.out.println(ObjectUtils.getProperties(b));
  }

}
