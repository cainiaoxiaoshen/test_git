<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>用户打标监测-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/monitor.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<div class="detail_m">
					<span><label>用户名：</label>${mntUser }</span> <span><label>角色：</label>${antDetails.roles }
					</span> <span> <label>打标任务数：</label>${antDetails.tagTaskNum }
					</span> <span> <label>打标结果总数：</label>${antDetails.tagResultNum }
					</span> <span> <label>审核记录总数：</label>${antDetails.checkTaskNum }
					</span>
				</div>
				<div id= "monitorItems"></div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$("#monitorLiId").addClass('active').siblings('li').removeClass('active');
window.onload = function(){
	var mntUser = '${mntUser }';
	if(mntUser != ''){
		$.ajax({
			url : "monitorItems.html",
			type : "post",
			data : {
				title : 0,
				mntUser : mntUser,
				_csrf : $("#csrf").val()
			},
			success : function(data) {
				$("#monitorItems").html(data);
			}
		});
	}
} 
</script>

</html>