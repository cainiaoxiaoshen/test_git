<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${roleLv != 'ANNOTATE' }">
	<div class="search_top form-inline">
		<div class="form-control">
			<input class="srch-txt" placeholder="查找文档" type="text" id="commonSearchKeyWord">
			<span class="srch-btn">
				<a href="javascript:commonSearch()">
					<i class="fa fa-search"></i>
				</a>
			</span>
		</div>
	</div>
</c:if>
