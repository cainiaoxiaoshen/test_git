<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="filter form-inline">
	<select class="form-control input-sm" onchange="filterDoc('${docNo }')" id="selectedCountry">
		<option value="0" <c:if test="${country == '0' }">selected="selected"</c:if>>全部</option>
		<c:forEach items="${countries }" var="cty">
			<option value="${cty.country }" <c:if test="${cty.country == country }">selected="selected"</c:if>>${cty.country }</option>
		</c:forEach>
	</select>
	<div class="form-group">
		<input class="form-control" placeholder="搜索文档" type="text" value="${docNo }" id="searchedDocNo">
		<a href="javascript:filterDocByDocNo('${country }')"><span
			class="btn btn-default search-s"><i
				class="fa fa-search"></i></span></a>
	</div>
</div>
<div class="cap_mod">
	<div class="tool_m">
		<span class="checkbox-inline"> <label><input
				type="checkbox" id="docSelectAll" onclick="choiceAll()">全选</label>
		</span>
	</div>
	<div class="page_top">已选：<span id="selectedNum">0</span></div>
</div>
<div class="table-responsive tab_mod">
	<table class="table table-striped table-bordered table-hover">
		<tbody>
			<c:forEach items="${page.list }" var="doc">
			<tr>
				<td><input type="checkbox" name="docSelectCbx" onclick="selectedNum()" value="${doc.country },${doc.id },${doc.docName },${doc.standardNo }"/></td>
				<td>${doc.standardNo }</td>
				<td>${doc.docName }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="page_m">
	<c:if test="${page.pages > 1 }">
		<ul class="pagination">
			<c:if test="${!page.hasPreviousPage }">
				<li class="paginate_button previous disabled"><a><i
						class="fa fa-angle-left"></i></a></li>
			</c:if>
			<c:if test="${page.hasPreviousPage }">
				<li class="paginate_button"><a
					href="javascript:pageDocSelected('${country }','${docNo }',${page.prePage })"><i
						class="fa fa-angle-left"></i></a></li>
			</c:if>
			<c:forEach items="${page.navigatepageNums }" var="n">
				<c:if test="${page.pageNum == n }">
					<li class="paginate_button active"><a
						href="javascript:pageDocSelected('${country }','${docNo }',${n })">${n }</a></li>
				</c:if>
				<c:if test="${page.pageNum != n }">
					<li class="paginate_button"><a
						href="javascript:pageDocSelected('${country }','${docNo }',${n })">${n }</a></li>
				</c:if>
			</c:forEach>

			<c:if test="${!page.hasNextPage }">
				<li class="paginate_button next previous disabled"><a><i
						class="fa fa-angle-right"></i></a></li>
			</c:if>
			<c:if test="${page.hasNextPage }">
				<li class="paginate_button next"><a
					href="javascript:pageDocSelected('${country }','${docNo }',${page.nextPage })"><i
						class="fa fa-angle-right"></i></a></li>
			</c:if>
		</ul>
	</c:if>
</div>

<script type="text/javascript">
document.onkeydown = function (event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		filterDocByDocNo('${country }');
	}
};
</script>
