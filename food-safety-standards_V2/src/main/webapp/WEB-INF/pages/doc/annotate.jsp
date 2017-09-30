<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>文档库-标准项目</title>
<link href="<c:url value="/assets/css/bootstrap.css"></c:url>" rel="stylesheet" />
<link href="<c:url value="/assets/css/bootstrap-datetimepicker.min.css"></c:url>" rel="stylesheet" />
<jsp:include page="../resource.jsp"></jsp:include>
<script type="text/javascript" src="<c:url value="/assets/js/bootstrap-datetimepicker.js"></c:url>"></script>
<script src="<c:url value="/js/doc.js"></c:url>"></script>
<script src="<c:url value="/js/annotate.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div class="page-wrapper">
			<div class="page-inner">
				<jsp:include page="docInfoNavigationPage.jsp"></jsp:include>
				<div class="caption_m">
					<span class="checkbox-inline"> <label><input
							type="checkbox" id="selectAll" onclick="selectAll('selectAll', 'antcheckbox')">全选</label>
					</span>
					<c:if test="${canDelete }">
						<a href="javascript:delAnnotate('${doc.id }')" class="t_m">
							<i class="fa fa-trash-o"></i><span>删除</span>
						</a>
					</c:if>
					<c:if test="${antTotal > 0 }">
						<a href="exportExcel.html?docId=${doc.id }" class="t_m">
							<i class="fa fa-download"></i>
							<span>下载打标结果</span>
						</a>
					</c:if>
					<span class="text-info">*  双击下面单元格可以进行编辑 </span>
					<div class="page_top">
						<ul class="pager">
							<li class="paginate_button prev_m"><a href="annotate.html?docId=${doc.id }&p=${page.pageNum - 1 }"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="annotate.html?docId=${doc.id }&p=${page.pageNum + 1 }"><i class="fa fa-angle-right"></i></a></li>
						</ul>
						<span class="tableinfo_m">共<em>${page.total }</em>条打标结果，第<b>${page.pageNum }</b>页
						</span>
						<div class="filter form-inline">
							<em class="pull-left">筛选：</em>
							<div data-link-format="yyyy-mm-dd" data-link-field="dtp_input2"
								data-date-format="dd MM yyyy" data-date=""
								class="input-group date form_date infor">
								<input id="annotateDate" readonly="true" value="${date }" onchange="getAnnotateByDate(${doc.id })"
									name="queryCondition.startDate" class="form-control" size="16"
									type="text"> <span class="input-group-addon"><i
									class="glyphicon glyphicon-th"></i></span>
							</div>
						</div>
					</div>
				</div>

				<!-- /. ROW  -->
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover"
						style="min-width: 3650px;">
						<thead>
							<tr>
								<th style="width: 30px;">&nbsp;</th>
								<th style="width: 170px;">产品</th>
								<th style="width: 170px;">指标</th>
								<th style="width: 70px;">最大限值</th>
								<th style="width: 70px;">最小限值</th>
								<th style="width: 90px;">单位</th>
								<th style="width: 130px;">ADI</th>
								<th style="width: 200px;">AdiWeb</th>
								<th style="width: 130px;">CNS</th>
								<th style="width: 130px;">INS</th>
								<th style="width: 130px;">CAS</th>
								<th style="width: 170px;">结构方程式</th>
								<th style="width: 170px;">摩尔方程式</th>
								<th style="width: 170px;">属性备注</th>
								<th style="width: 170px;">毒理学指标</th>
								<th style="width: 170px;">生物学指标</th>
								<th style="width: 130px;">功能</th>
								<th style="width: 170px;">病理学指标</th>
								<th style="width: 200px;">产品备注</th>
								<th style="width: 200px;">物质备注</th>
								<th style="width: 170px;">产品标准</th>
								<th style="width: 170px;">检测标准</th>
								<th style="width: 160px;">创建时间</th>

							</tr>
						</thead>
						<tbody id="antResultTbody">
							<c:forEach items="${page.list }" var="ant">
								<tr>
									<td><input type="checkbox" name="antcheckbox" value="${ant.id }"></td>
									<td antId="${ant.id }" antType="1">${ant.product }</td>
									<td antId="${ant.id }" antType="2">${ant.factor }</td>
									<td antId="${ant.id }" antType="3">${ant.max }</td>
									<td antId="${ant.id }" antType="4">${ant.min }</td>
									<td antId="${ant.id }" antType="5">${ant.unit }</td>
									<td antId="${ant.id }" antType="6">${ant.adi }</td>
									<td antId="${ant.id }" antType="7">${ant.adiWeb }</td>
									<td antId="${ant.id }" antType="8">${ant.cns }</td>
									<td antId="${ant.id }" antType="9">${ant.ins }</td>
									<td antId="${ant.id }" antType="10">${ant.cas }</td>

									<td antId="${ant.id }" antType="11">${ant.struc }</td>
									<td antId="${ant.id }" antType="12">${ant.mole }</td>
									<td antId="${ant.id }" antType="13">${ant.property }</td>
									<td antId="${ant.id }" antType="14">${ant.toxico }</td>
									<td antId="${ant.id }" antType="15">${ant.biological }</td>
									<td antId="${ant.id }" antType="16">${ant.funct }</td>
									<td antId="${ant.id }" antType="17">${ant.disease }</td>
									<td antId="${ant.id }" antType="18">${ant.proremark }</td>
									<td antId="${ant.id }" antType="19">${ant.facremark }</td>
									<td antId="${ant.id }" antType="20">${ant.prostd }</td>
									<td antId="${ant.id }" antType="21">${ant.testsId }</td>
									<td><fmt:formatDate value="${ant.createDate }" type="date"
											pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:if test="${page.pages > 1 }">
					<div class="page_m">
						
							<ul class="pagination">
								<c:if test="${!page.hasPreviousPage }">
									<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
								</c:if>
								<c:if test="${page.hasPreviousPage }">
									<li class="paginate_button"><a href="annotate.html?docId=${doc.id }&p=${page.prePage }"><i
										class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
								</c:if>
								<c:forEach items="${page.navigatepageNums }" var="n">
									<c:if test="${page.pageNum == n }">
										<li class="paginate_button active"><a href="annotate.html?docId=${doc.id }&p=${n }">${n }</a></li>
									</c:if>
									<c:if test="${page.pageNum != n }">
										<li class="paginate_button"><a href="annotate.html?docId=${doc.id }&p=${n }">${n }</a></li>
									</c:if>
								</c:forEach>
								
								<c:if test="${!page.hasNextPage }">
									<li class="paginate_button next previous disabled">
										<a><i class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
								</c:if>
								<c:if test="${page.hasNextPage }">
									<li class="paginate_button next"><a href="annotate.html?docId=${doc.id }&p=${page.nextPage }"><i
										class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
								</c:if>
							</ul>
							
						</div>
					</c:if>
			</div>
		</div>
	</div>

<!--
   <div class="modal fade in" aria-hidden="true" aria-labelledby="myModalLabel" id="delAnnotateModal" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" aria-hide="true" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" id="myModalLabel">系统提示</h4>
        </div>
        <div class="modal-body">您确定要删除所选的记录吗？</div>
        <div class="modal-footer">
          <button class="btn btn-default" aria-hidden="true" data-dismiss="modal">取消</button>
          <button class="btn btn-primary">确定</button>
        </div>
      </div>
    </div>
  </div>
  
  <div class="modal fade in" aria-hidden="true" aria-labelledby="myModalLabel" id="commonModal" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" aria-hide="true" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" id="commonModalLabel">系统提示</h4>
        </div>
        <div class="modal-body" id="commonModalMsg"></div>
        <div class="modal-footer">
          <button class="btn btn-primary" aria-hidden="true" data-dismiss="modal">确定</button>
        </div>
      </div>
    </div>
  </div>
  -->
  </body>
  <script type="text/javascript">
    $("#docAnnotateLiId").addClass('active').siblings('li').removeClass('active');
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
	var canDelete = "${canDelete }";
	if(canDelete == "true") {
		addClick("${doc.id }");
	}
</script>
</html>
