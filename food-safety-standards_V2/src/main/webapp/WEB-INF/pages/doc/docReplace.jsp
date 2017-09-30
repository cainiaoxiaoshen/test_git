<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>文档代替关系-标准项目</title>
<jsp:include page="../resource.jsp"></jsp:include>
<script src="<c:url value="/js/common.js"></c:url>"></script>
<script src="<c:url value="/js/docReplace.js"></c:url>"></script>
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
            <li class="active"><a href="docReplace.html">文档代替关系</a></li>
            <li><a href="docSynonym.html">指标同义词</a></li>
            </c:if>
        </ul>
    </div>
<div class="caption_m">
	<div class="tool_m">
          <div class="btn_m">
            <button class="btn btn-default" onclick="addReplace()"><i class="fa fa-plus"></i> <span>新建</span></button>
          </div>
        </div>
        <div class="page_top">
		    <ul class="pager">
				<li class="paginate_button prev_m"><a href="docReplace.html?p=${page.currentPage - 1 }"><i class="fa fa-angle-left"></i></a></li><li class="paginate_button next_m"><a href="docReplace.html?p=${page.currentPage + 1 }"><i class="fa fa-angle-right"></i></a></li>
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
        <th style="width: 200px">新文档编号</th>
        <th style="width: 200px">旧文档编号</th>
        <th>创建时间</th>
        <th>代替关系</th>
        <th>备注</th>
        <th style="width: 80px">历史记录</th>
        <th style="width: 150px">操作</th>
      </tr>
    </thead>
    <tbody id="repalce_tb">
      <c:forEach items="${page.items }" var="doc" varStatus="status">
      	<tr>
        <td>${doc.newDoc }</td>
        <td>${doc.oldDoc }</td>
        <td><fmt:formatDate value="${doc.createTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        <td><c:if test="${doc.relation == 0}">部分代替</c:if><c:if test="${doc.relation == 1}">完全代替</c:if></td>
        <td>${doc.remark }</td>
        <td><a href="docRepHistory.html?docNo=${doc.newDoc }&oldDocNo=${doc.oldDoc}" class="t_m"><i class="fa fa-history"></i><span>查看</span></a></td>
        <td>
	        <a href="javascript:modify(${status.index }, ${doc.id })"  class="t_m">
	        	<i class="fa fa-pencil-square-o"></i><span>修改</span>
	        </a>
	        <a href="javascript:del(${doc.id })" class="t_m">
	        	<i class="fa fa-trash"></i><span>删除</span>
	        </a>
        </td>
        <!--  <td>
	        <a href="#" class="t_m" data-toggle="modal" data-target="#myModal1">
	        	<i class="fa fa-pencil-square-o"></i><span>修改</span>
	        </a>
	        <a href="#" class="t_m" data-toggle="modal" data-target="#myModal">
	        	<i class="fa fa-trash"></i><span>删除</span>
	        </a>
        </td>-->
        
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
			<c:if test="${page.pages > 1 }">
			<div class="page_m">
			
				<ul class="pagination">
					<c:if test="${page.currentPage <= 1 }">
						<li class="paginate_button previous disabled"><a><i class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
					</c:if>
					<c:if test="${page.currentPage > 1 }">
						<li class="paginate_button"><a href="docReplace.html?p=${page.currentPage - 1 }"><i
							class="fa fa-angle-left" style="line-height: 1.4"></i></a></li>
					</c:if>
					<c:forEach items="${page.navigatePageNumbers }" var="n">
						<c:if test="${page.currentPage == n }">
							<li class="paginate_button active"><a href="docReplace.html?p=${n }">${n }</a></li>
						</c:if>
						<c:if test="${page.currentPage != n }">
							<li class="paginate_button"><a href="docReplace.html?p=${n }">${n }</a></li>
						</c:if>
					</c:forEach>
					
					<c:if test="${page.currentPage >= page.pages }">
						<li class="paginate_button next previous disabled">
							<a><i class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
					</c:if>
					<c:if test="${page.currentPage < page.pages }">
						<li class="paginate_button next"><a href="docReplace.html?p=${page.currentPage + 1 }"><i
							class="fa fa-angle-right" style="line-height: 1.4"></i></a></li>
					</c:if>
				</ul>
				
			</div>
</c:if>

    </div>
      </div>
      <!-- /. ROW  --> 
    </div>
    
    <div class="modal fade" aria-hidden="true" aria-labelledby="myModalLabel" id="myModal1" role="dialog" tabindex="-1">
  <div class="modal-backdrop fade in"></div>
  <div class="modal-dialog modal-right" style="margin-top:120px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" id="closeModifyBtn">&times;</button>
        <h4 class="modal-title" id="replaceActionTitle">修改文档代替关系</h4>
      </div>
      <div class="modal-body">
        <form role="form" class="form-horizontal">
          <div class="form-group">
            <label for="firstname" class="col-sm-3 control-label">新文档编号</label>
            <div class="col-sm-9">
              <input id="md_newDocNo" class="form-control" type="text">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">旧文档编号</label>
            <div class="col-sm-9">
              <input id="md_oldDocNo" class="form-control" type="text">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">代替关系</label>
            <div class="col-sm-9">
              <select class="form-control" id="md_relation">
                <option value="1">完全代替</option>
                <option value="0">部分代替</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
              <textarea placeholder="请输入内容" class="form-control" rows="5" id="md_remark"></textarea>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-default" id="cancelModifyBtn">取消</button>
        <button class="btn btn-primary" id="saveModifyBtn">确定</button>
      </div>
    </div>
  </div>
</div>
    
    
    
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