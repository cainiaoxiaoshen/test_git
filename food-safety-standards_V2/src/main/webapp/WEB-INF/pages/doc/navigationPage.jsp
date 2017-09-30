<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../commonSearch.jsp"></jsp:include>
<div class="add_m">
	<ul class="nav nav-tabs">
		<li id="docManageLi"><a href="<c:url value="/standards/doc/docManage.html"></c:url>">文档管理</a></li>
		<c:if test="${roleLv != 'ANNOTATE' }">
			<li id="docReplaceLi"><a href="docReplace.html">文档代替关系</a></li>
			<li id="docSynonymLi"><a href="docSynonym.html">指标同义词</a></li>
		</c:if>
	</ul>
</div>