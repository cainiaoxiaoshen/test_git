<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>文档历史记录-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/common.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<c:if test="${roleLv != 'ANNOTATE' }">
					<div class="search_top form-inline">
						<div class="form-control">
							<input class="srch-txt" placeholder="查找文档" type="text" id="kw">
							<span class="srch-btn"><a
								href="javascript:searchDoc('', '', '', 0)"><i
									class="fa fa-search"></i></a></span>
						</div>
					</div>
				</c:if>
				<!-- /. search  -->
				<div class="add_m">
					<!-- Advanced Tables -->
					<ul class="nav nav-tabs">
						<li><a href="docManage.html">文档管理</a></li>
						<li class="active"><a href="docReplace.html">文档代替关系</a></li>
						<li><a href="docSynonym.html">指标同义词</a></li>
					</ul>
				</div>
				<div class="caption_m">
					<div class="title">
						<b>${docNo} / ${oldDocNo }</b>的历史记录
					</div>
					<div class="page_top">
						<ul class="pager">
							<li class="paginate_button prev_m"><a href="docRepHistory.html?docNo=${docNo }&oldDocNo=${oldDocNo }&p=${page.currentPage - 1 }"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="docRepHistory.html?docNo=${docNo }&oldDocNo=${oldDocNo }&p=${page.currentPage + 1 }"><i class="fa fa-angle-right"></i></a></li>

						</ul>
						<span class="tableinfo_m">共<em>${page.total }</em>条记录，第<b>${page.currentPage }</b>页
						</span>
					</div>
				</div>

				<!-- /. ROW  -->
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 200px;">新文档编号</th>
								<th style="width: 200px;">旧文档编号</th>
								<th>创建时间</th>
								<th>代替关系</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.items }" var="doc" varStatus="status">
								<tr>
									<td>${doc.newDoc }</td>
									<td>${doc.oldDoc }</td>
									<td><fmt:formatDate value="${doc.createTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><c:if test="${doc.relation == 0}">部分代替</c:if>
										<c:if test="${doc.relation == 1}">完全代替</c:if></td>
									<td>${doc.remark }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:if test="${page.pages > 1 }">
					<div class="page_m">
						<ul class="pagination">
							<c:if test="${page.currentPage <= 1 }">
								<li class="paginate_button previous disabled"><a><i
										class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
							</c:if>
							<c:if test="${page.currentPage > 1 }">
								<li class="paginate_button"><a
									href="docRepHistory.html?docNo=${docNo }&oldDocNo=${oldDocNo }&p=${page.currentPage - 1 }"><i
										class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
							</c:if>
							<c:forEach items="${page.navigatePageNumbers }" var="n">
								<c:if test="${page.currentPage == n }">
									<li class="paginate_button active"><a
										href="docRepHistory.html?docNo=${docNo }&oldDocNo=${oldDocNo }&p=${n }">${n }</a></li>
								</c:if>
								<c:if test="${page.currentPage != n }">
									<li class="paginate_button"><a
										href="docRepHistory.html?docNo=${docNo }&oldDocNo=${oldDocNo }&p=${n }">${n }</a></li>
								</c:if>
							</c:forEach>

							<c:if test="${page.currentPage >= page.pages }">
								<li class="paginate_button next previous disabled"><a><i
										class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
							</c:if>
							<c:if test="${page.currentPage < page.pages }">
								<li class="paginate_button next"><a
									href="docRepHistory.html?docNo=${docNo }&oldDocNo=${oldDocNo }&p=${page.currentPage + 1 }"><i
										class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
							</c:if>
						</ul>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>