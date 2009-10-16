/**
 * Created 2009/09/03
 * Copyright (C) 2009  Yusuke Ikeda (ikeda.yusuke@gmail.com)
 *
 * This file is part of Tasklet.
 */
package tasklet.dao;

import java.sql.SQLException;

import tasklet.entity.User;

/**
 * ユーザ情報DAOインターフェースです。
 * 
 * @author Y.Ikeda
 */
public interface UserDao {

	/**
	 * ユーザIDをキーにユーザ情報エンティティを取得します。
	 * 
	 * @param ユーザID
	 * @return ユーザ情報エンティティ
	 */
	public User findByUserName(String userName);

	/**
	 * ユーザIDとパスワードをキーにユーザ情報エンティティを取得します。
	 * 
	 * @param ユーザID
	 * @param パスワード
	 * @return ユーザー情報オブジェクト
	 */
	public User findByUserNameAndPassword(String userName, String password);

	/**
	 * ユーザ情報エンティティをDBに登録します。（INSERT）
	 * 
	 * @param ユーザ情報エンティティ
	 * @return 更新件数
	 * @throws SQLException
	 *             TODO
	 */
	public int registUser(User user) throws SQLException;

}
