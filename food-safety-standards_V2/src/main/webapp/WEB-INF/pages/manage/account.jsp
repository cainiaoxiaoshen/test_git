<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>文档库-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/account.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<div class="caption_m">
					<div class="tool_m">
						<div class="btn_m">
							<button class="btn btn-default" onclick="createAddAccountView()">
								<i class="fa fa-plus"></i> <span>新建</span>
							</button>
						</div>
					</div>
					<div class="page_top">
						<ul class="pager">
							<li class="paginate_button prev_m"><a href="account.html?p=${page.prePage }"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="account.html?p=${page.nextPage }"><i class="fa fa-angle-right"></i></a></li>
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
								<th>编号</th>
								<th>用户名</th>
								<th>密码</th>
								<th>注册时间</th>
								<th>角色</th>
								<th>打标任务数</th>
								<th>审核任务数</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tbodyId">
							<c:forEach items="${page.list }" var="user" varStatus="num">
							<tr id="accountTr${user.id }">
								<c:if test="${(num.index + 1) + (page.prePage) * page.pageSize > 19 }">
									<td>${(num.index + 1) + (page.prePage) * page.pageSize }</td>
								</c:if>
								<c:if test="${(num.index + 1) + (page.prePage) * page.pageSize <= 19 }">
									<td>${(num.index + 1) + (page.prePage) * page.pageSize }</td>
								</c:if>
								<td>${user.name }</td>
								<td>${user.password }</td>
								<td><fmt:formatDate value="${user.createDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${user.roles}</td>
								<td>${user.annotateDocNum }</td>
								<td>${user.checkDocNum }</td>
								<td><a href="task.html" class="t_m"><i
										class="fa fa-tasks"></i><span>分配</span></a> <a
									href="accountMonitor.html?mntUser=${user.name }" class="t_m"><i
										class="fa fa-cog"></i><span>管理</span></a> <a
									href="javascript:modifyAccount(${user.id })" class="t_m"><i class="fa fa-pencil"></i><span>修改</span></a>
								</td>
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
					<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left" style="line-height:1.4"></i></a></li>
				</c:if>
				<c:if test="${page.hasPreviousPage }">
					<li class="paginate_button"><a href="account.html?p=${page.prePage }"><i
						class="fa fa-angle-left" style="line-height:1.4"></i></a></li>
				</c:if>
				<c:forEach items="${page.navigatepageNums }" var="n">
					<c:if test="${page.pageNum == n }">
						<li class="paginate_button active"><a href="account.html?p=${n }">${n }</a></li>
					</c:if>
					<c:if test="${page.pageNum != n }">
						<li class="paginate_button"><a href="account.html?p=${n }">${n }</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${!page.hasNextPage }">
					<li class="paginate_button next previous disabled">
						<a><i class="fa fa-angle-right" style="line-height:1.4"></i></a></li>
				</c:if>
				<c:if test="${page.hasNextPage }">
					<li class="paginate_button next"><a href="account.html?p=${page.nextPage }"><i
						class="fa fa-angle-right" style="line-height:1.4"></i></a></li>
				</c:if>
			</ul>
			
		</div>
</c:if>
			</div>
		</div>
	</div>
	<div class="modal fade" aria-hidden="true"
		aria-labelledby="myModalLabel" id="accountModal" role="dialog"
		tabindex="-1">
		<div class="modal-backdrop fade in"></div>
		<div class="modal-dialog modal-right" style="margin-top:120px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="accountCloseBtn">&times;</button>
					<h4 class="modal-title" id="accountModalTitle">新建用户</h4>
				</div>
				<div class="modal-body">
					<form role="form" class="form-horizontal">
						<div class="form-group">
							<label for="firstname" class="col-sm-3 control-label">用户名</label>
							<div class="col-sm-9">
								<input id="create_uname" class="form-control" type="text">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">密码</label>
							<div class="col-sm-9">
								<input id="create_password" class="form-control" type="text">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">角色</label>
							<div class="col-sm-9">
								<label class="checkbox-inline"><input type="checkbox" name="role" value="ROLE_ADMIN">管理员</label>
								<label class="checkbox-inline"><input type="checkbox" name="role" value="ROLE_CHECK">审核员</label>
								<label class="checkbox-inline"><input type="checkbox" name="role" value="ROLE_ANNOTATE">打标员</label>
							</div>

						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" id="accountCancelBtn">取消</button>
					<button class="btn btn-primary" id="accountSaveBtn">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$("#accountLiId").addClass('active').siblings('li').removeClass('active');
</script>
</html>
