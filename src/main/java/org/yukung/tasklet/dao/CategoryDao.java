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
import java.util.Map;

import org.yukung.tasklet.entity.Category;
import org.yukung.tasklet.exception.TaskletException;

/**
 * <p>
 * カテゴリ情報DAOインタフェースです。
 * </p>
 * 
 * @author yukung
 * 
 */
public interface CategoryDao {

	/**
	 * <p>
	 * カテゴリ「未分類」をcategoriesテーブルに登録します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param userId
	 *            DBから払い出されたユーザID
	 * @throws SQLException
	 *             DB更新エラー
	 */
	public void addDefaultCategory(Connection conn, int userId)
			throws SQLException;

	/**
	 * <p>
	 * 引数のアクティビティIDに紐づくカテゴリを取得します。
	 * </p>
	 * 
	 * @param activityId
	 * @return カテゴリ情報
	 */
	public Category findCategoryByActivityId(int activityId);

	/**
	 * <p>
	 * デフォルトのカテゴリ情報を取得します。
	 * </p>
	 * 
	 * @param userId
	 * @return カテゴリ情報
	 */
	public Category getDefaultCategory(int userId);

	/**
	 * <p>
	 * カテゴリ情報をcategoriesテーブルに追加します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param category
	 *            カテゴリ情報Entity
	 * @throws TaskletException
	 *             DB更新エラー
	 */
	public void add(Connection conn, Category category) throws TaskletException;

	/**
	 * <p>
	 * 引数のユーザIDに紐づくカテゴリを取得します。
	 * </p>
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザIDに紐づいたカテゴリ一覧をMapに持つList<br>
	 *         <p>
	 *         key:カテゴリID<br>
	 *         value:カテゴリ名
	 *         </p>
	 */
	public List<Map<String, Object>> findCategoriesByUserId(int userId);

	/**
	 * <p>
	 * ユーザIDをキーにカテゴリのソート順の最大値を取得します。
	 * 
	 * @param userId
	 *            ユーザID
	 */
	public Integer getMaxSeqOfCategories(int userId);

	/**
	 * <p>
	 * カテゴリを更新します。
	 * </p>
	 * 
	 * @param category
	 *            カテゴリ情報Entity
	 * @throws TaskletException
	 *             DB更新エラー
	 */
	public void updateCategoryName(Category category) throws TaskletException;

	/**
	 * <p>
	 * 引数のユーザIDに紐づくカテゴリを取得します（カテゴリ「未分類」を含め）。
	 * </p>
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザIDに紐づいたカテゴリ一覧をMapに持つList<br>
	 *         <p>
	 *         key:カテゴリID<br>
	 *         value:カテゴリ名
	 *         </p>
	 * 
	 */
	public List<Map<String, Object>> findCategoriesWithUncategorized(int userId);

	/**
	 * <p>
	 * indexesテーブルのカテゴリを「未分類」に戻します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param category
	 *            カテゴリ情報Entity
	 * @throws SQLException
	 *             DB更新エラー
	 */
	public void revertIndexes(Connection conn, Category category)
			throws SQLException;

	/**
	 * <p>
	 * categoriesテーブルからカテゴリを削除します。
	 * </p>
	 * 
	 * @param conn
	 *            DB接続
	 * @param category
	 *            カテゴリ情報Entity
	 * @throws SQLException
	 *             DB更新エラー
	 */
	public void deleteCategory(Connection conn, Category category)
			throws SQLException;
}
