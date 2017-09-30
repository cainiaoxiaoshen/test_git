<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>文档库-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/doc.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<c:if test="${roleLv != 'ANNOTATE' }">
				<div class="doc_add">
					<div class="btn_m">
						<button class="btn btn-default" data-toggle="collapse"
							data-target="#a0" aria-expanded="true">
							<i class="fa fa-plus"></i> <span>导入文档</span>
						</button>
					</div>
				</div>
				</c:if>
				<div class="collapse file_m" id="a0">
					<jsp:include page="docUpload.jsp"></jsp:include>
				</div>
				<div id="docItems"></div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$("#docManageLi").addClass('active').siblings('li').removeClass('active');
	docItems(null, 1, 2);
</script>
</html>
