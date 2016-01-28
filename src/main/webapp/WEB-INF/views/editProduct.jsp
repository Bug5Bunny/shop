<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit product</title>
</head>
<body>
	<form:form method="POST" action="${pageContext.request.contextPath}/editproduct" modelAttribute="product">
		<form:hidden path="uid" />
		<form:label path="name">Name</form:label>
		<form:input path="name" />
		<br>
		<form:label path="description">Description</form:label>
		<form:input path="description" />
		<br>
		<form:label path="price">Price</form:label>
		<form:input path="price" />
		<br>
		<form:label path="photo">Photo</form:label>
		<form:input path="photo" />
		<br>
		<input type="submit" value="Submit" />
	</form:form>
</body>
</html>