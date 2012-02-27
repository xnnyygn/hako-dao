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
package org.hako.dao.sql.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hako.Some;
import org.hako.dao.sql.Sql;

/**
 * A utilities for multiple SQL.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MultipleSqlUtils {

  /**
   * Join multiple SQL prepared string with {@code ",  "}.
   * 
   * @param sqls multiple SQL
   * @return delimiter separated SQL
   */
  public static String toPrepared(Sql... sqls) {
    return toPrepared(", ", sqls);
  }

  public static String toPrepared(String delimeter, Sql... sqls) {
    StringBuilder builder = new StringBuilder();
    if (sqls.length > 0) {
      for (Sql sql : sqls) {
        builder.append(sql.toPrepared()).append(delimeter);
      }
      builder.setLength(builder.length() - delimeter.length());
    }
    return builder.toString();
  }

  public static List<Object> getParams(Object... objects) {
    List<Sql> sqls = new ArrayList<Sql>();
    for (Object obj : objects) {
      if (obj instanceof Sql) {
        sqls.add((Sql) obj);
      } else if (obj instanceof Some<?>) {
        // handle option
        Object value = ((Some<?>) obj).get();
        if (value instanceof Sql) {
          sqls.add((Sql) value);
        }
      } else if (obj instanceof Collection<?>) {
        // handle inner collection
        for (Object innerObj : (Collection<?>) obj) {
          if (innerObj instanceof Sql) {
            sqls.add((Sql) innerObj);
          }
        }
      }
    }
    return getParams(sqls);
  }

  public static List<Object> getParams(Sql... sqls) {
    return getParams(Arrays.asList(sqls));
  }

  public static List<Object> getParams(List<Sql> sqls) {
    if (sqls.isEmpty()) {
      return Sql.NO_PARAM;
    }
    List<Object> all = new ArrayList<Object>();
    for (Sql sql : sqls) {
      all.addAll(sql.getParams());
    }
    return all;
  }

}
