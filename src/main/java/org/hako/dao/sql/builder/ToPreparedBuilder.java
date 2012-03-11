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
 * A builder for {@link Sql#toPrepared()}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class ToPreparedBuilder {

  private final StringBuilder builder;


  /**
   * Default constructor.
   */
  public ToPreparedBuilder() {
    this("");
  }

  /**
   * Create with collection.
   * 
   * @param collection
   */
  public ToPreparedBuilder(Collection<?> collection) {
    this();
    append(collection);
  }

  /**
   * Create with initializing string.
   * 
   * @param initStr initializing string
   */
  public ToPreparedBuilder(String initStr) {
    builder = new StringBuilder(initStr);
  }

  /**
   * Append character.
   * 
   * @param c
   * @return this
   */
  public ToPreparedBuilder append(char c) {
    builder.append(c);
    return this;
  }

  /**
   * Append string.
   * 
   * @param string
   * @return this
   */
  public ToPreparedBuilder append(String string) {
    builder.append(string);
    return this;
  }

  /**
   * Append SQL.
   * 
   * @param sql
   * @return this
   */
  public ToPreparedBuilder append(Sql sql) {
    return append(sql.toPrepared());
  }

  /**
   * Append object.
   * 
   * @param object
   * @return
   */
  public ToPreparedBuilder append(Object object) {
    if (object instanceof Sql) {
      return append((Sql) object);
    }
    return append(object.toString());
  }

  /**
   * Append with prefix.
   * 
   * @param prefix
   * @param option
   * @return this
   */
  public ToPreparedBuilder append(String prefix, Option<?> option) {
    if (option.hasValue()) {
      return append(prefix).append(option.get());
    }
    return this;
  }

  /**
   * Append collection with delimiter {@code , }.
   * 
   * @param collection
   * @return this
   */
  public ToPreparedBuilder append(Collection<?> collection) {
    return append(", ", collection);
  }

  /**
   * Append collection.
   * 
   * @param delimiter
   * @param collection
   * @return this
   */
  public ToPreparedBuilder append(String delimiter, Collection<?> collection) {
    if (!collection.isEmpty()) {
      Iterator<?> iterator = collection.iterator();
      append(iterator.next());
      while (iterator.hasNext()) {
        append(delimiter).append(iterator.next());
      }
    }
    return this;
  }

  public String toString() {
    return builder.toString();
  }

}
