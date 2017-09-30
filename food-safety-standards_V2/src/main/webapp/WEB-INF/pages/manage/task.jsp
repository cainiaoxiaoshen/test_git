<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>任务分配-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/account.js"></c:url>"></script>
<script src="<c:url value="/js/task.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="navigationPage.jsp"></jsp:include>
				<div class="task">
					<div class="row">
						<div class="col-lg-4">
							<h3>1.打标员</h3>
							<div class="input-group select">
								<input class="form-control" id="selectAntUser"> <span
									class="input-group-btn">
									<button class="btn btn-default" data-toggle="modal"
										data-target="#myModal" onclick="accountItems(0, 'selectAntUser')">
										<i class="fa fa-user"></i><span>选择</span>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-4">
							<h3>2.一级审核员</h3>

							<div class="input-group select">
								<input class="form-control" id="selectFcheckUser"> <span
									class="input-group-btn">
									<button class="btn btn-default" data-toggle="modal"
										data-target="#myModal" onclick="accountItems(1, 'selectFcheckUser')">
										<i class="fa fa-user"></i><span>选择</span>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-4">
							<h3>3.二级审核员</h3>
							<div class="input-group select">
								<input class="form-control" id="selectScheckUser"> <span
									class="input-group-btn">
									<button class="btn btn-default" data-toggle="modal"
										data-target="#myModal" onclick="accountItems(1, 'selectScheckUser')">
										<i class="fa fa-user"></i><span>选择</span>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-12">
							<div class="cap_user">
								<h3>4.文档任务 (已选:<span id="comfirmedDoc">0</span>)</h3>
								<button class="btn btn-default f_m pull-right" onclick="docSelect()"
									data-toggle="modal" data-target="#myModal2"><i
									class="fa fa-file-text"></i><span>选择</span></button>
							</div>
							<div class="table-responsive tab_result">
								<table class="table table-striped table-bordered table-hover">
									<tbody id="seletedDocsTb">
										
									</tbody>
								</table>
							</div>
							<div class="text-center submit" style="margin-top:20px"><button class="btn btn-default bb_m" onclick="saveTasks()"><i class="fa fa-check"></i><span>提交</span></button></div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
					<h4 class="modal-title" id="myModalLabel">选择用户</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive tab_mod">
						<div id="accountItems"></div>
					</div>
				</div>
				<div class="modal-footer">
					<input type="hidden" id="actionInputId">
					<button class="btn btn-default" aria-hidden="true"
						data-dismiss="modal">取消</button>
					<button class="btn btn-primary" onclick="selectedAccount(this)" aria-hidden="true"
						data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /. modal1  -->

	<div class="modal fade in" aria-hidden="true"
		aria-labelledby="myModalLabel" id="myModal2" role="dialog"
		tabindex="-1">
		<div class="modal-backdrop fade in"></div>
		<div class="modal-dialog" style="margin-top:100px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" aria-hide="true"
						data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel2">选择文档</h4>
				</div>
				<div class="modal-body">
					
					<div id="docSelect"></div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" aria-hidden="true"
						data-dismiss="modal">取消</button>
					<button class="btn btn-primary" aria-hidden="true"
						data-dismiss="modal" onclick="comfirmedDocsSelected()">确定</button>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
$("#taskLiId").addClass('active').siblings('li').removeClass('active');
</script>
</html>