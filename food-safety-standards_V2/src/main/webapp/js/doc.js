
var gnDocItemsStateUpdateDocIds = null;

var gnUpdateStateValue = null;

function docItems(tagState, p, view) {
	$.ajax({
		url : "docItems.html",
		type : "get",
		data : {
			tagState : tagState,
			p : p,
			view : view,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			$("#docItems").html(data);
		}
	});
}

function initTagStateSelect(state){
	var oSelect = document.getElementById("tagStatesSelectId");
	oSelect.options.length = 0; 
	for (var i = 0; i < gaTagStateValues.length; i++) {
		
		let eOption = document.createElement("option");
		eOption.text = gaTagStateTexts[i];
		eOption.value = gaTagStateValues[i];
		if(state == gaTagStateValues[i]){
			eOption.selected = true;
		}
		oSelect.add(eOption);
	}
}

function tagStatesFilter(view){
	var tagState = $("#tagStatesSelectId").val();
	if(tagState != ""){
		docItems(tagState, 1, view);
	}
}

//docItems页面文档状态为未打标状态，点击打标要把文档状态更新为打标中状态
function startMarkStateUpdate(docId, reqUrl){
	window.open(reqUrl);
	var docIds = [docId];
	gnDocItemsStateUpdateDocIds = docIds;
	gnUpdateStateValue = 1;
	docStateUpdate(docIds, 1, stateUpdateSuccess);
	
}

//状态修改成功后执行的方法
var stateUpdateSuccess = function(){
	var sStateHtml;
	if(gnUpdateStateValue == 1){
		sStateHtml = "<span class=\"text-warning\">打标中</span>";
	}
	if(gnUpdateStateValue == 3){
		sStateHtml = "<span class=\"text-review1\">一审中</span>";
	}
	if(gnUpdateStateValue == 4){
		sStateHtml = "<span class=\"text-review2\">二审中</span>";
	}
	if(gnUpdateStateValue == 5){
		sStateHtml = "<span class=\"text-success\">入库</span>";
	}
	for (var i = 0; i < gnDocItemsStateUpdateDocIds.length; i++) {
		$("#docStateTd_" + gnDocItemsStateUpdateDocIds[i]).html(sStateHtml);
	}
	gnDocItemsStateUpdateDocIds = null;
	gnUpdateStateValue = null;
}

function stateUpdate(updateState){
	var docIds = getSelectedValues("doccheckbox");
	if(docIds != null && docIds.length > 0){
		
		gnDocItemsStateUpdateDocIds = docIds;
		gnUpdateStateValue = updateState;
		docStateUpdate(docIds, updateState, stateUpdateSuccess);
	}
}


function antStartMarkStateUpdate(docId, reqUrl){
	window.open(reqUrl);
	var docIds = [docId];
	docStateUpdate(docIds, 1, antStateUpdateSuccess);
	
}

//状态修改成功后执行的方法
var antStateUpdateSuccess = function(){
	location.reload();
}

function antStateUpdate(docId, updateState){
	var docIds = [docId];
	docStateUpdate(docIds, updateState, antStateUpdateSuccess);
}

function selectDocType(activetype, type1, type2){
	$("#doctype_" + type1).attr("class", "");
	$("#doctype_" + type2).attr("class", "");
	$("#doctype_" + activetype).attr("class", "active");
	$("#docType").val(activetype);
}

function selectHtmlUrlType(activetype, type1, type2)
{
	$("#htmlUrltype_" + type1).attr("class", "");
	$("#htmlUrltype_" + type2).attr("class", "");
	$("#htmlUrltype_" + activetype).attr("class", "active");
	$("#htmlUrltype").val(activetype);
}

//----------文档导入-------
function checkUploadDoc() {
	var uploadfile = $("#fileUpload").val();
	if (uploadfile != null && uploadfile != "") {
		var start = uploadfile.lastIndexOf(".");
		var format = uploadfile.substring(start + 1, uploadfile.length);
		if (format == "pdf" || format == "zip" || format == "html" || format == "htm") {
			promptMsg("正在导入请等待...");
			return true;
		} else {
			promptMsg("文件格式错误。");
			return false;
		}
	} else {
		promptMsg("请选择导入的文件。");
		return false;
	}
}

function uploadDocument() {
	if (checkUploadDoc()) {
		upload();
	}
}
function upload() {
	var form = new FormData(document.getElementById("uploadDocumentForm"));
	$.ajax({
		url : "upload.html?_csrf=" + $("#csrf").val(),
		type : "post",
		data : form,
		processData : false,
		contentType : false,
		success : function(data) {
			var msg = JSON.parse(data);
			var repeatFileNames = msg.repeatFileNames;
			var type = msg.type;
			if (msg.repeatFileNames != "") {
				//关闭提示窗口
				closePromptMsgWindow();
				//显示操作提示窗口
				operateMsg("文档" + repeatFileNames + "已经存在是否继续导入？");
				$("#promptOperateWindowConfirmBtn").click(function() {
					repeatUpload(repeatFileNames, type);
				});
			} else {
				location.href = "docManage.html";
			}
		}
	});
}

