//翻页
function indexReplace(currentPage){
	location.href = "docReplace.html?p=" + currentPage;
}
//显示修改样式
function modify(index, id){
	
	showActionReplaceView("修改文档代替关系");	
	
	$("#saveModifyBtn").click(function(){
		$("#myModal1").css({'display':'none', 'opacity':0});
		save(index, id);
	});
	
	
	var row = $("#content tr:gt(0):eq(" + index + ")");
	var tds = row.find("td");
	//新文档编号
	$("#md_newDocNo").val(tds.eq(0).text());
	//旧文档编号
	$("#md_oldDocNo").val(tds.eq(1).text());
	//代替关系
	var relation = tds.eq(3).text();
	if(relation == "完全代替"){
		relation = 1;
	} else {
		relation = 0;
	}
	$("#md_relation").val(relation);
	//备注
	$("#md_remark").val(tds.eq(4).text());
}

function showActionReplaceView(title){
	$("#myModal1").css({'display':'block', 'opacity':1});
	
	$("#replaceActionTitle").html(title);
	
	$("#cancelModifyBtn").click(function(){
		$("#myModal1").css({'display':'none', 'opacity':0});
		$("#md_newDocNo").val("");
		$("#md_oldDocNo").val("");
		$("#md_relation").val(1);
		$("#md_remark").val("");
	});
	$("#closeModifyBtn").click(function(){
		$("#myModal1").css({'display':'none', 'opacity':0});
		$("#md_newDocNo").val("");
		$("#md_oldDocNo").val("");		
		$("#md_relation").val(1);
		$("#md_remark").val("");
	});
}

//保存修改结果
function save(index, id){
	var data = constructData(id);
	if(data != null){
		$.ajax({
			url: 'alterReplace.html',
			type: 'post',
			data: data,
			success:renew(index, data),
		});
	}
}


function constructData(id){
	var newDoc = $("#md_newDocNo").val();
	//旧文档编号
	var oldDoc = $("#md_oldDocNo").val();
	//代替关系
	var relation = $("#md_relation").val();
	//备注
	var remark = $("#md_remark").val();
	
	if(newDoc == null || newDoc.length == 0){
		promiseCommonModal("请填写新文档编号");
		return null;
	}
	if(oldDoc == null || oldDoc.length == 0){
		promiseCommonModal("请填写旧文档编号");
		return null;
	}
	var data = {
		'id': id,	
		'newDoc' : newDoc,
		'oldDoc' : oldDoc,
		'relation' : relation,
		'remark' : remark,
		_csrf : $("#csrf").val(),
	};
	
	return data;
}



//恢复修改后样式
function renew(index, data){
	var newDocNo =data.newDoc; 
	var oldDocNo = data.oldDoc; 
	var relation = data.relation;
	var remark = data.remark;
	
	var row = $("#content tr:gt(0):eq("+index+")");
	var tds = row.find("td");
	tds.eq(0).html(newDocNo);
	tds.eq(1).html(oldDocNo);
	if(relation == 1){
		relation = "完全代替";
	} else {
		relation = "部分代替";
	}
	tds.eq(3).html(relation);
	tds.eq(4).html(remark);
}
function del(id){
	if(id == null || id.length == 0)
		return false;
	
	myModal("#myModal",deleting,id);
}
//执行删除操作
function deleting(id){
	$.ajax({
		url: 'delReplace.html',
		type: 'post',
		data: {
			id: id,
			_csrf : $("#csrf").val(),
		},
		success: function(data){
			if(data != "error"){
				window.location.reload();
			} else{
				alert("操作失败");
			}
		},
	})
}
//为模态框按钮添加事件
function myModal(element, callback, parameter){
	$(element).css({'display':'block', 'opacity':1});
	
	$("#myModal > div > div > div.modal-header > button").click(function(){
		$("#myModal").css({'display':'none', 'opacity':0});
		return false;
	});
	
	$("#myModal > div > div > div.modal-footer > button.btn.btn-default").click(function(){
		$("#myModal").css({'display':'none', 'opacity':0});
		return false;
	});
	
	$("#myModal > div > div > div.modal-footer > button.btn.btn-primary").click(function(){
		$("#myModal").css({'display':'none', 'opacity':0});
		callback(parameter);
		return true;
	});
}

function addReplace(){
	
	showActionReplaceView("添加文档代替关系");
	$("#saveModifyBtn").click(function(){
		$("#myModal1").css({'display':'none', 'opacity':0});
		saveAddReplace();
	});
}


function saveAddReplace(){
	
	var data = constructData(-1);
	if(data != null){
		$.ajax({
			url: 'saveAddReplace.html',
			type: 'post',
			data: data,
			success: function(data) {
				if(data == "success"){
					location.href = "docReplace.html";
				}
			}
		});
	}
}

//-----编辑保存---replace
function createAddReplaceTr(){
	var id = $("#replace_tb_id").find("tr").eq(0).attr("id");
	id = id + "c1";
	var str = '<tr id="'+ id + '">'
		+ '<td><input class="form-control" type="text" name="newDoc"></td>'
		+ '<td><input class="form-control" type="text" name="relation"></td>'
		+ '<td><input type="hidden" name="rpcId" value="-1"><button class="btn btn-default btn-sm" onclick="cancleAddReplace(\''+id+'\')">'
		+ '<span class="glyphicon glyphicon-minus"> </span></button></td></tr>'
		+ '<tr id="remark'+ id + '"><td colspan="2" class="nobord"><textarea name="remark" class="form-control"  rows="2" placeholder="备注"></textarea></td></tr>';
	$("#replace_tb_id").prepend(str);
	sortReplaceName();
}

function cancleAddReplace(id){
	$("#" + id).remove();
	$("#remark" + id).remove();
	sortReplaceName();
}

function sortReplaceName(){
	var aTrs = $("#replace_tb_id").find("tr");
	var index = 0;
	for (var i = 0; i < aTrs.length; i++) {
		var oTr = aTrs[i];
		var newDocName = "docReplaces["+ index +"].newDoc";
		$(oTr).find("td").eq(0).find("input").eq(1).attr("name", newDocName);
		
		var oldDocName = "docReplaces["+ index +"].oldDoc";
		$(oTr).find("td").eq(0).find("input").eq(0).attr("name", oldDocName);
		
		var relationName = "docReplaces["+ index +"].relation";
		$(oTr).find("td").eq(1).find("input").attr("name", relationName);
		var idName = "docReplaces["+ index +"].id";
		$(oTr).find("td").eq(2).find("input").attr("name", idName);
		
		var remarkName = "docReplaces["+ index +"].remark";
		$(oTr).next().find("td textarea").attr("name", remarkName);
		i++;
		index++;
	}
}

function deleteDocReplace(id){
	operateMsg("确定要删除本条记录吗？");
	$("#promptOperateWindowConfirmBtn").click(function() {
		$.ajax({
			url: 'delReplace.html',
			type: 'post',
			data: {
				id: id,
				_csrf : $("#csrf").val(),
			},
			success: function(data){
				if(data != "error"){
					cancleAddReplace(id);
				} else{
					alert("操作失败");
				}
			},
		})
	});
}