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
package org.hako.dao.mapper.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import org.hako.dao.H2MemDbClientFlyweight;
import org.hako.dao.ListParams;
import org.hako.dao.restriction.LikeRestriction.MatchMode;
import org.hako.dao.restriction.Restrictions;
import org.junit.Test;

/**
 * Test of {@link EntityManager}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManagerTest {

  private EntityManager<Blog> manager = new EntityManager<Blog>(
      H2MemDbClientFlyweight.get(), Blog.class);

  @Test
  public void testGet() {
    assertTrue(manager.get(1l).hasValue());
  }

  @Test
  public void testCount() {
    assertTrue("blog count must greater than zero", manager.count() > 0);
  }

  @Test
  public void testCounBy() {
    assertEquals(2, manager.countBy(Restrictions.eq("userId", 1l)));
    assertEquals(0, manager.countBy(Restrictions.eq("userId", 2l)));
  }

  @Test
  public void testFindBy() {
    Timestamp lastYear =
        new Timestamp(new GregorianCalendar(2011, 0, 1).getTimeInMillis());
    assertTrue(manager.findBy(Restrictions.eq("id", 1l),
        Restrictions.like("title", "F", MatchMode.STARTS_WITH),
        Restrictions.gt("dateCreated", lastYear)).hasValue());
  }

  @Test
  public void testList() {
    assertEquals(2, manager.list(new ListParams(10, 1, "dateCreated", false))
        .size());
  }

  @Test
  public void testListBy() {
    // TODO here
  }

}
