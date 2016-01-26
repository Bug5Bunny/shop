<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chocolate shop</title>
<link href="<c:url value="resources/css/style.css" />" rel="stylesheet">
</head>
<body>
	<div id="login">
		<h1>
			<a href="${pageContext.request.contextPath}">${title}</a>
		</h1>
		<sec:authorize access="hasRole('ROLE_USER')">
			<!-- For login user -->
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			<script>
            function formSubmit() {
                document.getElementById("logoutForm").submit();
            }
        </script>

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h2 class='form'>
					Username : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()"> Logout</a>
				</h2>
			</c:if>
			<c:if test="${pageContext.request.userPrincipal.name == null}">
				<h2 class='form'>
					<a href="${pageContext.request.contextPath}/register"> Sign Up</a>
					<a href="${pageContext.request.contextPath}/login"> Sign In</a>
				</h2>
			</c:if>


		</sec:authorize>
	</div>
</body>
</html>