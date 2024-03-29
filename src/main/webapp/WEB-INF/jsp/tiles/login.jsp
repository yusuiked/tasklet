<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html:xhtml/>
			<div id="sidebar">

				<h3>ログイン</h3>
				<html:form action="/login" styleClass="loginform">
					<ul class="sidemenu">
						<li>ユーザーID</li>
						<li><html:text property="userName" maxlength="30" styleClass="textbox" /></li>
						<li>パスワード</li>
						<li><html:password property="password" maxlength="30" styleClass="textbox" redisplay="false" /></li>
					</ul>
					<html:submit value="ログイン" styleClass="button" />
				<html:errors />
				</html:form>

				<h3>新規登録</h3>
				<p>新規登録は<html:link action="/account">こちら</html:link>から</p>

				<h3>Links</h3>
				<ul class="sidemenu">
					<li><html:link href="http://d.hatena.ne.jp/yukung/">開発者ブログ</html:link></li>
					<li><html:link href="http://twitter.com/yukung/">Twitter</html:link></li>
				</ul>

			</div>