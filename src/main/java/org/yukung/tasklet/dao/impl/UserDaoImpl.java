/**
 * Copyright 2009-2010 Yusuke Ikeda. (@yukung) <yukung.i@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.yukung.tasklet.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.yukung.tasklet.dao.AbstractDao;
import org.yukung.tasklet.dao.UserDao;
import org.yukung.tasklet.entity.User;
import org.yukung.tasklet.exception.DataAccessException;
import org.yukung.tasklet.exception.TaskletException;

/**
 * <p>
 * ユーザ情報DAOインタフェースの実装クラスです。
 * </p>
 * 
 * @author yukung
 * 
 */
public class UserDaoImpl extends AbstractDao implements UserDao {

	/**
	 * <p>
	 * デフォルトコンストラクタ。
	 * </p>
	 */
	public UserDaoImpl() {
		super();
	}

	/**
	 * <p>
	 * コンストラクタ。DataSource経由での接続はこちらを使用します。
	 * </p>
	 * 
	 * @param ds
	 *            データソース
	 */
	public UserDaoImpl(DataSource ds) {
		super(ds);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.UserDao#findUserByUserName(java.lang.String)
	 */
	@Override
	public User findUserByUserName(String userName) {
		String sql = getSQLFromPropertyFile("findUserByUserName");
		BeanHandler<User> rsh = new BeanHandler<User>(User.class);
		try {
			return runner.query(sql, rsh, userName);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.dao.UserDao#findUserByUserNameAndPassword(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public User findUserByUserNameAndPassword(String userName, String password) {
		String sql = getSQLFromPropertyFile("findUserByUserNameAndPassword");
		BeanHandler<User> rsh = new BeanHandler<User>(User.class);
		try {
			return runner.query(sql, rsh, userName, password);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.UserDao#getUserCount(java.lang.String)
	 */
	@Override
	public Integer getUserCount(String userName) {
		String sql = getSQLFromPropertyFile("getUserCount");
		ResultSetHandler<Object> rsh = new ScalarHandler(1);
		try {
			return (Integer) runner.query(sql, rsh, userName);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.UserDao#addUser(java.sql.Connection,
	 * org.yukung.tasklet.entity.User)
	 */
	@Override
	public void addUser(Connection conn, User user) throws SQLException {
		String sql = getSQLFromPropertyFile("addUser");
		Object[] param = { user.getUserName(), user.getEmail(),
				user.getPassword(), user.getDisplayName() };
		runner.update(conn, sql, param);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.UserDao#getPassword(java.lang.String)
	 */
	@Override
	public String getPassword(String userName) {
		String sql = getSQLFromPropertyFile("getPassword");
		ResultSetHandler<Object> rsh = new ScalarHandler(1);
		try {
			return (String) runner.query(sql, rsh, userName);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.dao.UserDao#updateUser(org.yukung.tasklet.entity.User)
	 */
	@Override
	public void updateUser(User user) throws TaskletException {
		String sql = getSQLFromPropertyFile("updateUser");
		Object[] params = { user.getEmail(), user.getPassword(),
				user.getDisplayName(), user.getUserName() };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			throw new TaskletException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.dao.UserDao#updateUserWithoutPassword(org.yukung.tasklet
	 * .entity.User)
	 */
	@Override
	public void updateUserWithoutPassword(User user) throws TaskletException {
		String sql = getSQLFromPropertyFile("updateUserWithoutPassword");
		Object[] params = { user.getEmail(), user.getDisplayName(),
				user.getUserName() };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			throw new TaskletException(e.getMessage(), e);
		}
	}
}
