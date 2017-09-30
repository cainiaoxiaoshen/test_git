<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>词库管理-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/keyWords.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<ul class="nav nav-pills">
					<li class="active"><a href="keyWordsManage.html"><i
							class="fa fa-filter"></i>筛选标签词</a></li>
					<li><a href="annotateWords.html"><i
							class="fa fa-file-text"></i>筛选结果</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade in active">
						<div class="caption_m">
							<div class="tool_m">
								<p class="text-muted">共有${page.total }个标签词</p>
							</div>
							<div class="page_top">
								<div class="filter form-inline">
									<div class="input-group input-group-sm">
										<input class="form-control" placeholder="搜索标签词" id="searchedWords" value="${word }"> <span
											class="input-group-btn">
											<button class="btn btn-default" onclick="searchWords()">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</div>
							</div>
						</div>
						<!-- /. ROW  -->
						<div class="table-responsive table-word">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th style="width: 80px;">序号</th>
										<th style="width: 500px;">词语</th>
										<th style="width: 200px;">频数</th>
										<th style="width: 300px"><span class="pull-left"><label
												class="th-label"> <input type="checkbox" id="selectAllWords" onclick="selectAll('selectAllWords', 'selectedWordsChk')"/> 全部筛选
											</label></span><span class="pull-right"><a href="javascript:selectedWords(${page.pageNum })"
												class="btn btn-primary">确定</a></span></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${page.list }" var="wd" varStatus="num">
									<tr>
										<td>${(num.index + 1) + (page.nextPage) * 20 }</td>
										<td>${wd.word }</td>
										<td>${wd.frequency }</td>
										<td><input type="checkbox" name="selectedWordsChk" value="${wd.word }"/></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!--End Advanced Tables -->

						<c:if test="${page.pages > 1 }">
						<div class="page_m">
								<ul class="pagination">
									<c:if test="${!page.hasPreviousPage }">
										<li class="paginate_button previous disabled"><a><i
												class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
									</c:if>
									<c:if test="${page.hasPreviousPage }">
										<li class="paginate_button"><a
											href="keyWordsManage.html?p=${page.prePage }&word=${word}"><i
												class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
									</c:if>
									<c:forEach items="${page.navigatepageNums }" var="n">
										<c:if test="${page.pageNum == n }">
											<li class="paginate_button active"><a
												href="keyWordsManage.html?p=${n }&word=${word}">${n }</a></li>
										</c:if>
										<c:if test="${page.pageNum != n }">
											<li class="paginate_button"><a
												href="keyWordsManage.html?p=${n }&word=${word}">${n }</a></li>
										</c:if>
									</c:forEach>
						
									<c:if test="${!page.hasNextPage }">
										<li class="paginate_button next previous disabled"><a><i
												class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
									</c:if>
									<c:if test="${page.hasNextPage }">
										<li class="paginate_button next"><a
											href="keyWordsManage.html?p=${page.nextPage }&word=${word}"><i
												class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
									</c:if>
								</ul>
						</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$("#keyWordLiId").addClass('active').siblings('li').removeClass('active');
	$("tbody tr").click(function(e) {
		var check = $(this).find("input[type='checkbox']");
		if(check){
			var flag = check[0].checked;
			if(flag){
				$(this).attr("class", "");
				check[0].checked = false;
			} else {
				$(this).attr("class", "success");
				check[0].checked = true;
			}
		}
	});
	$("tbody tr td input[type='checkbox']").click(function(e){
		e.stopPropagation();
	});
</script>
</html>