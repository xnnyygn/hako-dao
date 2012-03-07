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
package org.hako.dao;

import static org.junit.Assert.*;

import org.hako.dao.mapping.field.PrimaryKey;
import org.junit.Test;

/**
 * Test of {@link ListParamsBuilder}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ListParamsBuilderTest {

  @Test
  public void testToListParams() {
    int offset = 10;
    int max = 20;
    ListParamsBuilder builder = new ListParamsBuilder(offset, max);
    builder.addOrderBy(new PrimaryKey<Long>(), false);
    ListParams params = builder.toListParams();
    assertEquals(offset, params.getOffset());
    assertEquals(max, params.getMax());
    assertEquals(1, params.getOrderBys().size());
    assertFalse(params.getOrderBys().get(0).isAsc());
  }

}