<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of users</title>
</head>
<body>
	List of users:
	</br>
	<c:forEach items="${list}" var="users">
		<a href='lock?username=${users.username}&notlocked=false'>Lock ${users.username}</a><br>
		<a href='delete?username=${users.username}'>Delete ${users.username}</a>
		</br>
	</c:forEach>
	<c:if test="${not empty lockedlist}">
	List of locked users:
	</br>
	<c:forEach items="${lockedlist}" var="locked">
		<a href='lock?username=${locked.username}&notlocked=true'>Unlock ${locked.username}</a><br>
		<a href='delete?username=${locked.username}'>Delete ${locked.username}</a>
		</br>
	</c:forEach>
	</c:if>
</body>
</html>