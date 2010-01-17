/**
 * Created 2009/09/03
 * Copyright (C) 2009  Yusuke Ikeda (ikeda.yusuke@gmail.com)
 *
 * This file is part of Tasklet.
 */
package tasklet.service;


import tasklet.TaskletException;
import tasklet.dao.UserDao;
import tasklet.entity.User;
import tasklet.factory.DaoFactory;
import tasklet.util.PasswordUtil;

/**
 * ユーザーアカウント関連のビジネスロジックを実行するServiceの実装クラスです。
 *
 * @author Y.Ikeda
 *
 */
public class AccountServiceImpl implements AccountService {

	private UserDao userDao = DaoFactory.getInstance().createUserDao();

	/*
	 * (非 Javadoc)
	 *
	 * @see tasklet.service.AccountService#login(java.lang.String,
	 * java.lang.String)
	 */
	public User login(String userName, String password) {
		String encryptedPassword = PasswordUtil.encrypt(password);
		User user = userDao.findUserByUserNameAndPassword(userName, encryptedPassword);
		if (user == null) {
			user = null;
		}
		return user;
	}

	/*
	 * (非 Javadoc)
	 * @see tasklet.service.AccountService#register(tasklet.entity.User)
	 */
	public void register(User user) throws TaskletException {

		// ユーザID重複チェック
		if (userDao.isRegistered(user.getUserName())) {
			throw new TaskletException("errors.already");
		}

		user.setPassword(PasswordUtil.encrypt(user.getPassword()));
		userDao.registerUser(user);
		int userId = userDao.findUserByUserName(user.getUserName()).getId();
		userDao.registerDefaultCategory(userId);
	}
}
