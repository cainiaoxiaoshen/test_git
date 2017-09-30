<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${view == 1 }">
	<div class="cap_home">
		<c:if test="${roleLv != 'ANNOTATE' }">
			<div class="tool_m">
				<div class="btn_m">
					<button class="btn btn-default"
						onclick="location='<c:url value="/standards/doc/docManage.html"></c:url>'">
						<i class="fa fa-plus"></i> <span>导入文档</span>
					</button>
				</div>
			</div>
		</c:if>
		<div class="page_top">
			<ul class="pager">
				<li class="paginate_button prev_m"><a href="javascript:docItems(${tagState }, ${page.prePage }, ${view })"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="javascript:docItems(${tagState }, ${page.nextPage }, ${view })"><i class="fa fa-angle-right"></i></a></li>
			</ul>
			<span class="tableinfo_m">共<em>${page.total }</em>篇文档，第<b>${page.pageNum }</b>页
			</span> <span class="form-group sel_m"> <label>筛选：</label><select
				onchange="tagStatesFilter(${view })" id="tagStatesSelectId"
				class="form-control">

			</select></span>
		</div>
	</div>
</c:if>
<c:if test="${view == 2 }">
	<div class="caption_m">
		<div class="tool_m">
			<div class="tool_doc">
				<span class="checkbox-inline"> <label> <input
						type="checkbox" id="selectAll"
						onclick="selectAll('selectAll', 'doccheckbox')">全选
				</label>
				</span>
				<c:if test="${roleLv != 'CHECK'}">
					<a
						href="javascript:stateUpdate(3)"
						class="b_m"><i class="fa fa-file"></i><span>打标完成</span></a>
				</c:if>
				<c:if test="${roleLv != 'ANNOTATE'}">
					<a
						href="javascript:stateUpdate(4)"
						class="b_m"><i class="fa fa-file"></i><span>一审完成</span></a>
				</c:if>
				<c:if test="${roleLv != 'ANNOTATE'}">
					<a
						href="javascript:stateUpdate(5)"
						class="b_m"><i class="fa fa-file"></i><span>入库</span></a>
				</c:if>
			</div>
		</div>
		<div class="page_top">
			<ul class="pager">
				<li class="paginate_button prev_m"><a href="javascript:docItems(${tagState }, ${page.prePage }, ${view })"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="javascript:docItems(${tagState }, ${page.nextPage }, ${view })"><i class="fa fa-angle-right"></i></a></li>
			</ul>
			<span class="tableinfo_m">共<em>${page.total }</em>篇文档，第<b>${page.pageNum }</b>页
			</span> <span class="form-group sel_m"> <label>筛选：</label><select
				onchange="tagStatesFilter(${view })" id="tagStatesSelectId"
				class="form-control">

			</select></span>
		</div>
	</div>
