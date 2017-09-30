<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<div class="page-wrapper">
			<div class="page-inner">

				<div class="add_m">
					<!-- Advanced Tables -->
					<ul class="nav nav-tabs">
						<c:if test="${roleLv != 'ANNOTATE' }">
							<li><a href="<c:url value="/standards/search/docSearch.html"></c:url>">文档检索</a></li>
						</c:if>
						<li class="active"><a href="<c:url value="/standards/search/factorSearch.html"></c:url>">指标检索</a></li>
					</ul>
				</div>

				<div class="pan_bd">
					<div class="searchTool">
						<input placeholder="查找指标" value="${container.keyWord }" class="txtSearch" type="text" id="kw">
						<button class="btnSearch" type="button" onclick="factorSearch2(${container.outputType }, '${container.outputCountry }')">
							<i class="fa fa-search"></i><span>搜索</span>
						</button>
					</div>
					<div class="options">
						<div class="opt_group">
							<label>类型：</label>
							<div class="opt_item">
								<span <c:if test="${container.outputType == '0' }">class="on"</c:if>>
									<a href="javascript:factorSearch(0, '${container.outputCountry }', '${container.keyWord }', 1)">不限</a>
								</span>
								<span <c:if test="${container.outputType == '1' }">class="on"</c:if>>
									<a href="javascript:factorSearch(1, '${container.outputCountry }', '${container.keyWord }', 1)">标准</a>
								</span>
								<span <c:if test="${container.outputType == '2' }">class="on"</c:if>>
									<a href="javascript:factorSearch(2, '${container.outputCountry }', '${container.keyWord }', 1)">法规</a>
								</span>
								<span <c:if test="${container.outputType == '3' }">class="on"</c:if>>
									<a href="javascript:factorSearch(3, '${container.outputCountry }', '${container.keyWord }', 1)">研究报告</a>
								</span>
							</div>
						</div>

						<div class="opt_group">
							<label>国家/组织：</label>
							<div class="opt_item">
								<span <c:if test="${container.outputCountry == '0' }">class="on"</c:if>>
									<a href="javascript:factorSearch(${container.outputType }, '0', '${container.keyWord }', 1)">不限</a>
								</span>
								<c:forEach items="${countries }" var="cty">
									<span <c:if test="${container.outputCountry == cty.country }">class="on"</c:if>>
										<a href="javascript:factorSearch(${container.outputType }, '${cty.country }', '${container.keyWord }', 1)">${cty.country }</a>
									</span>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="stat">
					检索结果<b>${page.total }</b>个
				</div>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover"
						style="min-width: 3650px;">
						<thead>
							<tr>
								<th style="width: 170px;">国家</th>
								<th style="width: 200px;">编号</th>
								<th style="width: 170px;">产品</th>
								<th style="width: 170px;">物质</th>
								<th style="width: 70px;">最大限值</th>
								<th style="width: 70px;">最小限值</th>
								<th style="width: 90px;">单位</th>
								<th style="width: 130px;">ADI</th>
								<th style="width: 200px;">AdiWeb</th>
								<th style="width: 130px;">CNS</th>
								<th style="width: 130px;">INS</th>
								<th style="width: 130px;">CAS</th>
								<th style="width: 170px;">结构方程式</th>
								<th style="width: 170px;">摩尔方程式</th>
								<th style="width: 170px;">属性备注</th>
								<th style="width: 170px;">毒理学指标</th>
								<th style="width: 170px;">生物学指标</th>
								<th style="width: 130px;">功能</th>
								<th style="width: 170px;">病理学指标</th>
								<th style="width: 200px;">产品备注</th>
								<th style="width: 200px;">物质备注</th>
								<th style="width: 170px;">产品标准</th>
								<th style="width: 170px;">检测标准</th>
								<th style="width: 160px;">创建时间</th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list }" var="factor">
								<tr>
									<td>${factor.country }</td>
									<td>${factor.docId }</td>
									<td>${factor.product }</td>
									<td>${factor.factor }</td>
									<td>${factor.max }</td>
									<td>${factor.min }</td>
									<td>${factor.unit }</td>
									<td>${factor.adi }</td>
									<td>${factor.adiWeb }</td>
									<td>${factor.cns }</td>
									<td>${factor.ins }</td>
									<td>${factor.cas }</td>

									<td>${factor.struc }</td>
									<td>${factor.mole }</td>
									<td>${factor.property }</td>
									<td>${factor.toxico }</td>
									<td>${factor.biological }</td>
									<td>${factor.funct }</td>
									<td>${factor.disease }</td>
									<td>${factor.proremark }</td>
									<td>${factor.facremark }</td>
									<td>${factor.prostd }</td>
									<td>${factor.testsId }</td>
									<td><fmt:formatDate value="${factor.createDate }"
											type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:if test="${page.pages > 1 }">
					<div class="page_m">
							<ul class="pagination">
								<c:if test="${!page.hasPreviousPage }">
									<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left"></i></a></li>
								</c:if>
								<c:if test="${page.hasPreviousPage }">
									<li class="paginate_button"><a href="javascript:factorSearch('${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${page.prePage })"><i
										class="fa fa-angle-left"></i></a></li>
								</c:if>
								<c:forEach items="${page.navigatepageNums }" var="n">
									<c:if test="${page.pageNum == n }">
										<li class="paginate_button active"><a href="javascript:factorSearch('${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${n })">${n }</a></li>
									</c:if>
									<c:if test="${page.pageNum != n }">
										<li class="paginate_button"><a href="javascript:factorSearch('${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${n })">${n }</a></li>
									</c:if>
								</c:forEach>
								
								<c:if test="${!page.hasNextPage }">
									<li class="paginate_button next previous disabled">
										<a><i class="fa fa-angle-right"></i></a></li>
								</c:if>
								<c:if test="${page.hasNextPage }">
									<li class="paginate_button next"><a href="javascript:factorSearch('${container.outputType }', '${container.outputCountry }', '${container.keyWord }', ${page.nextPage })"><i
										class="fa fa-angle-right"></i></a></li>
								</c:if>
							</ul>
						</div>
						</c:if>

			</div>
		</div>
		<!-- /. ROW  -->
	</div>
	<div class="modal fade in" aria-hidden="true"
		aria-labelledby="myModalLabel" id="myModal" role="dialog"
		tabindex="-1">
		<div class="modal-backdrop fade in"></div>
		<div class="modal-dialog" style="margin-top:100px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" aria-hide="true"
						data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">系统提示</h4>
				</div>
				<div class="modal-body">您确定要删除本条记录吗？</div>
				<div class="modal-footer">
					<button class="btn btn-default" aria-hidden="true"
						data-dismiss="modal">取消</button>
					<button class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
document.onkeydown = function (event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		factorSearch2('${container.outputType }', '${container.outputCountry }');
	}
};
</script>
</html>
