<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
	<c:when test="${session_user eq null}">
		<a href="user/login.jsp" target="_blank">登录</a>
		<a href="user/regist.jsp" target="_blank">注册</a>
	</c:when>
	<c:otherwise>
		欢迎，${session_user.username}  
		<a href="upload.jsp" target="body">上传音乐</a>
	</c:otherwise>
</c:choose>
</body>
</html>