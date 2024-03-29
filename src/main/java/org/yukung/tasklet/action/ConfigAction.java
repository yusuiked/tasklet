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
package org.yukung.tasklet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.yukung.tasklet.dto.UserDto;
import org.yukung.tasklet.entity.User;
import org.yukung.tasklet.exception.DataAccessException;
import org.yukung.tasklet.form.ConfigForm;
import org.yukung.tasklet.service.AccountService;
import org.yukung.tasklet.service.impl.AccountServiceImpl;

/**
 * <p>
 * 設定変更画面に遷移するアクションです。
 * </p>
 * 
 * @author yukung
 * 
 */
public class ConfigAction extends AbstractAction {

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * org.yukung.tasklet.action.AbstractAction#doExecute(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		User user = (User) request.getSession().getAttribute("user");

		AccountService accountService = new AccountServiceImpl();
		UserDto userDto = accountService.find(user.getUserName());
		if (userDto == null) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("errors.mismatch");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}

		// ActionFormにDBのデータをマッピングする
		ConfigForm configForm = (ConfigForm) form;
		try {
			BeanUtils.copyProperties(configForm, userDto);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage(), e);
		}

		saveToken(request);
		return mapping.findForward(SUCCESS);
	}

}
