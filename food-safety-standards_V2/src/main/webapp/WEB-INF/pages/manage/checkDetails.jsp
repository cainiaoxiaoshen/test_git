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

</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<div class="detail_m">
					<div>
						<span> <label>国家：</label> ${doc.country }
						</span> <span> <label>类型：</label> <c:if test="${doc.type == 1}">标准</c:if>
									<c:if test="${doc.type == 2}">法规</c:if>
									<c:if test="${doc.type == 3}">研究报告</c:if>
						</span><span> <label>打标结果数：</label> <c:if test="${monitor == null }">0</c:if><c:if test="${monitor != null }">${monitor.antNum }</c:if>
						</span><span class="length"> <label>文档名称：</label> ${doc.docName }
						</span>
					</div>
					<div>
						<span> <label>审核记录数：</label> <c:if test="${monitor == null }">0</c:if><c:if test="${monitor != null }">${monitor.antChkNum }</c:if>
						</span> <span> <label>打标员：</label> ${doc.annotateUser }
						</span><span> <label>一级审核员：</label> ${doc.firstCheckUser }
						</span><span> <label>二级审核员：</label> ${doc.secondCheckUser }
						</span>
					</div>
				</div>
				<div class="caption_m">
					<div class="tool_m">
						<div class="hd_m">
							<b>${monitor.standardNo }</b>审核修改记录
						</div>
					</div>
					<div class="page_top">
						<ul class="pager">
							<li class="paginate_button prev_m"><a href="checkDetails.html?docId=${monitor.id }&p=${page.prePage }"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="checkDetails.html?docId=${monitor.id }&p=${page.nextPage }"><i class="fa fa-angle-right"></i></a></li>
						</ul>
						<span class="tableinfo_m">共<em>${page.total }</em>条记录，第<b>${page.pageNum }</b>页
						</span>
					</div>
				</div>
				<!-- /. ROW  -->
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>审核员</th>
								<th>修改记录</th>
								<th style="width: 160px;">修改时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list }" var="chk">
							<tr>
								<td>${chk.createUser }</td>
								<td>${chk.description }</td>
								<td><fmt:formatDate value="${chk.createTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:if test="${page.pages > 1 }">
					<div class="page_m">
						
							<ul class="pagination">
								<c:if test="${!page.hasPreviousPage }">
									<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
								</c:if>
								<c:if test="${page.hasPreviousPage }">
									<li class="paginate_button"><a href="checkDetails.html?docId=${monitor.id }&p=${page.prePage }"><i
										class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
								</c:if>
								<c:forEach items="${page.navigatepageNums }" var="n">
									<c:if test="${page.pageNum == n }">
										<li class="paginate_button active"><a href="checkDetails.html?docId=${monitor.id }&p=${n }">${n }</a></li>
									</c:if>
									<c:if test="${page.pageNum != n }">
										<li class="paginate_button"><a href="checkDetails.html?docId=${monitor.id }&p=${n }">${n }</a></li>
									</c:if>
								</c:forEach>
								
								<c:if test="${!page.hasNextPage }">
									<li class="paginate_button next previous disabled">
										<a><i class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
								</c:if>
								<c:if test="${page.hasNextPage }">
									<li class="paginate_button next"><a href="checkDetails.html?docId=${monitor.id }&p=${page.nextPage }"><i
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
