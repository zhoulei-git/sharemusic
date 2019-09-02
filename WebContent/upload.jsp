<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>uploadmusic</title>
</head>
<body>
<form action="<c:url value='/UploadMusicServlet'/>" method="POST" enctype="multipart/form-data">
	音乐名：<input type="text" name="musicname" value="${musicname}"/>${errors.musicname == null?"音乐名不要超过50":errors.musicname}<br/>
	作者：<input type="text" name="producername" value="${producername}"/>${errors.producername == null?"作者名不要超过50":errors.producername}<br/>
	音乐简介：<input type="text" name="description" value="${description}"/>${errors.description == null?"简介不要超过50":errors.description}<br/>
	<input type="hidden" name="upemail" value="${session_user.email}"/>
	音乐类型：<select name="musictype">
				<option value="pop" ${musictype eq "pop"?"selected='selected'":""}>流行</option>
				<option value="rock" ${musictype eq "rock"?"selected='selected'":""}>摇滚</option>
				<option value="edm" ${musictype eq "edm"?"selected='selected'":""}>电子</option>
				<option value="rap" ${musictype eq "rap"?"selected='selected'":""}>说唱</option>
				<option value="soft" ${musictype eq "soft"?"selected='selected'":""}>轻音乐</option>
				<option value="jazz" ${musictype eq "jazz"?"selected='selected'":""}>爵士</option>
			</select><br/>
	音乐：<input type="file" name="music" />${errors.mimetype == null?"注意：目前只支持mp3格式的音乐上传":errors.mimetype}<br/>
	<input type="submit" value="上传" />
</form>
</body>
</html>