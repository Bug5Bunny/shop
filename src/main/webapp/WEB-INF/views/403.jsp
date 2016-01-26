<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>403 Error- Access is denied</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href='http://fonts.googleapis.com/css?family=Love+Ya+Like+A+Sister' rel='stylesheet' type='text/css'>
<link href='resources/css/error.css' rel='stylesheet' type='text/css'>
</head>
<body>
	<script async type='text/javascript' src='//cdn.fancybar.net/ac/fancybar.js?zoneid=1502&serve=C6ADVKE&placement=w3layouts' id='_fancybar_js'></script>
	<div class="wrap">
		<div class="logo">
			<c:choose>
				<c:when test="${empty username}">
					<p>OOPS! - Access denied</p>
				</c:when>
				<c:otherwise>
					<p>OOPS! - Hey, ${username}! Access denied</p>
				</c:otherwise>
			</c:choose>
			<div class="errortext">403</div>
			<div class="sub">
				<p>
					<a href="javascript:window.history.back()">Back </a>
				</p>
			</div>
		</div>
	</div>
	<div class="footer">Chocolate shop</div>
</body>
</html>