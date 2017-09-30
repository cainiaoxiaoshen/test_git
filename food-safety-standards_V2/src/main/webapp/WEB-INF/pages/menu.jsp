<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar-default navbar-side" role="navigation">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrf" />
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li><img src="<c:url value="/assets/img/find_user.png"></c:url>"
				class="user-image img-responsive" title="数据打标平台" /></li>
				
			<li <c:if test="${menu == 'index' }">class="active"</c:if>><a href="<c:url value="/standards/doc/fss.html"></c:url>" data-toggle="tooltip"
				data-placement="right" title="首页"><i class="fa fa-home fa-3x"></i></a>
			</li>
			<li <c:if test="${menu == 'doc' }">class="active"</c:if>><a href="<c:url value="/standards/doc/docManage.html"></c:url>" data-toggle="tooltip"
				data-placement="right" title="文档库"><i class="fa fa-file fa-3x"></i></a>
			</li>
			<c:if test="${roleLv != 'ANNOTATE' }">
				<li <c:if test="${menu == 'search' }">class="active"</c:if>><a href="<c:url value="/standards/search/docSearch.html"></c:url>" data-toggle="tooltip"
					data-placement="right" title="搜索"><i class="fa fa-search fa-3x"></i></a>
				</li>
			</c:if>
			<li <c:if test="${menu == 'manage' }">class="active"</c:if>>
				<c:if test="${roleLv == 'ADMIN' }">
					<a href="<c:url value="/standards/manage/account.html"></c:url>" data-toggle="tooltip"
					data-placement="right" title="管理">
						<i class="fa fa-cog fa-3x"></i>
					</a>
				</c:if>
			</li>
			<li class="b_user" onclick='showUser()'> <a><i class="fa fa-user fa-3x"></i></a> </li>
		</ul>
	</div>
	<div style="display: none;">
		<form action="<c:url value="/standards/logout.html"/>" method="post" id="logout_form">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrf" />
		</form>
	</div>
</nav>
<input type="hidden" value="<c:url value="/"></c:url>" id="contextPath">

<a href="javascript:void(0)" class="usr_block" id="pop1" style="display:none;">
	<span><sec:authentication property="name"/></span><hr />
	<b onclick="logout()">退出</b>
</a>

<!-- 
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
  
  <!-- 信息提示窗口 -->
  <div class="modal fade in" aria-hidden="true" aria-labelledby="myModalLabel" id="msgPromptWindow" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closePromptMsgWindow()">&times;</button>
          <h4 class="modal-title">系统提示</h4>
        </div>
        <div class="modal-body" id="msgPromptTagId"></div>
        <div class="modal-footer">
          <button class="btn btn-primary" onclick="closePromptMsgWindow()">确定</button>
        </div>
      </div>
    </div>
  </div>
  
  
  <div class="modal fade in" aria-hidden="true" aria-labelledby="myModalLabel" id="promptOperateWindow" role="dialog" tabindex="-1">
    <div class="modal-backdrop fade in"></div>
    <div class="modal-dialog" style="margin-top:100px">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="cancelOperateMsgWindow()">&times;</button>
          <h4 class="modal-title">系统提示</h4>
        </div>
        <div class="modal-body" id="promptOperateTagId"></div>
        <div class="modal-footer">
          <button class="btn btn-default" onclick="cancelOperateMsgWindow()">取消</button>
          <button class="btn btn-primary" id="promptOperateWindowConfirmBtn">确定</button>
        </div>
      </div>
    </div>
  </div>
<script src="<c:url value="/js/common.js"></c:url>"></script>
<script type="text/javascript">
function showUser(){
	$("#pop1").show();
	$("#pop1").focus();
	$("#pop1").blur(function () {
		$(this).hide();
	});	
}
function logout() {
	$("#logout_form").submit();
}

$.ajaxSetup({  
	type: 'POST',  
	contentType:"application/x-www-form-urlencoded;charset=utf-8",  
	complete: function(xhr,status) {  
		var sessionStatus = xhr.getResponseHeader('sessionstatus');  
		if(sessionStatus == 'timeout') {
			var contextPath = $("#contextPath").val();
			window.location.href = contextPath + 'standards/login.html';  
		}
	}
});

</script>