<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>error</title>
<jsp:include page="../resource.jsp"></jsp:include>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		
		
		<center>
			<br><br>
			<a href="javascript:testJson()">接收json参数</a>
			<br><br>
			<a href="testJson.html">返回json数组</a>
			<br><br>
			<a href="testResponseEntity.html">testResponseEntity</a> 
			<br><br>
			<a href="testExceptionHandler.html?i=0">testExceptionHandler</a>
		</center>
	</div>
</body>
<script type="text/javascript">
//-------------------------

	function testJson() {
		var url = "http://localhost:8080/food-safety-standards_V2/standards/test/testJson2.html?_csrf="
				+ $("#csrf").val();
		var myUser = {};
		myUser.name = 'rtrtr';
		myUser.password = "123";

		$.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',

			data : JSON.stringify(myUser),
			success : function(response) {
				console.log(response);
			}
		});
	}
</script>
</html>