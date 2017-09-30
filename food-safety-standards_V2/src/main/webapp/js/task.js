function docSelect(){
	$.ajax({
		url : "docSelect.html",
		type : "post",
		data : {
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			$("#docSelect").html(data);
		}
	});
}

function comfirmedDocsSelected(){
	var checkboxs = document.getElementsByName("docSelectCbx");
	var num = 0;
	for (var i = 0; i < checkboxs.length; i++) {
		var e = checkboxs[i];
		if (e.checked) {
			var infos = e.value;
			var docInfos = infos.split(",");
			if(document.getElementById(docInfos[1]) == null){
				var trHtml = '<tr id="'+docInfos[1]+'"><td>'+docInfos[0]+'</td>'
				+ '<td>'+docInfos[3]+'</td>'
				+ '<td>'+docInfos[2]+'</td>'
				+ '<td><a href="javascript:delSelectedDoc(\''+docInfos[1]+'\')" class="t_m"><i class="fa fa-trash"></i></a></td>'
				+ '</tr>';
				$("#seletedDocsTb").prepend(trHtml);
				num = num + 1;
			}
		}
	}
	document.getElementById("comfirmedDoc").innerHTML = num;
}

function saveTasks(){
	var docIds = [];
	var trNums = $("#seletedDocsTb tr").length;
	for (var i = 0; i < trNums; i++) {
		var docId = $("#seletedDocsTb tr").eq(i).attr("id");
		docIds.push(docId);
		
	}
	if(docIds.length > 0){
		var annotateUser = $("#selectAntUser").val();
		var firstCheckUser = $("#selectFcheckUser").val();
		var secondCheckUser = $("#selectScheckUser").val();
		if(annotateUser != "" && firstCheckUser != "" && secondCheckUser != ""){
			$.ajax({
				url : "saveTasks.html",
				type : "post",
				data : {
					annotateUser : annotateUser,
					firstCheckUser : firstCheckUser,
					secondCheckUser : secondCheckUser,
					docIds : docIds,
					_csrf : $("#csrf").val()
				},
				success : function(data) {
					var oMsg = JSON.parse(data);
					if(oMsg.success){
						promiseCommonModal("任务分配成功");
					} else{
						promiseCommonModal("编号" + oMsg.errorMsg + "的文档任务分配出错");
					}
				}
			});
		} else {
			if(annotateUser == ""){
				promiseCommonModal("请选择打标员");
				return;
			}
			if(firstCheckUser == ""){
				promiseCommonModal("请选择一级审核员");
				return;
			}
			if(secondCheckUser == ""){
				promiseCommonModal("请选择二级审核员");
				return;
			}
		}
	} else {
		promiseCommonModal("请选择文档");
	}
}

function filterDoc(docNo){
	var country = $("#selectedCountry").val();
	$.ajax({
		url : "docSelect.html",
		type : "post",
		data : {
			country : country,
			docNo : docNo,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			$("#docSelect").html(data);
		}
	});
}

function filterDocByDocNo(country){
	var docNo = $("#searchedDocNo").val();
	pageDocSelected(country, docNo, 1);
}

function pageDocSelected(country, docNo, p){
	$.ajax({
		url : "docSelect.html",
		type : "post",
		data : {
			country : country,
			docNo : docNo,
			p: p,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			$("#docSelect").html(data);
		}
	});
}

function choiceAll(){
	selectAll('docSelectAll', 'docSelectCbx');
	selectedNum();
}

function selectedNum(){
	var num = getSelectedValues("docSelectCbx").length;
	document.getElementById("selectedNum").innerHTML = num;
}

function delSelectedDoc(docId){
	$.ajax({
		url : "delDocTasks.html",
		type : "post",
		data : {
			docId : docId,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if(!msg.success) {
				alert("删除任务分配出错");
			} else {
				var objTr = document.getElementById(docId);
				objTr.parentNode.removeChild(objTr);
			}
		}
	});
}
