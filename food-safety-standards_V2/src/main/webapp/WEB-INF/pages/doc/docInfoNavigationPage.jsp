<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../commonSearch.jsp"></jsp:include>
<ol class="breadcrumb path_m">
	<li><a href="docManage.html">文档管理</a></li>
	<li class="active">编号：${doc.standardNo }</li>
</ol>
<div class="add_m">
	<!-- Advanced Tables -->
	<ul class="nav nav-tabs">
		<li id="docAnnotateLiId"><a
			href="<c:url value="/standards/annotate/annotate.html?docId=${doc.id }"></c:url>">打标结果</a></li>
		<c:if test="${roleLv != 'ANNOTATE' }">
			<li id="docEditLiId"><a
				href="<c:url value="/standards/doc/edit.html?docId=${doc.id }"></c:url>">文档信息</a></li>
		</c:if>
	</ul>
	<div class="operate_m">
		<c:if test="${doc.tagState == 2 }">
			<a
				href="javascript:antStartMarkStateUpdate(${doc.id }, '${doc.reqUrl }')"
				class="b_m"> <i class="fa fa-file"></i><span>去打标</span>
			</a>
		</c:if>
		<c:if
			test="${doc.tagState == 1 && (doc.annotateUser == userName || roleLv == 'ADMIN')}">
			<a href="${doc.reqUrl }" class="b_m" target="_blank"> <i
				class="fa fa-file"></i><span>去打标</span>
			</a>
			<a href="javascript:antStateUpdate(${doc.id }, 3)" class="b_m"> <i
				class="fa fa-file"></i><span>打标完成</span>
			</a>
		</c:if>
		<c:if
			test="${doc.tagState == 3 && (doc.firstCheckUser == userName || roleLv == 'ADMIN')}">
			<a href="${doc.reqUrl }" class="b_m" target="_blank"> <i
				class="fa fa-file"></i><span>去打标</span>
			</a>
			<a href="javascript:antStateUpdate(${doc.id }, 4)" class="b_m"> <i
				class="fa fa-file"></i><span>一审完成</span>
			</a>
		</c:if>
		<c:if
			test="${doc.tagState == 4 && (doc.secondCheckUser == userName || roleLv == 'ADMIN')}">
			<a href="${doc.reqUrl }" class="b_m" target="_blank"> <i
				class="fa fa-file"></i><span>去打标</span>
			</a>
			<a href="javascript:antStateUpdate(${doc.id }, 5)" class="b_m"> <i
				class="fa fa-file"></i><span>入库</span>
			</a>
		</c:if>
		<c:if test="${doc.tagState == 5 && roleLv == 'ADMIN' }">
			<a href="${doc.reqUrl }" class="b_m" target="_blank"> <i
				class="fa fa-file"></i><span>去打标</span>
			</a>
		</c:if>
	</div>
</div>