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
package org.hako.dao.db.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hako.None;
import org.hako.Option;
import org.hako.OptionUtils;
import org.hako.Some;
import org.hako.dao.HakoDaoException;
import org.hako.dao.db.DatabaseException;
import org.hako.dao.db.vendor.DbVendor;
import org.hako.dao.sql.Clause;
import org.hako.dao.sql.clause.delete.DeleteClause;
import org.hako.dao.sql.clause.insert.InsertClause;
import org.hako.dao.sql.clause.select.Limit;
import org.hako.dao.sql.clause.select.SelectClause;
import org.hako.dao.sql.clause.update.UpdateClause;

/**
 * Default implement.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class DefaultDbClient implements DbClient {

  protected final DbVendor connector;
  protected final boolean printSql;

  /**
   * Create without print sql.
   * 
   * @param connector
   */
  public DefaultDbClient(DbVendor connector) {
    this(connector, false);
  }

  /**
   * Create.
   * 
   * @param connector
   * @param printSql print SQL
   */
  public DefaultDbClient(DbVendor connector, boolean printSql) {
    super();
    this.connector = connector;
    this.printSql = printSql;
  }

  public List<Map<String, Object>> selectMultipleRows(SelectClause clause)
      throws HakoDaoException {
    try {
      return getRowValues(limitStatement(
          createPreparedStatement(clause, false), clause.getLimitOpt())
          .executeQuery());
    } catch (SQLException e) {
      throw convertException(e);
    }
  }

  /**
   * Limit statement.
   * 
   * @param ps prepared statement
   * @param limitOpt limit option
   * @return limited statement
   * @throws SQLException if database error occurred
   * @see PreparedStatement#setFetchSize(int)
   * @see PreparedStatement#setMaxRows(int)
   */
  private PreparedStatement limitStatement(PreparedStatement ps,
      Option<Limit> limitOpt) throws SQLException {
    if (limitOpt.hasValue()) {
      Limit limit = limitOpt.get();
      // ignore negative offset
      if (limit.getOffset() >= 0) {
        ps.setFetchSize(limit.getOffset());
      }
      // ignore negative and zero
      if (limit.getMax() > 0) {
        ps.setMaxRows(limit.getMax());
      }
    }
    return ps;
  }

  /**
   * Get row values.
   * 
   * @param rs result set
   * @return rows values
   * @throws SQLException if database error occurred
   * @see #getColumnNames(ResultSet)
   * @see ResultSet#next()
   * @see ResultSet#getObject(String)
   */
  private List<Map<String, Object>> getRowValues(ResultSet rs)
      throws SQLException {
    List<String> columnNames = getColumnNames(rs);
    List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    while (rs.next()) {
      Map<String, Object> props = new HashMap<String, Object>();
      for (String name : columnNames) {
        props.put(name, rs.getObject(name));
      }
      rows.add(props);
    }
    return rows;
  }

  /**
   * Get column names.
   * 
   * @param rs result set
   * @return column names
   * @throws SQLException if database error occurred
   * @see ResultSet#getMetaData()
   * @see ResultSetMetaData#getColumnCount()
   * @see ResultSetMetaData#getColumnLabel(int)
   */
  private List<String> getColumnNames(ResultSet rs) throws SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    List<String> names = new ArrayList<String>();
    int count = meta.getColumnCount();
    for (int i = 1; i <= count; i++) {
      names.add(meta.getColumnLabel(i));
    }
    return names;
  }

  public Option<Map<String, Object>> selectSingleRow(SelectClause clause) {
    List<Map<String, Object>> rows = selectMultipleRows(clause);
    switch (rows.size()) {
      case 0:
        return new None<Map<String, Object>>();
      case 1:
        return OptionUtils.some(rows.get(0));
      default:
        throw new UnexpectedRowCountException("unexpected row count ["
            + rows.size() + "]");
    }
  }

  public Option<Object> selectObject(SelectClause clause) {
    Option<Map<String, Object>> rowOpt = selectSingleRow(clause);
    if (rowOpt instanceof Some<?>) {
      Map<String, Object> values = rowOpt.get();
      int count = values.size();
      if (count == 1) {
        return OptionUtils.some(values.values().iterator().next());
      } else if (count > 1) {
        throw new UnexpectedColumnCountException("unexpected column count ["
            + values.size() + "]");
      }
    }
    return new None<Object>();
  }

  public int insert(InsertClause clause) {
    return executeUpdate(clause);
  }

  public Object insertAndGet(InsertClause clause) throws DatabaseException,
      GetGeneratedKeyFailureException {
    PreparedStatement ps = createPreparedStatement(clause, true);
    executeUpdate(ps);
    return getGeneratedKey(ps);
  }

  private Object getGeneratedKey(PreparedStatement ps)
      throws GetGeneratedKeyFailureException {
    try {
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) return rs.getObject(1);
    } catch (SQLException e) {
      throw new GetGeneratedKeyFailureException(e);
    }
    throw new GetGeneratedKeyFailureException("failed to get key");
  }

  public int update(UpdateClause clause) {
    return executeUpdate(clause);
  }

  public int delete(DeleteClause clause) throws DatabaseException {
    return executeUpdate(clause);
  }

  /**
   * Execute update with clause. And update don't need to generate key.
   * 
   * @param clause
   * @return updated record
   * @throws DatabaseException if database error occurred
   * @see #createPreparedStatement(Clause, boolean)
   * @see #executeUpdate(PreparedStatement)
   */
  private int executeUpdate(Clause clause) throws DatabaseException {
    return executeUpdate(createPreparedStatement(clause, false));
  }

  /**
   * Execute update and convert {@link SQLException} to
   * {@link DatabaseException}.
   * 
   * @param ps prepared statement
   * @return update record
   * @throws DatabaseException if database error occurred
   * @see SQLException
   */
  private int executeUpdate(PreparedStatement ps) throws DatabaseException {
    try {
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  /**
   * Create prepared statement. If you want to dynamically change target table
   * even target database on specified value in SQL clause or other condition,
   * please inherit this method and change the logic.
   * 
   * @param clause
   * @param generateKey should generate key
   * @return prepared statement
   * @throws DatabaseException if database error occurred
   * @see DbVendor#connect()
   * @see #createPreparedStatement(Connection, String, boolean, List)
   */
  protected PreparedStatement createPreparedStatement(Clause clause,
      boolean generateKey) throws DatabaseException {
    String preparedSql = clause.toPrepared();
    if (printSql) System.out.println(preparedSql);
    Connection conn = connector.connect();
    try {
      return createPreparedStatement(conn, preparedSql, generateKey,
          clause.getParams());
    } catch (Exception e) {
      throw convertException(e);
    } finally {
      connector.releaseQuietly(conn);
    }
  }

  /**
   * Create prepared statement with connection, prepared SQL, generate key and
   * parameters.
   * 
   * @param connection
   * @param preparedSql
   * @param generateKey
   * @param params
   * @return prepared statement
   * @throws SQLException if database error occurred
   * @see Connection#prepareStatement(String)
   * @see PreparedStatement#setObject(int, Object)
   */
  protected PreparedStatement createPreparedStatement(Connection connection,
      String preparedSql, boolean generateKey, List<Object> params)
      throws SQLException {
    // create prepared statement
    PreparedStatement ps =
        connection.prepareStatement(preparedSql, (generateKey
            ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS));
    // set parameters
    System.out.println(params);
    int count = params.size();
    int i = 0;
    while (i < count) {
      Object value = params.get(i++);
      ps.setObject(i, value);
    }
    return ps;
  }

  /**
   * If exception is subclass of {@link HakoDaoException}, just return it,
   * otherwise wrapped exception with {@link DatabaseException}.
   * 
   * @param e exception
   * @return hako DAO exception
   */
  protected HakoDaoException convertException(Exception e) {
    return (e instanceof HakoDaoException) ? (HakoDaoException) e
        : new DatabaseException(e);
  }

}
