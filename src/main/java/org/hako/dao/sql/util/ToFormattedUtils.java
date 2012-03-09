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

import org.hako.Option;
import org.hako.dao.sql.Sql;

/**
 * Utilities for format SQL.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ToFormattedUtils {

  /**
   * Append option to string builder.
   * <p>
   * Append formatted SQL to string builder if {@code objOpt} has value and
   * value is instance of {@link Sql}. A formatted SQL was retrieved from
   * {@link Sql#toFormatted()}.
   * </p>
   * <p>
   * Since {@code Option<Sql>} is not applicable to all SQl option, use object
   * option instead.
   * </p>
   * 
   * @param prefix
   * @param depth
   * @param objOpt object option
   * @param builder string builder.
   * @see #appendFormattedSql(String, int, Sql, StringBuilder)
   */
  public static void appendFormattedSql(String prefix, int depth,
      Option<?> objOpt, StringBuilder builder) {
    if (objOpt.hasValue() && (objOpt.get() instanceof Sql)) {
      appendFormattedSql(prefix, depth, (Sql) objOpt.get(), builder);
    }
  }

  /**
   * Append prefix and SQL to string builder.
   * 
   * @param prefix
   * @param depth
   * @param sql
   * @param builder
   */
  public static void appendFormattedSql(String prefix, int depth, Sql sql,
      StringBuilder builder) {
    appendMargins(builder, depth - 1);
    builder.append(prefix);
    appendMargins(builder, depth);
    builder.append(sql.toFormatted(depth));
    builder.append('\n');
  }

  /**
   * Append margins to string builder.
   * 
   * @param builder
   * @param count
   */
  private static void appendMargins(StringBuilder builder, int count) {
    for (int i = 0; i < count; i++) {
      builder.append(Sql.MARGIN);
    }
  }

  /**
   * Join multiple SQL prepared string with delimiter.
   * 
   * @param depth
   * @param delimiter
   * @param sqls
   * @return delimiter separated SQL
   */
  // TODO change third parameter to Collection<?>
  public static String formatAndConcat(int depth, String delimiter, Sql... sqls) {
    StringBuilder builder = new StringBuilder();
    if (sqls.length > 0) {
      int newDepth = depth + 1;
      for (Sql sql : sqls) {
        // TODO check count
        builder.append(sql.toFormatted(newDepth)).append(delimiter);
        appendMargins(builder, depth);
      }
      builder.setLength(builder.length() - delimiter.length()
          - Sql.MARGIN.length() * depth);
    }
    return builder.toString();
  }

}
