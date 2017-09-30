<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>error</title>
<jsp:include page="resource.jsp"></jsp:include>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<p class="text-danger error">
					<i class="fa fa-exclamation-triangle"></i>${message }，<a href="javascript:window.history.back();">返回</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>