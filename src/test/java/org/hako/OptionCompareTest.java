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
package org.hako;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Compare test of {@link Option}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class OptionCompareTest {

  @Test
  public void testCompareSome() {
    Some<String> someA1 = new Some<String>("A");
    Some<String> someA2 = new Some<String>("A");
    Some<String> someB = new Some<String>("A");
    assertEquals(someA1, someA2);
    assertNotSame(someA1, someB);
  }

  @Test
  public void testCompareNone() {
    None<String> noneA = new None<String>();
    None<Integer> noneB = new None<Integer>();
    assertEquals(noneA, noneB);
  }

}
