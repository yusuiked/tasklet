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
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.yukung.tasklet.dao.AbstractDao;
import org.yukung.tasklet.dao.TaskDao;
import org.yukung.tasklet.entity.Task;
import org.yukung.tasklet.exception.DataAccessException;
import org.yukung.tasklet.exception.TaskletException;
import org.yukung.tasklet.utils.StringUtil;

/**
 * <p>
 * タスク情報DAOインタフェースの実装クラスです。
 * </p>
 * 
 * @author yukung
 * 
 */
public class TaskDaoImpl extends AbstractDao implements TaskDao {

	/**
	 * <p>
	 * デフォルトコンストラクタ。
	 * </p>
	 */
	public TaskDaoImpl() {
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
	public TaskDaoImpl(DataSource ds) {
		super(ds);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#findTasksByActivityId(int)
	 */
	@Override
	public List<Task> findTasksByActivityId(int activityId) {
		String sql = getSQLFromPropertyFile("findTasksByActivityId");
		ResultSetHandler<List<Task>> rsh = new BeanListHandler<Task>(Task.class);
		try {
			return runner.query(sql, rsh, Integer.valueOf(activityId));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.dao.TaskDao#addTask(org.yukung.tasklet.entity.Task)
	 */
	@Override
	public void addTask(Task task) throws TaskletException {
		String sql = getSQLFromPropertyFile("addTask");
		Object[] param = { Integer.valueOf(task.getActivityId()),
				task.getTitle(), Integer.valueOf(task.getPriority().getCode()),
				task.getPeriod(), Double.valueOf(task.getEstimatedTime()) };
		try {
			runner.update(sql, param);
		} catch (SQLException e) {
			throw new TaskletException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#getTaskDetail(int)
	 */
	@Override
	public Task getTask(int taskId) {
		String sql = getSQLFromPropertyFile("getTask");
		ResultSetHandler<Task> rsh = new BeanHandler<Task>(Task.class);
		try {
			return runner.query(sql, rsh, Integer.valueOf(taskId));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#getActivityIdByTaskId(int)
	 */
	@Override
	public Integer getActivityIdByTaskId(int taskId) {
		String sql = getSQLFromPropertyFile("getActivityIdByTaskId");
		ResultSetHandler<Object> rsh = new ScalarHandler(1);
		try {
			return (Integer) runner.query(sql, rsh, Integer.valueOf(taskId));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#getActualTimeByTaskId(int)
	 */
	@Override
	public Double getActualTimeByTaskId(int taskId) {
		String sql = getSQLFromPropertyFile("getActualTimeByTaskId");
		ResultSetHandler<Object> rsh = new ScalarHandler(1);
		try {
			return (Double) runner.query(sql, rsh, Integer.valueOf(taskId));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#updateTask(java.sql.Connection,
	 * org.yukung.tasklet.entity.Task)
	 */
	@Override
	public void updateTask(Connection conn, Task task) throws SQLException {
		String sql = getSQLFromPropertyFile("updateTask");
		Object[] params = { Double.valueOf(task.getActualTime()),
				Integer.valueOf(task.getId()) };
		runner.update(conn, sql, params);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.dao.TaskDao#modifyTask(org.yukung.tasklet.entity.Task)
	 */
	@Override
	public void modifyTask(Task task) throws TaskletException {
		String sql = getSQLFromPropertyFile("modifyTask");
		Object[] params = { task.getTitle(),
				Integer.valueOf(task.getPriorityCode()), task.getPeriod(),
				Double.valueOf(task.getEstimatedTime()),
				Integer.valueOf(task.getId()) };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			throw new TaskletException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#completeTasks(java.sql.Connection,
	 * java.lang.String[])
	 */
	@Override
	public void completeTasks(Connection conn, String[] checked)
			throws SQLException {
		String sql = StringUtil.createBindVariables(checked,
				getSQLFromPropertyFile("completeTasks"));
		Object[] params = checked;
		runner.update(conn, sql, params);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#getIncompleteCount(int)
	 */
	@Override
	public Integer getIncompleteCount(int activityId) {
		String sql = getSQLFromPropertyFile("getIncompleteCount");
		ResultSetHandler<Object> rsh = new ScalarHandler(1);
		try {
			return (Integer) runner
					.query(sql, rsh, Integer.valueOf(activityId));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#removeTasks(java.sql.Connection,
	 * java.lang.String[])
	 */
	@Override
	public void removeTasks(Connection conn, String[] checked)
			throws SQLException {
		String sql = StringUtil.createBindVariables(checked,
				getSQLFromPropertyFile("removeTasks"));
		Object[] params = checked;
		runner.update(conn, sql, params);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#findTaskIdByActivityId(int)
	 */
	@Override
	public List<Object[]> findTaskIdByActivityId(int activityId) {
		String sql = getSQLFromPropertyFile("findTaskIdByActivityId");
		ResultSetHandler<List<Object[]>> rsh = new ArrayListHandler();
		try {
			return runner.query(sql, rsh, Integer.valueOf(activityId));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.dao.TaskDao#deleteTasksFromActivity(java.sql.Connection
	 * , java.lang.Object[])
	 */
	@Override
	public void deleteTasksFromActivity(Connection conn, String[] taskIds)
			throws SQLException {
		String sql = StringUtil.createBindVariables((String[]) taskIds,
				getSQLFromPropertyFile("deleteTasksFromActivity"));
		Object[] params = taskIds;
		runner.update(conn, sql, params);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.dao.TaskDao#completeTask(java.sql.Connection,
	 * org.yukung.tasklet.entity.Task)
	 */
	@Override
	public void completeTask(Connection conn, Task task) throws SQLException {
		String sql = getSQLFromPropertyFile("completeTask");
		Object[] params = { Integer.valueOf(task.getStatusCode()),
				Double.valueOf(task.getActualTime()),
				Integer.valueOf(task.getId()) };
		runner.update(conn, sql, params);
	}
}
