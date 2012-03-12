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
package org.hako.dao.sql.builder;

import java.util.Collection;
import java.util.Iterator;

import org.hako.Option;
import org.hako.dao.sql.Sql;

/**
 * A builder for {@link Sql#toFormatted(int)}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ToFormattedBuilder {

  private final StringBuilder builder;

  /**
   * Default constructor.
   */
  public ToFormattedBuilder() {
    this("");
  }

  /**
   * Create with initializing string.
   * 
   * @param initStr initializing string
   * @see StringBuilder#StringBuilder(String)
   */
  public ToFormattedBuilder(String initStr) {
    builder = new StringBuilder(initStr);
  }

  /**
   * Append char.
   * 
   * @param string
   * @return this
   * @see StringBuilder#append(char)
   */
  public ToFormattedBuilder append(char c) {
    builder.append(c);
    return this;
  }

  /**
   * Append string.
   * 
   * @param string
   * @return this
   * @see StringBuilder#append(String)
   */
  public ToFormattedBuilder append(String string) {
    builder.append(string);
    return this;
  }

  /**
   * Append with {@code 0} margin count.
   * 
   * @param obj
   * @return this
   */
  public ToFormattedBuilder append(Object obj) {
    return append(0, obj);
  }

  /**
   * Append with margin count.
   * 
   * @param marginCount margin count
   * @param obj
   * @return this
   */
  public ToFormattedBuilder append(int marginCount, Object obj) {
    if (obj instanceof Sql) {
      return append(((Sql) obj).toFormatted(marginCount));
    }
    return appendMargins(marginCount).append(obj.toString());
  }

  /**
   * Append prefix and object option with {@code 0} margin count.
   * 
   * @param prefix
   * @param objOpt
   * @return this
   * @see #append(String, int, Option)
   */
  public ToFormattedBuilder append(String prefix, Option<?> objOpt) {
    return append(prefix, 0, objOpt);
  }

  /**
   * Append prefix and object option with margin count.
   * 
   * @param prefix
   * @param marginCount margin count
   * @param objOpt
   * @return this
   */
  public ToFormattedBuilder append(String prefix, int marginCount,
      Option<?> objOpt) {
    if (objOpt.hasValue()) {
      return append(marginCount - 1, prefix).append(marginCount, objOpt.get());
    }
    return this;
  }

  /**
   * Append with suffix.
   * 
   * @param prefix
   * @param marginCount
   * @param objOpt
   * @param suffix
   * @return this
   */
  public ToFormattedBuilder append(String prefix, int marginCount,
      Option<?> objOpt, String suffix) {
    if (objOpt.hasValue()) {
      return append(prefix, marginCount, objOpt).append(suffix);
    }
    return this;
  }

  /**
   * Append prefix and SQL.
   * 
   * @param prefix
   * @param marginCount margin count
   * @param sql
   * @return this
   * @see Sql#toFormatted(int)
   */
  public ToFormattedBuilder append(String prefix, int marginCount, Sql sql) {
    return append(marginCount - 1, prefix).append(sql.toFormatted(marginCount));
  }

  /**
   * Append margins and prefix.
   * 
   * @param marginCount
   * @param prefix
   * @return this
   */
  public ToFormattedBuilder append(int marginCount, String prefix) {
    return appendMargins(marginCount).append(prefix);
  }

  /**
   * Append with suffix.
   * 
   * @param prefix
   * @param marginCount
   * @param sql
   * @param suffix
   * @return
   */
  public ToFormattedBuilder append(String prefix, int marginCount, Sql sql,
      String suffix) {
    return append(prefix, marginCount, sql).append(suffix);
  }

  /**
   * Append SQLs.
   * 
   * @param marginCount
   * @param delimiter
   * @param collections
   * @return this
   */
  public ToFormattedBuilder append(int marginCount, String delimiter,
      Collection<?> collections) {
    if (!collections.isEmpty()) {
      Iterator<?> iterator = collections.iterator();
      append(marginCount, iterator.next());
      while (iterator.hasNext()) {
        append(delimiter).append(marginCount, iterator.next());
      }
    }
    return this;
  }

  /**
   * Append margins.
   * 
   * @param count
   * @return this
   * @see Sql#MARGIN
   */
  public ToFormattedBuilder appendMargins(int count) {
    for (int i = 0; i < count; i++) {
      append(Sql.MARGIN);
    }
    return this;
  }

  /**
   * Return formatted SQL.
   * 
   * @see StringBuilder#toString()
   */
  public String toString() {
    return builder.toString();
  }

}
