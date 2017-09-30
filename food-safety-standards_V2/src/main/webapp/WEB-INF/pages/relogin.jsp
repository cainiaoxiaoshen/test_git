<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>

<body>
	<center>
		<div style="margin-top: 80px">
			<p>该用户已经在别处登录，您已经被强制退出。</p><br>
			<p><a href="<c:url value="/standards/login.html"/>">重新登录</a></p>
		</div>
	</center>

</body>
</html>