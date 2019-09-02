<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<script type="text/javascript">
function change(){
	var img=document.getElementById("img");
	img.src="/sharemusic/VerifyCodeServlet?a="+new Date().getTime();
}	
</script>
</head>
<body>
<form action="<c:url value='/UserServlet'/>" method="POST">
	<h1>登录</h1>
	<font color="red">${msg}</font><br/>
	邮箱：<input type="text" name="email" value="${form.email}"/>${errors.email}<br/>
	<input type="hidden" name="method" value="login"/>
	密码：<input type="password" name="password" value="${form.password}"/>${errors.password}<br/>
	验证码：<input type="text" name="verifyCode" value="${form.verifyCode}"/>
	<image src="<c:url value='/VerifyCodeServlet'/>" id="img"/>
	<a href="javascript:change()">看不清楚，换一张</a>${errors.verifyCode}<br/>
	<input type="submit" value="登录"/>
</form>
</body>
</html>