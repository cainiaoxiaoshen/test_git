<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../commonSearch.jsp"></jsp:include>

<div class="add_m">
	<ul class="nav nav-tabs">
		<li id="accountLiId"><a href="<c:url value="/standards/manage/account.html"></c:url>">用户</a></li>
		<li id="monitorLiId"><a href="<c:url value="/standards/manage/monitor.html"></c:url>">打标监测</a></li>
		<li id="taskLiId"><a href="<c:url value="/standards/manage/task.html"></c:url>">任务分配</a></li>
		<li id="keyWordLiId"><a href="<c:url value="/standards/words/keyWordsManage.html"></c:url>">词库管理</a></li>
	</ul>
</div>