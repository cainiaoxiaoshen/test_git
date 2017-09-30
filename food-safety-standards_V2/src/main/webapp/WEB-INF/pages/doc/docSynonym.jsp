<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>指标同义词-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/common.js"></c:url>"></script>
<script type="text/javascript" src="<c:url value="/js/docSynonym.js"/>"></script>

</head>
<body>
<div class="wrapper">
  <jsp:include page="../menu.jsp"></jsp:include>
  <!-- /. NAV SIDE  -->
  <div class="page-wrapper" >
    <div class="page-inner">
    <c:if test="${roleLv != 'ANNOTATE' }">
        <div class="search_top form-inline">
			<div class="form-control">
				<input class="srch-txt" placeholder="查找文档" type="text" id="kw"> <span
					class="srch-btn"><a href="javascript:searchDoc('', '', '', 0)"><i class="fa fa-search"></i></a></span>
			</div>
      </div>
      </c:if>
      <!-- /. search  -->
      <div class="add_m"> 
          <!-- Advanced Tables -->
          <ul class="nav nav-tabs">
            <li><a href="docManage.html">文档管理</a></li>
            <c:if test="${roleLv != 'ANNOTATE' }">
            <li><a href="docReplace.html">文档代替关系</a></li>
            <li class="active"><a href="docSynonym.html">指标同义词</a></li>
            </c:if>
        </ul>
    </div>
<div class="caption_m">
	<div class="tool_m">
          <div class="btn_m">
          	<input type="hidden" value="<sec:authentication property='name'/>" id="username">
            <button class="btn btn-default" onclick="add()"><i class="fa fa-plus"></i> <span>新建</span></button>
          </div>
        </div>
        <div class="page_top">
		    <ul class="pager">
				<li class="paginate_button prev_m"><a href="docSynonym.html?p=${page.currentPage - 1 }"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="docSynonym.html?p=${page.currentPage + 1 }"><i class="fa fa-angle-right"></i></a></li>		
			</ul>
                <span class="tableinfo_m">共<em>${page.total }</em>条记录，第<b>${page.currentPage }</b>页</span>
            </div>
         </div>
         
          <!-- /. ROW  -->
          <div class="table-responsive">
          	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrf" />
            <table class="table table-striped table-bordered table-hover" id="content">
    <thead>
      <tr>
        <th>同义词1</th>
        <th>同义词2</th>
        <th>创建者</th>
        <th style="width: 160px;">创建时间</th>
        <th style="width: 150px;">操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${page.items }" var="sym" varStatus="status">
      	<tr>
        <td>${sym.mainWord }</td>
        <td>${sym.synWord }</td>
        <td>${sym.createUser }</td>
        <td><fmt:formatDate value="${sym.createTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        <td><a href="javascript:modify(${status.index }, ${sym.id })" class="t_m"><i class="fa fa-pencil-square-o"></i><span>修改</span></a><a href="javascript:del(${sym.id })" class="t_m"><i class="fa fa-trash"></i><span>删除</span></a></td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
		<c:if test="${page.pages > 1 }">
			<div class="page_m">
				<ul class="pagination">
					<c:if test="${page.currentPage <= 1 }">
						<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left" style="line-height:1.4"></i></a></li>
					</c:if>
					<c:if test="${page.currentPage > 1 }">
						<li class="paginate_button"><a href="docSynonym.html?p=${page.currentPage - 1 }"><i
							class="fa fa-angle-left" style="line-height:1.4"></i></a></li>
					</c:if>
					<c:forEach items="${page.navigatePageNumbers }" var="n">
						<c:if test="${page.currentPage == n }">
							<li class="paginate_button active"><a href="docSynonym.html?p=${n }">${n }</a></li>
						</c:if>
						<c:if test="${page.currentPage != n }">
							<li class="paginate_button"><a href="docSynonym.html?p=${n }">${n }</a></li>
						</c:if>
					</c:forEach>
					
					<c:if test="${page.currentPage >= page.pages }">
						<li class="paginate_button next previous disabled">
							<a><i class="fa fa-angle-right" style="line-height:1.4"></i></a></li>
					</c:if>
					<c:if test="${page.currentPage < page.pages }">
						<li class="paginate_button next"><a href="docSynonym.html?p=${page.currentPage + 1 }"><i
							class="fa fa-angle-right" style="line-height:1.4"></i></a></li>
					</c:if>
				</ul>
				
			</div>
	</c:if>

    </div>
      </div>
      <!-- /. ROW  --> 
    </div>
    <!-- /. PAGE INNER  --> 
  </div>
  <!-- /. PAGE WRAPPER  --> 
</div>
<!-- /. WRAPPER  --> 
 <div class="modal fade in" aria-hidden="true" aria-labelledby="myModalLabel" id="myModal" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" aria-hide="true" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" id="myModalLabel">系统提示</h4>
        </div>
        <div class="modal-body">您确定要删除本条记录吗？</div>
        <div class="modal-footer">
          <button class="btn btn-default" aria-hidden="true" data-dismiss="modal">取消</button>
          <button class="btn btn-primary">确定</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /. modal  --> 
</body>
</html>