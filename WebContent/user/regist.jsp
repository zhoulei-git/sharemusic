<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<script type="text/javascript">
function change(){
	var img=document.getElementById("img");
	img.src="/sharemusic/VerifyCodeServlet?a="+new Date().getTime();
}
</script>
</head>
<body>
<h1>用户注册</h1>
<form action="<c:url value='/UserServlet'/>" method="POST">
	<input type="hidden" name="method" value="regist"/>
	<font color="red">${msg}</font><br/>
	用户名：<input type="text" name="username" value="${form.username}"/>${errors.username}<br/>
	密码：<input type="password" name="password" value="${form.password}"/>${errors.password}<br/>
	年龄：<input type="text" name="age" value="${form.age}"/>${errors.age}<br/>
	性别：<select name="gender">
		<option value="male" ${form.gender=="male"?"selected='selected'":""}>男</option>
		<option value="female" ${form.gender=="female"?"selected='selected'":""}>女</option>
	</select><br/>
	生日：<input type="text" name="birthday" value="${form.birthday}"/><br/>
	手机号：<input type="text" name="phone" value="${form.phone}"/>${errors.phone}<br/>
	邮箱：<input type="text" name="email" value="${form.email}"/>${errors.email}<br/>
	验证码：<input type="text" name="verifyCode" value="${form.verifyCode}"/>
	<image id="img" src="<c:url value='/VerifyCodeServlet'/>">
	<a href="javascript:change()">看不清，换一张</a>${errors.verifyCode}<br/>
	<input type="submit" value="注册"/><input type="reset" value="重置"/>
</form>
</body>
</html>