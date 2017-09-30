
function pageMonitorSearch(tagState, kw, p, mntUser, title){

	var contextPath = $("#contextPath").val();
	$.ajax({
		url : contextPath + "standards/manage/monitorItems.html",
		type : "post",
		data : {
			title : title,
			tagState : tagState,
			kw : kw,
			p : p,
			mntUser : mntUser,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			$("#monitorItems").html(data);
		}
	});
}

function monitorSelectTagStates(kw, userName, title){
	var tagState = $("#monitorTagStates").val();
	if(tagState != null && tagState != ""){
		pageMonitorSearch(tagState, kw, 1, userName, title);
	}
}

function monitorSearch(userName, tagState, title){
	var kw = $("#monitorkw").val();
	pageMonitorSearch(tagState, kw, 1, userName, title);
}

function setDelDocNo(docId){
	$("#deledDocNo").val(docId);
	promiseModal("delDocTaskModal");
}
function delDocTask(tagState, kw, p, userName){
	
	var docId = $("#deledDocNo").val();
	var contextPath = $("#contextPath").val();
	if(docId != ""){
		$.ajax({
			url : contextPath + "standards/manage/delDocTasks.html",
			type : "post",
			data : {
				docId : docId,
				_csrf : $("#csrf").val()
			},
			success : function(data) {
				$("#delDocTaskModal").css({'display':'none', 'opacity':0});
				var msg = JSON.parse(data);
				if(!msg.success) {
					alert("删除任务分配出错");
				} else {
					document.getElementById("ant"+docId).innerHTML = "";
					document.getElementById("fit"+docId).innerHTML = "";
					document.getElementById("sec"+docId).innerHTML = "";
				}
			}
		});
	}
}

//设置要删除的文档的id
function setDeleteDocId(id){
	$("#deleteDocumentId").val(id);
	promiseModal("deleteDocumentModal");
}
//删除文档
function deleteDocument(tagState, kw, p, userName){
	
	var id = $("#deleteDocumentId").val();
	var contextPath = $("#contextPath").val();
	if(id != ""){
		var ids = [];
		ids.push(id);
		$.ajax({
			url : contextPath + "standards/doc/delete.html",
			type : "post",
			data : {
				ids : ids,
				_csrf : $("#csrf").val()
			},
			success : function(data) {
				$("#deleteDocumentModal").css({'display':'none', 'opacity':0});
				var msg = JSON.parse(data);
				if(msg.success) {
					pageMonitorSearch(tagState, kw, p, userName, 1);
				} else {
					promiseCommonModal("删除文档出错");
				}
			}
		});
	}
}

//重置文档状态
function renewDocState(id) {
	var ids = [];
	ids.push(id);
	var contextPath = $("#contextPath").val();
	$.ajax({
		url : contextPath + "standards/doc/stateConfirm.html",
		type : "post",
		data : {
			status : 2,
			ids : ids,
			flag : "synch",
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if(msg.success) {
				$("#docState_" + id).parent().parent().parent().html("<span class=\"text-info\">未打标</span>");
			} else {
				promiseCommonModal("操作失败");
			}
		}
	});
}