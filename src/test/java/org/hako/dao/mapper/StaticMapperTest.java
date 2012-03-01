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

import java.util.Arrays;

import org.hako.dao.mapper.StaticMapper;
import org.hako.dao.mapping.entity.EntityMeta;
import org.hako.dao.user.domain.Blog;
import org.hako.dao.user.domain.Comment;
import org.hako.dao.user.domain.User;
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
    setup(Blog.class);
    setup(User.class);
    setup(Comment.class);
  }
  
  private void setup(Class<?> entityClass){
    EntityMeta entity = mapper.setup(entityClass);
    System.out.println("table name: " + entity.getTableName().getName());
    System.out.println("table alias: " + entity.getTableName().getAlias());
    System.out.println("fields: "
        + Arrays.toString(entity.getFieldColumnNames().toArray()));
    System.out.println("pk count: " + entity.getPkFields().size());
    System.out.println("normal count: " + entity.getNormalFields().size());
  }

  @Test
  public void testToDashSeparatedColumnName() {
    assertEquals("id", mapper.toDashSeparatedColumnName("id"));
    assertEquals("date_created",
        mapper.toDashSeparatedColumnName("dateCreated"));
    assertEquals("a_b_c", mapper.toDashSeparatedColumnName("aBC"));
  }
  
}
