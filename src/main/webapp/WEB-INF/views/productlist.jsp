<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of products</title>
</head>
<body>
	List of products:
	<br>
	<c:forEach items="${list}" var="products">
		Name: ${products.name}<br>
		Description: ${products.description}<br>
		Price: ${products.price}<br>
		Photo: ${products.photo}<br>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a href="${pageContext.request.contextPath}/deleteproduct?id=${products.uid}">Delete</a>
			<a href="${pageContext.request.contextPath}/editproduct?id=${products.uid}">Edit</a>
			<br>
		</sec:authorize>
		<br>
	</c:forEach>
</body>
</html>