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
package org.yukung.tasklet.service.impl;

import org.yukung.tasklet.dao.CategoryDao;
import org.yukung.tasklet.dao.UserDao;
import org.yukung.tasklet.dto.UserDto;
import org.yukung.tasklet.dto.converter.DtoConverter;
import org.yukung.tasklet.entity.User;
import org.yukung.tasklet.exception.TaskletException;
import org.yukung.tasklet.factory.ConverterFactory;
import org.yukung.tasklet.factory.DaoFactory;
import org.yukung.tasklet.logic.AccountTxLogic;
import org.yukung.tasklet.service.AccountService;
import org.yukung.tasklet.utils.PasswordUtil;

/**
 * <p>
 * ユーザアカウント関連のビジネスロジックを実行するServiceの実装クラスです。
 * </p>
 * 
 * @author yukung
 * 
 */
public class AccountServiceImpl implements AccountService {

	/** ユーザ情報DAO */
	private UserDao userDao = DaoFactory.getInstance().createUserDao();

	/** カテゴリ情報DAO */
	private CategoryDao categoryDao = DaoFactory.getInstance()
			.createCategoryDao();

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.service.AccountService#register(org.yukung.tasklet
	 * .entity.User)
	 */
	@Override
	public void register(User user) throws TaskletException {
		// ユーザID重複チェック
		checkForDuplicate(user.getUserName());
		// パスワードの暗号化
		user.setPassword(PasswordUtil.encrypt(user.getPassword()));
		// ユーザ登録
		AccountTxLogic tx = new AccountTxLogic(userDao, categoryDao);
		tx.register(user);
	}

	/**
	 * <p>
	 * ユーザ名が重複していないかチェックします。
	 * </p>
	 * 
	 * @param userName
	 * @throws TaskletException
	 *             ユーザ名重複エラー
	 */
	private void checkForDuplicate(String userName) throws TaskletException {
		int count = userDao.getUserCount(userName).intValue();
		if (count > 0) {
			throw new TaskletException("errors.already");
		}

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.service.AccountService#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User login(String userName, String password) {
		String encryptPass = PasswordUtil.encrypt(password);
		return userDao.findUserByUserNameAndPassword(userName, encryptPass);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.yukung.tasklet.service.AccountService#find(java.lang.String)
	 */
	@Override
	public UserDto find(String userName) {
		User user = userDao.findUserByUserName(userName);

		if (user == null) {
			return null;
		}
		DtoConverter<User, UserDto> converter = ConverterFactory
				.createDtoConverter(UserDto.class);
		return converter.convert(user);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.service.AccountService#isPasswordValid(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public boolean isPasswordValid(String userName, String entry) {
		if ((userName == null) || (entry == null)) {
			return false;
		}
		String origin = userDao.getPassword(userName);
		if (origin.equals(entry)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.service.AccountService#update(org.yukung.tasklet.entity
	 * .User)
	 */
	@Override
	public void update(User user) throws TaskletException {
		String password = user.getPassword();
		if ((password == null) || (password.equals(""))) {
			userDao.updateUserWithoutPassword(user);
		} else {
			// パスワードの暗号化
			user.setPassword(PasswordUtil.encrypt(user.getPassword()));
			// ユーザ登録
			userDao.updateUser(user);
		}
	}

}