</c:if>
<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<c:if test="${view == 2 }">
					<th>选择</th>
				</c:if>
				<th style="width: 200px;">编号</th>
				<th>类型</th>
				<c:if test="${view == 2 }">
					<th>格式</th>
					<th>创建者</th>
					<th style="width: 160px;">创建时间</th>
				</c:if>
				<th>文档名称</th>
				<th>国家</th>
				<th style="width: 80px;">打标状态</th>
				<c:if test="${view == 1 }">
					<th>打标员</th>
					<th>一级审核员</th>
					<th>二级审核员</th>
				</c:if>
				<th style="width: 150px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="doc">
				<tr>
					<c:if test="${view == 2 }">
						<td class="charts citytop">
							<input type="checkbox" name="doccheckbox" value="${doc.id }" />
						</td>
					</c:if>
					<td>${doc.standardNo }</td>
					<td>
						<c:if test="${doc.type == 1 }">标准</c:if> 
						<c:if test="${doc.type == 2 }">法规</c:if>
						<c:if test="${doc.type == 3 }">研究报告</c:if>
					</td>
					<c:if test="${view == 2 }">
						<td>${doc.format }</td>
						<td>${doc.createUser }</td>
						<td><fmt:formatDate value="${doc.createTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</c:if>
					<td>${doc.docName }</td>
					<td>${doc.country }</td>
					<td id="docStateTd_${doc.id }"><c:if test="${doc.tagState == 1 }">
							<span class="text-warning">打标中</span>
						</c:if> <c:if test="${doc.tagState == 2 }">
							<span class="text-info">未打标</span>
						</c:if> <c:if test="${doc.tagState == 3 }">
							<span class="text-review1">一审中</span>
						</c:if> <c:if test="${doc.tagState == 4 }">
							<span class="text-review2">二审中</span>
						</c:if> <c:if test="${doc.tagState == 5 }">
							<span class="text-success">入库</span>
						</c:if></td>
						
					<c:if test="${view == 1 }">
						<td>${doc.annotateUser }</td>
						<td>${doc.firstCheckUser }</td>
						<td>${doc.secondCheckUser }</td>
					</c:if>
					<td>
						<!-- 未打标状态，先修改打标状态位打标中状态 -->
						<c:if test="${doc.tagState == 2 && (doc.annotateUser == userName || roleLv == 'ADMIN') }">
							<a href="javascript:startMarkStateUpdate(${doc.id }, '${doc.reqUrl }')" class="t_m">
								<i class="fa fa-pencil-square-o"></i><span>打标</span>
							</a>
							
						</c:if>
						
						<!-- 打标中状态 -->
						<c:if test="${doc.tagState == 1 && (doc.annotateUser == userName || roleLv == 'ADMIN') }">
							<a href="${doc.reqUrl }" class="t_m" target="_blank">
								<i class="fa fa-pencil-square-o"></i><span>打标</span>
							</a>
						</c:if>
						<!-- 一审 -->
						<c:if test="${doc.tagState == 3 && (doc.firstCheckUser == userName || roleLv == 'ADMIN') }">
							<a href="${doc.reqUrl }" class="t_m" target="_blank">
								<i class="fa fa-pencil-square-o"></i><span>打标</span>
							</a>
						</c:if>
						<!-- 二审 -->
						<c:if test="${doc.tagState == 4 && (doc.secondCheckUser == userName || roleLv == 'ADMIN') }">
							<a href="${doc.reqUrl }" class="t_m" target="_blank">
								<i class="fa fa-pencil-square-o"></i><span>打标</span>
							</a>
						</c:if>
						<c:if test="${doc.tagState == 5 && roleLv == 'ADMIN' }">
							<a href="${doc.reqUrl }" class="t_m" target="_blank">
								<i class="fa fa-pencil-square-o"></i><span>打标</span>
							</a>
						</c:if>
						<a href="<c:url value="/standards/annotate/annotate.html?docId=${doc.id }"></c:url>" class="t_m">
							<i class="fa fa-cog"></i><span>管理</span>
						</a>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="page_m">
			<c:if test="${page.pages > 1 }">
			<ul class="pagination">
				<c:if test="${!page.hasPreviousPage }">
					<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left"></i></a></li>
				</c:if>
				<c:if test="${page.hasPreviousPage }">
					<li class="paginate_button"><a href="javascript:docItems(${tagState }, ${page.prePage }, ${view })"><i
						class="fa fa-angle-left"></i></a></li>
				</c:if>
				<c:forEach items="${page.navigatepageNums }" var="n">
					<c:if test="${page.pageNum == n }">
						<li class="paginate_button active"><a href="javascript:docItems(${tagState }, ${n }, ${view })">${n }</a></li>
					</c:if>
					<c:if test="${page.pageNum != n }">
						<li class="paginate_button"><a href="javascript:docItems(${tagState }, ${n }, ${view })">${n }</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${!page.hasNextPage }">
					<li class="paginate_button next previous disabled">
						<a><i class="fa fa-angle-right"></i></a></li>
				</c:if>
				<c:if test="${page.hasNextPage }">
					<li class="paginate_button next"><a href="javascript:docItems(${tagState }, ${page.nextPage }, ${view })"><i
						class="fa fa-angle-right"></i></a></li>
				</c:if>
			</ul>
			</c:if>
		</div>
	</div>
</div>

<script type="text/javascript">
	initTagStateSelect("${tagState }");
</script>
