<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="caption_m">
	<div class="tool_m">
		<div class="hd_m"><c:if test="${title == 1 }">文档打标监测</c:if><c:if test="${title == 0 }">用户打标监测</c:if></div>
	</div>
	<div class="page_top">
		<ul class="pager">
			<li class="paginate_button prev_m"><a href="javascript:pageMonitorSearch(${container.outputTagState }, '${container.keyWord }', ${page.prePage }, '${container.user }', ${title })"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="javascript:pageMonitorSearch(${container.outputTagState }, '${container.keyWord }', ${page.nextPage }, '${container.user }', ${title })"><i class="fa fa-angle-right"></i></a></li>
		</ul>


		<span class="tableinfo_m">共<em>${page.total }</em>条记录，第<b>${page.pageNum }</b>页
		</span>
		<div class="filter form-inline">
			<select class="form-control input-sm" id="monitorTagStates"
				onchange="monitorSelectTagStates('${container.keyWord }', '${container.user }', ${title })">
				<option value="0"
					<c:if test="${container.outputTagState == 0 }">selected="selected"</c:if>>全部</option>
				<option value="2"
					<c:if test="${container.outputTagState == 2 }">selected="selected"</c:if>>未打标</option>
				<option value="1"
					<c:if test="${container.outputTagState == 1 }">selected="selected"</c:if>>打标中</option>
				<option value="3"
					<c:if test="${container.outputTagState == 3 }">selected="selected"</c:if>>一审中</option>
				<option value="4"
					<c:if test="${container.outputTagState == 4 }">selected="selected"</c:if>>二审中</option>
				<option value="5"
					<c:if test="${container.outputTagState == 5 }">selected="selected"</c:if>>入库</option>
			</select>
			<div class="input-group input-group-sm">
				<input class="form-control" placeholder="搜索国家、文档、用户" id="monitorkw"
					value="${container.keyWord }"> <span class="input-group-btn">
					<button class="btn btn-default" onclick="monitorSearch('${container.user }', ${container.outputTagState }, ${title })">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</div>
	</div>
</div>

<!-- /. ROW  -->
<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>国家</th>
				<th style="width: 200px;">编号</th>
				<th>类型</th>
				<th>文档名称</th>
				<th style="width: 80px;">文档状态</th>
				<th>打标员</th>

				<th>一级审核员</th>
				<th>二级审核员</th>
				<th>打标结果数</th>
				<th>审核记录数</th>
				<th>错误率</th>
				<th style="width: 70px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="doc">
				<tr>
					<td>${doc.country }</td>
					<td>${doc.standardNo }</td>
					<td><c:if test="${doc.type == 1}">标准</c:if> <c:if
							test="${doc.type == 2}">法规</c:if> <c:if test="${doc.type == 3}">研究报告</c:if>
					</td>
					<td>${doc.docName }</td>
					<td><c:if test="${doc.tagState == 1 }">
							<span class="text-warning">打标中</span>
						</c:if> <c:if test="${doc.tagState == 2 }">
							<span class="text-info">未打标</span>
						</c:if> <c:if test="${doc.tagState == 3 }">
							<span class="text-review1">一审中</span>
						</c:if> <c:if test="${doc.tagState == 4 }">
							<span class="text-review2">二审中</span>
						</c:if>
						<c:if test="${doc.tagState == 5 }">
							<span class="input-group-btn">
								<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<span class="text-success" id="docState_${doc.id }">入库</span>
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="javascript:renewDocState(${doc.id })">未打标</a></li>
								</ul>
							</span>
						</c:if>
						</td>
					<td id="ant${doc.id }">
						<a href="<c:url value="/standards/manage/accountMonitor.html?mntUser=${doc.annotateUser }"></c:url>">${doc.annotateUser }</a>
					</td>
					<td id="fit${doc.id }">
						<a href="<c:url value="/standards/manage/accountMonitor.html?mntUser=${doc.firstCheckUser }"></c:url>">${doc.firstCheckUser }</a>
					</td>
					<td id="sec${doc.id }">
						<a href="<c:url value="/standards/manage/accountMonitor.html?mntUser=${doc.secondCheckUser }"></c:url>">${doc.secondCheckUser }</a>
					</td>
					<td>
						<a href="<c:url value="/standards/annotate/annotate.html?docId=${doc.id }"></c:url>">${doc.antNum }</a>
					</td>
					<td><a href="checkDetails.html?docId=${doc.id }">${doc.antChkNum }</a></td>
					<td>
						<c:if test="${doc.errorRate == null }">0%</c:if>
						<c:if test="${doc.errorRate != null }">${doc.errorRate }%</c:if>
					</td>
					<td>
						<span class="input-group-btn sub">
							<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
								<span class="t_m"><i class="fa fa-trash"></i><span>删除</span></span>
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:setDelDocNo('${doc.id }')">删除分配任务</a></li>
								<li><a href="javascript:setDeleteDocId('${doc.id }')">删除该文档</a></li>
							</ul>
						</span>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<c:if test="${page.pages > 1 }">