function repeatUpload(repeatFileNames, type) {
	promptMsg("正在导入请等待...");
	$.ajax({
		url : "repeatUpload.html",
		type : "post",
		data : {
			repeatFileNames : repeatFileNames,
			type : type,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if (msg.success) {
				location.href = "docManage.html";
			} else {
				cancelOperateMsgWindow();
				promptMsg.html("导入失败");
			}
		}
	});
}
//------------excel导入----------------
function uploadExcel() {
	if (checkUploadUrlExcel()) {
		uploadUrlExcel();
	}
}

function checkUploadUrlExcel() {
	var excelFile = $("#excelFile").val();
	if (excelFile == "") {
		promptMsg("请选择导入的excel");
		return false;
	}
	var start = excelFile.lastIndexOf(".");
	var format = excelFile.substring(start + 1, excelFile.length);
	if ("xls" == format || "xlsx" == format) {
		promptMsg("正在导入请等待...");
		return true;
	} else {
		promptMsg("文件格式错误");
		return false;
	}
}

function uploadUrlExcel() {
	var form = new FormData(document.getElementById("uploadUrlExcelForm"));
	$.ajax({
		url : "uploadUrlExcel.html?_csrf=" + $("#csrf").val(),
		type : "post",
		data : form,
		processData : false,
		contentType : false,
		success : function(data) {
			var msg = JSON.parse(data);
			if (msg.success) {
				var repeatInfo = msg.repeatInfo;
				var repeatObjMsg = msg.repeatJsonObj;
				var type = msg.type;
				if (repeatInfo != "") {
					closePromptMsgWindow();
					//有重复的
					operateMsg(repeatInfo + "已经存在是否继续导入？");
					//添加继续导入按钮事件
					$("#promptOperateWindowConfirmBtn").click(function() {
						repeatUploadUrlExcel(repeatObjMsg, type);
					});
				} else {
					location.href = "docManage.html";
				}
			} else {
				promptMsg("文件导入失败");
			}
		}
	});
}

function repeatUploadUrlExcel(repeatObjMsg, type) {

	$.ajax({
		url : "repeatUploadUrlExcel.html",
		type : "post",
		data : {
			repeatObjMsg : repeatObjMsg,
			type : type,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if (msg.success) {
				location.href = "docManage.html";
			} else {
				cancelOperateMsgWindow();
				promptMsg("文件导入失败");
			}
		}
	});
}
//--------------------------网址添加----------------------------
function uploadHtmlUrl(){
	var standardNo = $("#uploadHtmlStandardNo").val();
	var reqUrl = $("#uploadHtmlReqUrl").val();
	if(validate(standardNo, reqUrl)){
		var type = $("#htmlUrltype").val();
		if(type != ""){
			$.ajax({
				url : "addHTMLUrl.html",
				type : "post",
				data : {
					standardNo : standardNo,
					reqUrl : reqUrl,
					type : type,
					isRepeat : false,
					_csrf : $("#csrf").val()
				},
				success : function(data) {
					var msg = JSON.parse(data);
					if(msg.success) {
						location.href = "docManage.html";
					} else {
						
						var oInfos = JSON.parse(msg.errorMsg);
						var stipInfo = oInfos.repeatInfo;
						var nType = oInfos.type;
						var aDocNo = oInfos.standardNo;
						var aUrl = oInfos.reqUrl;
						
						operateMsg(stipInfo + "已经存在是否继续导入？");
						//添加继续导入按钮事件
						$("#promptOperateWindowConfirmBtn").click(function() {
							continueUploadUrl(aDocNo, aUrl, nType);
						});
					}
				}
			});
		}
	}
}
function continueUploadUrl(standardNo, reqUrl, type)
{
	if(validate(standardNo, reqUrl)){
		$.ajax({
			url : "addHTMLUrl.html",
			type : "post",
			data : {
				standardNo : standardNo,
				reqUrl : reqUrl,
				type : type,
				isRepeat : true,
				_csrf : $("#csrf").val()
			},
			success : function(data) {
				var msg = JSON.parse(data);
				if(msg.success) {
					location.href = "docManage.html";
				} else {
					promptMsg("导入失败");
				}
			}
		});
	}
}

function validate(standardNo, reqUrl)
{
	if(standardNo == ""){
		$("#singleUploadTips").html("文档编号不能为空");
		return false;
	}
	if(reqUrl == "") {
		$("#singleUploadTips").html("网址不能为空");
		return false;
	}
	return true;
}
