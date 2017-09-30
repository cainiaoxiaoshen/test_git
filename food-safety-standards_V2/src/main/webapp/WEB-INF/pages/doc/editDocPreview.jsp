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
								<h3 class="panel-title">
									基本信息
									<div class="pull-right">
										<a href="edit.html?docId=${doc.id }&edit=true" class="t_m"><i
											class="fa fa-pencil"></i><span>编辑</span></a>
									</div>
								</h3>
							</div>
							<div class="panel-body">
								<form role="form" class="form-horizontal">
									<div class="form-group">
										<label class="col-sm-3 control-label">文档名称</label>
										<div class="col-sm-9">
											<p class="form-control-static">${doc.docName }</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">产品体系</label>
										<div class="col-sm-9">
											<p class="form-control-static">${doc.proSystem }</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">正文语种</label>
										<div class="col-sm-9">
											<p class="form-control-static">${doc.language }</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">发布时间</label>
										<div class="col-sm-9">
											<div class="date">
												<p class="form-control-static"><fmt:formatDate value="${doc.pubTime }" type="date" pattern="yyyy-MM-dd" /></p>
											</div>
											<div class="pull-right">
												<label class="control-label datelabel">实施时间</label>
												<div class="date">
													<p class="form-control-static"><fmt:formatDate value="${doc.impTime }" type="date" pattern="yyyy-MM-dd" /></p>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">国家</label>
										<div class="col-sm-9">
											<p class="form-control-static">${doc.country }</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">关键词</label>
										<div class="col-sm-9">
											<p class="form-control-static">${doc.keyWord }</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">摘要</label>
										<div class="col-sm-9">
											<p class="form-control-static">${doc.description }</p>
										</div>
									</div>
								</form>
							</div>
							<div class="panel-heading">
								代替文档信息<span class="pull-right"> </span>
							</div>
							<div class="panel-body">
								<table class="table">
									<thead>
										<tr>
											<th>代替编号</th>
											<th>代替关系</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${docReplaces }" var="rpc">
											<tr>
												<td><p class="form-control-static">${rpc.oldDoc }</p></td>
												<td>
													<p class="form-control-static">
														<c:if test="${rpc.relation == 1}">完全代替</c:if>
														<c:if test="${rpc.relation != 1}">部分代替</c:if>
													</p>
												</td>
	
											</tr>
											<tr>
												<td colspan="2" class="nobord"><p
														class="form-control-static">备注：${rpc.remark }</p></td>
											</tr>
										</c:forEach>
										
									</tbody>
								</table>
							</div>

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
</body>
<script type="text/javascript">
    $("#docEditLiId").addClass('active').siblings('li').removeClass('active');
</script>
</html>
