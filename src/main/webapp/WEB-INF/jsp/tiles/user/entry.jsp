<%@ page contentType="text/html; charset=UTF-8" %>
<html:xhtml/>

			<div id="main">

				<h2>新規タスクの追加</h2>
				<html:form action="/addTask">
					<html:errors/>
					<p>
						<label>タスク名</label>
						<html:text property="title" size="50" tabindex="1"/>
						<label>優先度</label>
						<html:select property="priority" tabindex="2">
							<html:optionsCollection property="priorities" value="value" label="priorityName"/>
						</html:select>
						<label>期限</label>
						<html:text property="period" size="10" styleId="period" tabindex="3" readonly="true" />
						<label>見積時間</label>
						<html:text property="estimatedTime" size="5" tabindex="4" />
						<html:hidden property="activityId" /><%-- 受渡アクティビティID --%>
					</p>
					<p>
						<html:submit value="追加" styleClass="button" tabindex="5" />
						<html:cancel value="キャンセル" styleClass="button" tabindex="6" />
					</p>
				</html:form>

			</div>