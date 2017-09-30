
var gaTagStateValues = [0, 2, 1, 3, 4, 5];

var gaTagStateTexts = ["全部", "未打标", "打标中", "一审中", "二审中", "入库"];

var requestContextPath = $("#contextPath").val();

//全选--checkboxAllId：全选checkbox的id，checkboxName：被选择的checkbox的name
function selectAll(checkboxAllId, checkboxName) {

	var checkboxs = document.getElementsByName(checkboxName);
	var all = document.getElementById(checkboxAllId);
	for (var i = 0; i < checkboxs.length; i++) {
		var e = checkboxs[i];
		if (all.checked) {
			e.checked = true;
		} else {
			e.checked = false;
		}
	}
}

function getSelectedValues(checkboxName) {
	var values = new Array();
	var checkboxs = document.getElementsByName(checkboxName);
	for (var i = 0; i < checkboxs.length; i++) {
		var e = checkboxs[i];
		if (e.checked) {
			values.push(e.value);
		}
	}
	return values;
}

function commonSearch()
{
	var kw = $("#commonSearchKeyWord").val();
	if(kw != ""){
		kw = encodeURIComponent(encodeURIComponent(kw));
	}
	url = requestContextPath + "standards/search/docSearch.html?kw=" + kw;
	window.open(url);
}

function hideModal(id){
	$("#" + id).hide();
}

function showModal(id){
	$("#" + id).show();
}

/*
function promiseModal(modalId){
	$("#" + modalId).css({'display':'block', 'opacity':1});
	
	$("#"+modalId+" > div > div > div.modal-header > button").click(function(){
		$("#"+ modalId).css({'display':'none', 'opacity':0});
		return false;
	});
	
	$("#"+modalId+" > div > div > div.modal-footer > button.btn.btn-default").click(function(){
		$("#"+modalId).css({'display':'none', 'opacity':0});
		return false;
	});
	
}

function promiseCommonModal(msg){
	$("#commonModalMsg").html(msg);
	var modalId = "commonModal";
	$("#" + modalId).css({'display':'block', 'opacity':1});
	
	$("#"+modalId+" > div > div > div.modal-header > button").click(function(){
		$("#"+ modalId).css({'display':'none', 'opacity':0});
		return false;
	});
	
	$("#"+modalId+" > div > div > div.modal-footer > button.btn.btn-primary").click(function(){
		$("#"+modalId).css({'display':'none', 'opacity':0});
		return false;
	});
	
}
*/

//----------------------------------------------
function docStateUpdate(docIds, updateState, functionName) {
	var requestContextPath = $("#contextPath").val();
	$.ajax({
		url : requestContextPath + "standards/doc/docStateUpdate.html",
		type : "post",
		data : {
			updateState : updateState,
			docIds : docIds,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if(msg.success) {
				functionName();
			} else {
				promptMsg("编号为" + msg.errorMsg + "的文档操作失败");
			}
		}
	});
}

//提示信息窗口
function promptMsg(msg)
{
	showPromptWindow("msgPromptWindow");
	$("#msgPromptTagId").html(msg);
}
//关闭提示信息窗口
function closePromptMsgWindow(){
	hidePromptWindow("msgPromptWindow");
}
//提示信息并且有指示下一步操作
function operateMsg(msg){
	showPromptWindow("promptOperateWindow");
	$("#promptOperateTagId").html(msg);
}

function cancelOperateMsgWindow(){
	hidePromptWindow("promptOperateWindow");
	$("#promptOperateWindowConfirmBtn").unbind("click");
}

//显示提示窗
function showPromptWindow(windowId){
	$("#" + windowId).css({'display' : 'block', 'opacity' : 1});
}
//关闭提示窗
function hidePromptWindow(windowId){
	$("#" + windowId).css({'display' : 'block', 'opacity' : 0});
}

