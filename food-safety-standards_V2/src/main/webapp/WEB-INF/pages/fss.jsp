<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>标准项目</title>
<jsp:include page="resource.jsp"></jsp:include>
<script src="<c:url value="/js/doc.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
	<jsp:include page="menu.jsp"></jsp:include>
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="commonSearch.jsp"></jsp:include>
				<div class="nav_index">
					<span>打标进度</span>
					<hr />
				</div>
				<div class="row">
					<div class="col-md-2 col-sm-6 col-xs-6">
						<div class="panel panel-back noti-box">
							<span class="icon-box circle1 set-icon"> <i
								class="fa fa-files-o"></i>
							</span> <a href="<c:url value="/standards/doc/docManage.html"></c:url>" class="text-box">
								<p class="main-text">${progress.total }</p>
								<p class="text-muted">文档总数</p>
							</a>
						</div>
					</div>
					<div class="col-md-2 col-sm-6 col-xs-6">
						<div class="panel panel-back noti-box">
							<span class="icon-box circle2 set-icon"> <i
								class="fa fa-file-o"></i>
							</span>
							
							<a <c:if test="${roleLv != 'ANNOTATE' }">
									href="<c:url value="/standards/search/docSearch.html?tagState=2"></c:url>" 
								</c:if>
							class="text-box">
								<p class="main-text">${progress.tagStartCount }</p>
								<p class="text-muted">未打标</p>
							</a>
						</div>
					</div>
					<div class="col-md-2 col-sm-6 col-xs-6">
						<div class="panel panel-back noti-box">
							<span class="icon-box circle3 set-icon"> <i
								class="fa fa-pencil"></i>
							</span>
							<a <c:if test="${roleLv != 'ANNOTATE' }">
									href="<c:url value="/standards/search/docSearch.html?tagState=1"></c:url>" 
								</c:if>
								class="text-box">
								<p class="main-text">${progress.tagActionCount }</p>
								<p class="text-muted">打标中</p>
							</a>
						</div>
					</div>
					<div class="col-md-2 col-sm-6 col-xs-6">
						<div class="panel panel-back noti-box">
							<span class="icon-box circle4 set-icon"> <i
								class="fa fa-eye"></i>
							</span>
							<a <c:if test="${roleLv != 'ANNOTATE' }">
									href="<c:url value="/standards/search/docSearch.html?tagState=3"></c:url>" 
								</c:if>
								class="text-box">
								<p class="main-text">${progress.firstCheckCount }</p>
								<p class="text-muted">一审中</p>
							</a>						
						</div>
					</div>
					<div class="col-md-2 col-sm-6 col-xs-6">
						<div class="panel panel-back noti-box">
							<span class="icon-box circle5 set-icon"> <i
								class="fa fa-eye"></i>
							</span>
							<a <c:if test="${roleLv != 'ANNOTATE' }">
								href="<c:url value="/standards/search/docSearch.html?tagState=4"></c:url>" 
							</c:if>
							class="text-box">
								<p class="main-text">${progress.secondCheckCount }</p>
								<p class="text-muted">二审中</p>
							</a>
						</div>
					</div>
					<div class="col-md-2 col-sm-6 col-xs-6">
						<div class="panel panel-back noti-box">
							<span class="icon-box circle6 set-icon"> <i
								class="fa fa-check"></i>
							</span>
							<a <c:if test="${roleLv != 'ANNOTATE' }">
								href="<c:url value="/standards/search/docSearch.html?tagState=5"></c:url>" 
							</c:if>
							class="text-box">
								<p class="main-text">${progress.finishCount }</p>
								<p class="text-muted">入库</p>
							</a>
						</div>
					</div>
				</div>
				<div class="nav_index">
					<span>我的任务</span>
					<hr />
				</div>
				<div id="docItems"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	docItems(null, 1, 1);
</script>
</html>