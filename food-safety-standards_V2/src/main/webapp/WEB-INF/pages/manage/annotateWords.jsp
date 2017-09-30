<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>任务分配-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/keyWords.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<ul class="nav nav-pills">
					<li><a href="<c:url value="/standards/words/keyWordsManage.html"></c:url>"><i
							class="fa fa-filter"></i>筛选标签词</a></li>
					<li class="active"><a href="annotateWords.html"><i
							class="fa fa-file-text"></i>筛选结果</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade in active">
						<div class="caption_m">
							<div class="tool_m">
								<p class="text-muted">共有${page.total }个筛选词</p>
							</div>
							<div class="page_top">
							<c:if test="${!synchronous }">
								<a href="javascript:synchronousWords()" class="btn btn-primary"><span>启动识别</span></a>
							</c:if>
							<c:if test="${synchronous }">
								正在同步识别...
							</c:if>
							</div>
						</div>
						<!-- /. ROW  -->
						<div class="table-responsive">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th style="width: 80px;">序号</th>
										<th style="width:;">标签词</th>
										<th style="width: 150px;">删除</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.list }" var="wd" varStatus="num">
									<tr>
										<td>${(num.index + 1) + (page.nextPage) * 20 }</td>
										<td>${wd.annotateWord }</td>
										<td><a href="javascript:deleteWord(${wd.id}, '${wd.annotateWord }', ${page.pageNum })" class="t_m"><i class="fa fa-trash"></i><span>删除</span></a></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!--End Advanced Tables -->
						<c:if test="${page.pages > 1 }">
						<div class="page_m">
								<ul class="pagination">
									<c:if test="${!page.hasPreviousPage }">
										<li class="paginate_button previous disabled"><a><i
												class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
									</c:if>
									<c:if test="${page.hasPreviousPage }">
										<li class="paginate_button"><a
											href="annotateWords.html?p=${page.prePage }"><i
												class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
									</c:if>
									<c:forEach items="${page.navigatepageNums }" var="n">
										<c:if test="${page.pageNum == n }">
											<li class="paginate_button active"><a
												href="annotateWords.html?p=${n }">${n }</a></li>
										</c:if>
										<c:if test="${page.pageNum != n }">
											<li class="paginate_button"><a
												href="annotateWords.html?p=${n }">${n }</a></li>
										</c:if>
									</c:forEach>
						
									<c:if test="${!page.hasNextPage }">
										<li class="paginate_button next previous disabled"><a><i
												class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
									</c:if>
									<c:if test="${page.hasNextPage }">
										<li class="paginate_button next"><a
											href="annotateWords.html?p=${page.nextPage }"><i
												class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
									</c:if>
								</ul>
						</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$("#keyWordLiId").addClass('active').siblings('li').removeClass('active');
</script>
</html>