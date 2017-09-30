<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>文档库-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<link href="<c:url value="/assets/css/bootstrap.css"></c:url>" rel="stylesheet" />
<link href="<c:url value="/assets/css/bootstrap-datetimepicker.min.css"></c:url>" rel="stylesheet" />
<script src="<c:url value="/js/doc.js"></c:url>"></script>
<script src="<c:url value="/js/docReplace.js"></c:url>"></script>

<script type="text/javascript"
	src="<c:url value="/assets/js/bootstrap-datetimepicker.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="docInfoNavigationPage.jsp"></jsp:include>
				<div class="info_m">
					<span> <label>编号：</label> ${doc.standardNo }
					</span> <span> <label>格式：</label> ${doc.format }
					</span> <span> <label>类型：</label> <c:if test="${doc.type == 1}">标准</c:if>
									<c:if test="${doc.type == 2}">法规</c:if>
									<c:if test="${doc.type == 3}">研究报告</c:if>
					</span><span> <label>创建者：</label> ${doc.createUser }
					</span><span> <label>创建时间：</label> <fmt:formatDate value="${doc.createTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
					</span>
					<span> <label>打标员：</label> ${doc.annotateUser }</span>
					<span> <label>一审员：</label> ${doc.firstCheckUser }</span>
					<span> <label>二审员：</label> ${doc.secondCheckUser }</span>
				</div>
				<div class="row">
					<div class="col-md-5">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">基本信息</h3>
							</div>
							<form role="form" class="form-horizontal" action="saveEdit.html" method="post">
							<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-3 control-label">文档名称</label>
										<div class="col-sm-9">
											 <textarea id="docName" name="docName" class="form-control" rows="2" >${doc.docName }</textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">产品体系</label>
										<div class="col-sm-9">
											<input class="form-control" type="text" id="proSystem" name = "proSystem" value="${doc.proSystem }">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">正文语种</label>
										<div class="col-sm-9">
											<input class="form-control" type="text" id="language" name="language" value="${doc.language }">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label">发布时间</label>
										<div class="col-sm-9">
											<div data-link-format="yyyy-mm-dd"
												data-link-field="dtp_input2" data-date-format="dd MM yyyy"
												data-date="" class="input-group date form_date infor">
												<input readonly="true" id="pubTime" value="<fmt:formatDate value="${doc.pubTime }" type="date" pattern="yyyy-MM-dd" />"
													name="queryCondition.startDate" class="form-control"
													size="16" type="text"> <span
													class="input-group-addon"><i
													class="glyphicon glyphicon-th"></i></span>
											</div>
											<div class="pull-right">
												<label class="datelabel">实施时间</label>
												<div data-link-format="yyyy-mm-dd"
													data-link-field="dtp_input2" data-date-format="dd MM yyyy"
													data-date="" class="input-group date form_date infor">
													<input readonly="true" id="impTime" value="<fmt:formatDate value="${doc.impTime }" type="date" pattern="yyyy-MM-dd" />"
														name="queryCondition.startDate" class="form-control"
														size="16" type="text"> <span
														class="input-group-addon"><i
														class="glyphicon glyphicon-th"></i></span>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">国家</label>
										<div class="col-sm-9">
											<input class="form-control" type="text" id="country" name="country" value="${doc.country }">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">关键词</label>
										<div class="col-sm-9">
											<input class="form-control" type="text" id="keyWord" name="keyWord" value="${doc.keyWord }">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">摘要</label>
										<div class="col-sm-9">
											<textarea name="description" class="form-control" rows="2" id="description">${doc.description }</textarea>
										</div>
									</div>
							</div>
							
							<div class="panel-heading">
								代替文档信息<span class="pull-right">
								<a class="btn btn-default btn-sm btn-xxs"
										href="javascript:createAddReplaceTr()">
										<span class="glyphicon glyphicon-plus"> </span>
									</a></span>
							</div>
							<div class="panel-body">
								<table class="table">
									<thead>
										<tr>
											<th>代替编号</th>
											<th>代替关系</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="replace_tb_id">
									<c:if test="${docReplaces.size() < 1 }">
										<tr id="tmp1">
											<td>
												<input class="form-control" type="text" name="docReplaces[0].oldDoc">
												<input type="hidden" name="docReplaces[0].newDoc" value="${doc.standardNo }">
											</td>
											<td>
												<input class="form-control" type="text" name="docReplaces[0].relation">
											</td>
											<td>
												<input type="hidden" name="docReplaces[0].id" value="-1">
												<button class="btn btn-default btn-sm"
													onclick="cancleAddReplace('tmp1')">
													<span class="glyphicon glyphicon-minus"> </span>
												</button></td>
										</tr>
										<tr id="remarktmp1">
											<td colspan="2" class="nobord">
												<textarea name="docReplaces[0].remark" class="form-control"  rows="2" placeholder="备注"></textarea>
											</td>
										</tr>
									</c:if>
									<c:forEach items="${docReplaces }" var="rpc">
										<tr id="${rpc.id}">
											<td>
												<input class="form-control" type="text" value="${rpc.oldDoc }">
												<input type="hidden" value="${doc.standardNo }">
											</td>
											<td>
												<c:if test="${rpc.relation == 1}">
													<input class="form-control" type="text" value="完全代替">
												</c:if>
												<c:if test="${rpc.relation != 1}">
												<input class="form-control" type="text" value="部分代替">
												</c:if>
											</td>
											<td>
											<input type="hidden" value="${rpc.id}">
											<button class="btn btn-default btn-sm"
													onclick="deleteDocReplace(${rpc.id})">
													<span class="glyphicon glyphicon-minus"> </span>
												</button></td>
										</tr>
										<tr id="remark${rpc.id}">
											<td colspan="2" class="nobord">
												<textarea class="form-control" rows="2" placeholder="备注">${rpc.remark }</textarea>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
							<c:if test="${doc.version != null}">
								<input type="hidden" name="version" value="${doc.version}" />
							</c:if>
							<input type="hidden" name="id" value="${doc.id}" />
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="panel-footer text-center">
								<button class="btn btn-default bb_m" onclick="toEditorPreview('${doc.id}')">
									<span>取消</span>
								</button>
								<button class="btn btn-primary bb_m" type="submit">
									<span>保存</span>
								</button>
							</div>
							</form>
						</div>
					</div>
					<div class="col-md-7">
						<div class="doc_real">
							<iframe src="${doc.reqUrl }" width="800" height="871" scrolling="yes">
							</iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$("#docEditLiId").addClass('active').siblings('li').removeClass('active');
		$('.form_date').datetimepicker({
			language : 'zh',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			format : 'yyyy-mm-dd'
		});
		function toEditorPreview(docId){
			location.href = "edit.html?docId=" + docId;
		}
	</script>

</body>
</html>
