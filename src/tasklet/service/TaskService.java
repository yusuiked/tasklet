/**
 * Created 2009/09/03
 * Copyright (C) 2009  Yusuke Ikeda (ikeda.yusuke@gmail.com)
 *
 * This file is part of Tasklet.
 */
package tasklet.service;

import java.util.List;

import tasklet.entity.Task;

/**
 * タスク関連のビジネスロジックを規定するインタフェースです。
 *
 * @author Y.Ikeda
 *
 */
public interface TaskService {

	/**
	 * アクティビティIDに紐づいたタスク一覧を取得します。
	 *
	 * @param activityId
	 * @return タスク一覧を格納したList
	 */
	public List<Task> show(int activityId);

	/**
	 * アクティビティIDに対応したアクティビティ名を取得します。
	 *
	 * @param activityId
	 * @return アクティビティのタイトル
	 */
	public String getActivityTitle(int activityId);
}