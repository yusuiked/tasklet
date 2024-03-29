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
package org.yukung.tasklet.service;

import org.yukung.tasklet.dto.UserDto;
import org.yukung.tasklet.entity.User;
import org.yukung.tasklet.exception.TaskletException;

/**
 * <p>
 * ユーザアカウント関連のビジネスロジックを規定するインタフェースです。
 * </p>
 * 
 * @author yukung
 * 
 */
public interface AccountService {

	/**
	 * <p>
	 * ユーザ情報をusersテーブルに登録します。
	 * </p>
	 * 
	 * @param user
	 * @throws TaskletException
	 *             DB登録エラー
	 */
	public void register(User user) throws TaskletException;

	/**
	 * <p>
	 * ユーザIDとパスワードをキーにログインします。
	 * </p>
	 * 
	 * @param userName
	 * @param password
	 * @return ログインユーザーのUserオブジェクト
	 */
	public User login(String userName, String password);

	/**
	 * <p>
	 * ユーザIDから設定変更情報を取得します。
	 * </p>
	 * 
	 * @param userName
	 * @return 設定画面DTO
	 */
	public UserDto find(String userName);

	/**
	 * <p>
	 * 入力されたパスワードが妥当かどうかをチェックします。
	 * </p>
	 * 
	 * @param userName
	 *            ユーザ名
	 * @param encrypt
	 *            暗号化されたパスワード
	 * @return チェック結果
	 */
	public boolean isPasswordValid(String userName, String encrypt);

	/**
	 * <p>
	 * ユーザ情報を更新します。
	 * </p>
	 * 
	 * @param user
	 *            ユーザEntity
	 * @throws TaskletException
	 *             DB更新エラー
	 */
	public void update(User user) throws TaskletException;
}
