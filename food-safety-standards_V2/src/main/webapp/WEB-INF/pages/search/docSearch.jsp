<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>指标同义词-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/search.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<div class="add_m">
					<!-- Advanced Tables -->
					<ul class="nav nav-tabs">
						<li class="active"><a href="<c:url value="/standards/search/docSearch.html"></c:url>">文档检索</a></li>
						<li><a href="<c:url value="/standards/search/factorSearch.html"></c:url>">指标检索</a></li>
					</ul>
				</div>
				<div class="pan_bd">
					<div class="searchTool">
						<input placeholder="查找文档" value="${container.keyWord }" class="txtSearch" type="text" id="kw">
						<button class="btnSearch" type="button" onclick="searchDoc('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', 1)">
							<i class="fa fa-search"></i><span>搜索</span>
						</button>
					</div>
					<div class="options">
						<div class="opt_group">
							<label>类型：</label>
							<div class="opt_item">
								<span <c:if test="${container.outputType == '0' }">class="on"</c:if>>
									<a href="javascript:docSearchPage('${container.outputTagState }', 0, '${container.outputCountry }', '${container.keyWord }', 1)">不限</a>
								</span>
								<span <c:if test="${container.outputType == '1' }">class="on"</c:if>>
									<a href="javascript:docSearchPage('${container.outputTagState }', 1, '${container.outputCountry }', '${container.keyWord }', 1)">标准</a>
								</span>
								<span <c:if test="${container.outputType == '2' }">class="on"</c:if>>
									<a href="javascript:docSearchPage('${container.outputTagState }', 2, '${container.outputCountry }', '${container.keyWord }', 1)">法规</a>
								</span>
								<span <c:if test="${container.outputType == '3' }">class="on"</c:if>>
									<a href="javascript:docSearchPage('${container.outputTagState }', 3, '${container.outputCountry }', '${container.keyWord }', 1)">研究报告</a>
								</span>
							</div>
						</div>
						<div class="opt_group">
							<label>文档状态：</label>
							<div class="opt_item">
								<span <c:if test="${container.outputTagState == '0' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(0, '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', 1)">不限</a>
								</span>
								<span <c:if test="${container.outputTagState == '2' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(2, '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', 1)">未打标</a>
								</span>
								<span <c:if test="${container.outputTagState == '1' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(1, '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', 1)">打标中</a>
								</span>
								<span <c:if test="${container.outputTagState == '3' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(3, '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', 1)">一审中</a>
								</span>
								<span <c:if test="${container.outputTagState == '4' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(4, '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', 1)">二审中</a>
								</span>
								<span <c:if test="${container.outputTagState == '5' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(5, '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', 1)">入库</a>
								</span>
							</div>
						</div>
						<div class="opt_group">
							<label>国家/组织：</label>
							<div class="opt_item">
								<span <c:if test="${container.outputCountry == '0' }">class="on"</c:if>>
									<a href="javascript:docSearchPage(${container.outputTagState }, '${container.outputType }', '0', '${container.keyWord }', 1)">不限</a>
								</span>
								<c:forEach items="${countries }" var="cty">
									<span <c:if test="${container.outputCountry == cty.country }">class="on"</c:if>>
										<a href="javascript:docSearchPage(${container.outputTagState }, '${container.outputType }', '${cty.country }', '${container.keyWord }', 1)">${cty.country }</a>
									</span>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="stat">
					检索结果<b>${page.total }</b>个
				</div>
				<ul class="list">
					<c:forEach items="${page.list }" var="doc">
						<li>
							<h3 class="item_h3">
								<a href="<c:url value="/standards/annotate/annotate.html?docNo=${doc.standardNo }"></c:url>" target="_blank">${doc.docName }<i>${doc.standardNo }</i></a>
								<c:if test="${doc.tagState == 2 }">
									<a><em class="notyet"><i class="fa fa-file"></i><span>未打标</span></em></a>
								</c:if>
								<c:if test="${doc.tagState == 1 }">
									<a><em class="ongoing"><i class="fa fa-pencil"></i><span>打标中</span></em></a>
								</c:if>
								<c:if test="${doc.tagState == 3 }">
									<a><em class="review1"><i class="fa fa-eye"></i><span>一审中</span></em></a>
								</c:if>
								<c:if test="${doc.tagState == 4 }">
									<a><em class="review2"><i class="fa fa-eye"></i><span>二审中</span></em></em></a>
								</c:if>
								<c:if test="${doc.tagState == 5}">
									<a><em class="done"><i class="fa fa-check"></i><span>入库</span></em></a>
								</c:if>
							</h3>
							<h4 class="item_h4">
								<span> <label>国家：</label> ${doc.country }
								</span>&nbsp;&nbsp;&nbsp;<span> <label>发布时间：</label> <fmt:formatDate value="${doc.pubTime }" type="date" pattern="yyyy-MM-dd" />
								</span>&nbsp;&nbsp;&nbsp;<span> <label>实施时间：</label> <fmt:formatDate value="${doc.impTime }" type="date" pattern="yyyy-MM-dd" />
								</span>
							</h4>
							<div class="docinfo">
								${doc.description }
							</div>
						</li>
					</c:forEach>
				</ul>
				<c:if test="${page.pages > 1 && !lucene}">
					<div class="page_m">
							<ul class="pagination">
								<c:if test="${!page.hasPreviousPage }">
									<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left"></i></a></li>
								</c:if>
								<c:if test="${page.hasPreviousPage }">
									<li class="paginate_button"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${page.prePage })"><i
										class="fa fa-angle-left"></i></a></li>
								</c:if>
								<c:forEach items="${page.navigatepageNums }" var="n">
									<c:if test="${page.pageNum == n }">
										<li class="paginate_button active"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${n })">${n }</a></li>
									</c:if>
									<c:if test="${page.pageNum != n }">
										<li class="paginate_button"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${n })">${n }</a></li>
									</c:if>
								</c:forEach>
								
								<c:if test="${!page.hasNextPage }">
									<li class="paginate_button next previous disabled">
										<a><i class="fa fa-angle-right"></i></a></li>
								</c:if>
								<c:if test="${page.hasNextPage }">
									<li class="paginate_button next"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${page.nextPage })"><i
										class="fa fa-angle-right"></i></a></li>
								</c:if>
							</ul>
						</div>
					</c:if>
					
				<c:if test="${page.pages > 1 && lucene}">
					<div class="page_m">
							<ul class="pagination">
								<c:if test="${page.currentPage <= 1 }">
									<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left"></i></a></li>
								</c:if>
								<c:if test="${page.currentPage > 1 }">
									<li class="paginate_button"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${page.currentPage - 1 })"><i
										class="fa fa-angle-left"></i></a></li>
								</c:if>
								<c:forEach items="${page.navigatePageNumbers }" var="n">
									<c:if test="${page.currentPage == n }">
										<li class="paginate_button active"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${n })">${n }</a></li>
									</c:if>
									<c:if test="${page.currentPage != n }">
										<li class="paginate_button"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${n })">${n }</a></li>
									</c:if>
								</c:forEach>
								
								<c:if test="${page.currentPage >= page.pages }">
									<li class="paginate_button next previous disabled">
										<a><i class="fa fa-angle-right"></i></a></li>
								</c:if>
								<c:if test="${page.currentPage < page.pages }">
									<li class="paginate_button next"><a href="javascript:docSearchPage('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${page.currentPage + 1 })"><i
										class="fa fa-angle-right"></i></a></li>
								</c:if>
							</ul>
						</div>
					</c:if>
			</div>
		</div>
		<!-- /. ROW  -->
	</div>
</body>
<script type="text/javascript">
document.onkeydown = function (event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		searchDoc('${container.outputTagState }', '${container.outputType }', '${container.outputCountry }', 1);
	}
};
</script>
</html>
