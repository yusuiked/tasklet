<%@ page contentType="text/html; charset=UTF-8" %>
<html:xhtml/>

			<div id="main">

				<h2>新規タスクの追加</h2>
				<html:form action="/addTask">
					<html:errors/>
					<p>
						<label>タスク名</label>
						<html:text property="title" />
						<label>優先度</label>
						<html:select property="priority">
							<html:optionsCollection property="priorities" value="value" label="priorityName"/>
						</html:select>
						<label>期限</label>
						<html:text property="period" styleId="period" readonly="true" />
						<label>見積時間</label>
						<html:text property="estimatedTime" />
						<html:hidden property="activityId" />
					</p>
					<p>
						<html:submit value="追加" styleClass="button" />
						<html:cancel value="キャンセル" styleClass="button" />
					</p>
				</html:form>

			</div>