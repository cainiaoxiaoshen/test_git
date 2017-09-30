<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>选择</th>
			<th>用户名</th>
			<th>角色</th>
			<th>任务数</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${accounts }" var="act">
			<tr>
				<td><input type="checkbox" name="accountSelectCbx" value="${act.name }" onclick="onlyOneAccountSelect(this)"/></td>
				<td>${act.name }</td>
				<td>${act.roles}</td>
				<td>${act.annotateDocNum + act.checkDocNum }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>