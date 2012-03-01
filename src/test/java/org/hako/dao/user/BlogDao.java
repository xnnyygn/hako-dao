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
package org.hako.dao.user;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hako.dao.Entity;
import org.hako.dao.GenericDao;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapping.field.SimpleField;
import org.hako.dao.sql.clause.select.SelectClauseBuilder;
import org.hako.dao.sql.clause.select.table.JoinWithConditionTable;
import org.hako.dao.sql.clause.select.table.TableFactory;
import org.hako.dao.sql.expression.TableColumnName;
import org.hako.dao.sql.expression.condition.Conditions;

/**
 * DAO of {@link Blog}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class BlogDao extends GenericDao<Blog, Long> {

  public final static String TABLE_NAME = "blog";
  public final static String TABLE_ALIAS = "b";
  public final static SimpleField<Long> FIELD_ID = new SimpleField<Long>(TABLE_ALIAS, "id",
      true);
  public final static SimpleField<String> FIELD_TITLE = new SimpleField<String>(
      TABLE_ALIAS, "title", false);
  public final static SimpleField<String> FIELD_CONTENT = new SimpleField<String>(
      TABLE_ALIAS, "content", false);
  public final static SimpleField<Timestamp> FIELD_DATE_CREATED =
      new SimpleField<Timestamp>(TABLE_ALIAS, "date_created", "dateCreated", false);
  public final static SimpleField<Long> FIELD_USER_ID = new SimpleField<Long>(TABLE_ALIAS,
      "user_id", "userId", false);
  @SuppressWarnings("unchecked")
  public final static List<SimpleField<?>> FIELD_ALL = Arrays.asList(
      (SimpleField<?>) FIELD_ID, FIELD_TITLE, FIELD_CONTENT, FIELD_DATE_CREATED,
      FIELD_USER_ID);

  /**
   * Create.
   * 
   * @param client
   */
  public BlogDao(DbClient client) {
    super(client, new Entity(TABLE_NAME, TABLE_ALIAS, FIELD_ALL));
  }

  @Override
  protected Blog convert(Map<String, Object> props) {
    return new Blog(props);
  }

  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> listWithUserName() {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    builder.select(createSelection(Arrays.asList(FIELD_ID,
        (SimpleField<?>) FIELD_TITLE, FIELD_CONTENT, FIELD_DATE_CREATED,
        UserDao.FIELD_NAME)));
    builder.from(new JoinWithConditionTable(TableFactory.createSimpleAkaTable(
        TABLE_NAME, TABLE_ALIAS), TableFactory.createSimpleAkaTable(
        UserDao.TABLE_NAME, UserDao.TABLE_ALIAS), Conditions.eq(
        new TableColumnName(TABLE_ALIAS, FIELD_ID.getColumnName()),
        new TableColumnName(UserDao.TABLE_ALIAS, UserDao.FIELD_ID
            .getColumnName()))));
    return client.selectMultipleRows(builder.toSelectClause());
  }
}
