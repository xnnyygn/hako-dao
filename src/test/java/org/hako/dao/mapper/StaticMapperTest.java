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

import static org.junit.Assert.assertEquals;

import org.hako.dao.mapper.StaticMapper;
import org.junit.Test;

/**
 * Test of {@link StaticMapper}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class StaticMapperTest {

  private StaticMapper mapper = new StaticMapper();

  @Test
  public void testSetup() {
    System.out.println(mapper.setup(Blog.class));
  }

  @Test
  public void testToDashSeparatedColumnName() {
    assertEquals("id", mapper.toDashSeparatedColumnName("id"));
    assertEquals("date_created",
        mapper.toDashSeparatedColumnName("dateCreated"));
    assertEquals("a_b_c", mapper.toDashSeparatedColumnName("aBC"));
  }
}
