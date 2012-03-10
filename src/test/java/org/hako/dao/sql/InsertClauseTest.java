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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hako.dao.sql.clause.insert.InsertClause;
import org.hako.dao.sql.clause.insert.values.ExpressionValues;
import org.hako.dao.sql.expression.Expression;
import org.hako.dao.sql.expression.value.DefaultValue;
import org.hako.dao.sql.expression.value.LongValue;
import org.hako.dao.sql.expression.value.StringValue;
import org.junit.Test;

/**
 * Test of insert clause.
 *
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class InsertClauseTest {

  @Test
  public void testToPrepared() {
    List<Expression> expressions = new ArrayList<Expression>();
    expressions.add(new StringValue("title0"));
    expressions.add(new StringValue("content0"));
    expressions.add(new DefaultValue());
    expressions.add(new LongValue(1));
    InsertClause clause =
        new InsertClause("BLOG", Arrays.asList("title", "content",
            "date_created", "user_id"), new ExpressionValues(expressions));
    System.out.println(clause.toPrepared());
    System.out.println(clause.getParams());
  }

}
