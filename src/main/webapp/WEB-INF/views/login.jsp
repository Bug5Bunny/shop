<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chocolate shop - Login</title>
<link href="<c:url value="resources/css/login.css" />" rel="stylesheet">
<script src="resources/js/jquery-2.2.0.min.js"></script>
<script src="resources/js/jquery.validate.min.js"></script>
<script src="resources/js/login.js"></script>
</head>
<body onload='document.loginForm.username.focus();'>
	<div id="login">
		<c:if test="${not empty error}">
			<div class="error-login">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<h1>
			<a href="${pageContext.request.contextPath}">${title}</a>
		</h1>
		<form name='loginForm' id='loginForm' action="<c:url value='/j_spring_security_check' />" method="post" class="loginform">
			<input type='text' name='username' placeholder='username' /> <input type='password' name='password' placeholder="password" /> <input
				name='submit' type='submit' value='Log in' /> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="register">
				Not registered yet? <a href="${pageContext.request.contextPath}/register">Register here</a>
			</div>
		</form>
	</div>
</body>
</html>