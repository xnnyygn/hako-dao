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
package org.hako.dao.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.hako.dao.sql.clause.select.selection.AsteriskSelection;
import org.hako.dao.sql.clause.select.selection.TableAliasAsteriskSelection;
import org.junit.Test;

/**
 * SQL compare.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class SqlCompareTest {

  @Test
  public void testCompareAsteriskSelection() {
    assertEquals(new AsteriskSelection(), new AsteriskSelection());
  }

  @Test
  public void testCompareTableAsteriskSelection() {
    assertNotSame(new TableAliasAsteriskSelection("a"), new TableAliasAsteriskSelection(
        "b"));
    assertEquals(new TableAliasAsteriskSelection("b"), new TableAliasAsteriskSelection(
        "b"));
  }
}
