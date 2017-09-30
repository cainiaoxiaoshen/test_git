function delAnnotate(docId){
	var antIds = getSelectedValues("antcheckbox");
	if(antIds != null && antIds.length > 0){
		operateMsg("确定要删除所选的记录吗？");
		$("#promptOperateWindowConfirmBtn").click(function() {
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					docId : docId,
					antIds : antIds,
					_csrf : $("#csrf").val()
				},
				success : function(data) {
					var msg = JSON.parse(data);
					if(msg.success){
						location.href = "annotate.html?docId=" + docId;
					} else {
						cancelOperateMsgWindow();
						promptMsg(msg.errorMsg + "删除出错");
					}
				}
			});
		});
	}
}

function getAnnotateByDate(docId) {
	var date = $("#annotateDate").val();
	if(date != ""){
		location.href = "annotate.html?docId=" + docId + "&date=" + date;
	}
}

//type 代表是哪个属性
function modifyAnnotate(antId, text, type, docId) {
	var tdHtml = "<input class=\"form-control\" id=\"modifyedTd_" + antId + type + "\" type=\"text\" value=\"" 
				+ text + "\" onblur=\"doModify('" + docId + "', " + antId + ", '" + text + "', " + type + ")\">";
	$("#" + antId + "_" + type).html(tdHtml);
}

function addClick(docId)
{
	var trs = $("#antResultTbody").children("tr");
	for (var i = 0; i < trs.length; i++) {
		var tds = trs.eq(i).find("td");
		for (var j = 1; j < 22; j++) {
			tds.eq(j).dblclick(function () {
				var text = $(this).html();
				var antId = $(this).attr("antId");
				var antType = $(this).attr("antType");
				var tdHtml = "<input class=\"form-control\" id=\"modifyedTd_" + antId + antType + "\" type=\"text\" value=\"" 
					+ text + "\" onblur=\"doModify('" + docId + "', " + antId + ", '" + text + "', " + antType + ")\">";
				$(this).html(tdHtml);
				$(this).unbind("dblclick");//td内容设置成可编辑状态后，解除点击事件
				$("#modifyedTd_" + antId + antType).focus();
			});
		}
	}
}

function doModify(docId, antId, text, type) {
	
	var oInput = $("#modifyedTd_" + antId + type);
	var modifyText = oInput.val();
	if(text != modifyText){
		$.ajax({
			url : "update.html",
			type : "post",
			data : {
				docId : docId,
				antId : antId,
				text : modifyText,
				type : type,
				_csrf : $("#csrf").val()
			},
			success : function(data) {
				var msg = JSON.parse(data);
				if(msg.success) {
					renewTdHtml(oInput, antId, type, modifyText, docId);
				} else {
					renewTdHtml(oInput, antId, type, text, docId);
					promptMsg(msg.errorMsg);
				}
			}
		});
	} else {
		renewTdHtml(oInput, antId, type, modifyText, docId);
	}
}

function renewTdHtml(oInput, antId, type, text, docId) {
	var oTd = oInput.parent();
	oTd.attr("antId", antId);
	oTd.attr("antType", type);
	oTd.html(text);
	oTd.dblclick(function () {
		var sSpanText = $(this).html();
		var nAntId = $(this).attr("antId");
		var nAntType = $(this).attr("antType");
		var tdHtml = "<input class=\"form-control\" id=\"modifyedTd_" + nAntId + nAntType + "\" type=\"text\" value=\"" 
			+ sSpanText + "\" onblur=\"doModify('" + docId + "', " + nAntId + ", '" + sSpanText + "', " + nAntType + ")\">";
		$(this).html(tdHtml);
		$(this).unbind("dblclick");//td内容设置成可编辑状态后，解除点击事件
		$("#modifyedTd_" + nAntId + nAntType).focus();
	});
}