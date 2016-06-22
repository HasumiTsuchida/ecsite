<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="adminCommon.jsp" %>
<body>
 <div align="center">
 
 <br><br>
<h1>新規利用者登録画面</h1><br>
<br><br>
管理者の情報を入力し、「管理者情報を登録する」ボタンをクリックしてください。 <br>
<br><br>
	<form:form action="/admin/register/adminRegister" modelAttribute="adminUserRegisterForm">
		
	
		<c:out value="${err}"/><br>
		<table border="">
		<tr>
		<td>
		<form:errors path="name" cssStyle="color:red" element="div"/>

		
名前</td><td><form:input path="name"/></td>
		</tr>
		<tr>
		<td>
		<form:errors path="email" cssStyle="color:red" element="div" />
アドレス</td><td><form:input path="email"/></td></tr>
<tr>
		
		<td><form:errors path="password" cssStyle="color:red" element="div"/>
パスワード</td><td><form:password path="password"/></td>
		<tr><td>
		<form:errors path="confirmationPassword" cssStyle="color:red" element="div"/>
確認用パスワード</td><td><form:password path="confirmationPassword"/></td></tr>
</table>
		<input type="submit" value="登録">
	</form:form>
	<form action="/admin/register/"> <input type="submit" value="クリア"></form>
</div>
</body>
</html>