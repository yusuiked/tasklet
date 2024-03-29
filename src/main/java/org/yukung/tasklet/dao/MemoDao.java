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
package org.yukung.tasklet.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.yukung.tasklet.entity.Memo;
import org.yukung.tasklet.exception.TaskletException;

/**
 * <p>
 * メモ情報DAOインタフェースです。
 * </p>
 * 
 * @author yukung
 * 
 */
public interface MemoDao {

	/**
	 * <p>
	 * タスクIDをキーにメモの一覧を取得します。
	 * </p>
	 * 
	 * @param taskId
	 * @return メモ一覧を格納したList
	 */
	public List<Memo> getMemosByTaskId(int taskId);

	/**
	 * <p>
	 * タスクIDをキーにメモテーブルのソート順の最大値を取得します。
	 * </p>
	 * 
	 * @param taskId
	 * @return ソート順の最大値
	 */
	public Integer getMaxSeqOfMemos(int taskId);

	/**
	 * <p>
	 * メモを追加します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param memo
	 *            メモ情報Entity
	 * @throws SQLException
	 *             DB更新エラー
	 */
	public void addMemoToMemos(Connection conn, Memo memo) throws SQLException;

	/**
	 * <p>
	 * メモを追加します。
	 * </p>
	 * 
	 * @param memo
	 *            メモ情報Entity
	 * @throws TaskletException
	 *             DB更新エラー
	 */
	public void addMemoToMemos(Memo memo) throws TaskletException;

	/**
	 * <p>
	 * 選択されたタスクに紐づくメモを削除します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param checked
	 *            選択されたタスクIDの配列
	 * @throws DB更新エラー
	 */
	public void removeMemos(Connection conn, String[] checked)
			throws SQLException;

	/**
	 * <p>
	 * アクティビティIDから紐づいたメモIDを取得します。
	 * </p>
	 * <p>
	 * 
	 * @param activityId
	 *            アクティビティID
	 * @return メモIDの配列を要素としたList
	 */
	public List<Object[]> findMemoIdByActivityId(int activityId);

	/**
	 * <p>
	 * アクティビティに紐づいたメモを削除します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param memoIds
	 *            削除対象のメモID
	 * @throws SQLException
	 *             DB更新エラー
	 */
	public void deleteMemosFromActivity(Connection conn, String[] memoIds)
			throws SQLException;

}