<div class="page_m">
		<ul class="pagination">
			<c:if test="${!page.hasPreviousPage }">
				<li class="paginate_button previous disabled"><a><i
						class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
			</c:if>
			<c:if test="${page.hasPreviousPage }">
				<li class="paginate_button"><a
					href="javascript:pageMonitorSearch(${container.outputTagState }, '${container.keyWord }', ${page.prePage }, '${container.user }', ${title })"><i
						class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
			</c:if>
			<c:forEach items="${page.navigatepageNums }" var="n">
				<c:if test="${page.pageNum == n }">
					<li class="paginate_button active"><a
						href="javascript:pageMonitorSearch(${container.outputTagState }, '${container.keyWord }', ${n }, '${container.user }', ${title })">${n }</a></li>
				</c:if>
				<c:if test="${page.pageNum != n }">
					<li class="paginate_button"><a
						href="javascript:pageMonitorSearch(${container.outputTagState }, '${container.keyWord }', ${n }, '${container.user }', ${title })">${n }</a></li>
				</c:if>
			</c:forEach>

			<c:if test="${!page.hasNextPage }">
				<li class="paginate_button next previous disabled"><a><i
						class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
			</c:if>
			<c:if test="${page.hasNextPage }">
				<li class="paginate_button next"><a
					href="javascript:pageMonitorSearch(${container.outputTagState }, '${container.keyWord }', ${page.nextPage }, '${container.user }', ${title })"><i
						class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
			</c:if>
		</ul>
</div>
</c:if>
<div class="modal fade" aria-hidden="true" aria-labelledby="myModalLabel" id="delDocTaskModal" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" aria-hide="true" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" id="myModalLabel">系统提示</h4>
        </div>
        <div class="modal-body">您确定要删除分配任务吗？</div>
        <div class="modal-footer">
        	<input type="hidden" id="deledDocNo">
          <button class="btn btn-default" aria-hidden="true" data-dismiss="modal">取消</button>
          <button class="btn btn-primary" onclick="delDocTask('${container.outputTagState }', '${container.keyWord }', ${page.pageNum }, '${container.user }')">确定</button>
        </div>
      </div>
    </div>
  </div>
  
  <div class="modal fade" aria-hidden="true" aria-labelledby="myModalLabel" id="deleteDocumentModal" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" aria-hide="true" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">系统提示</h4>
        </div>
        <div class="modal-body">您确定要该文档吗？</div>
        <div class="modal-footer">
        	<input type="hidden" id="deleteDocumentId">
          <button class="btn btn-default" aria-hidden="true" data-dismiss="modal">取消</button>
          <button class="btn btn-primary" onclick="deleteDocument('${container.outputTagState }', '${container.keyWord }', ${page.pageNum }, '${container.user }')">确定</button>
        </div>
      </div>
    </div>
  </div>
  
<script type="text/javascript">
document.onkeydown = function (event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		monitorSearch('${container.user }', '${container.outputTagState }', '${title }');
	}
};
</script>
  
