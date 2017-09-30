//翻页
function indexReplace(currentPage){
	location.href = "docSynonym.html?p=" + currentPage;
}
//显示修改样式
function modify(index, id){
	var row = $("#content tr:gt(0):eq("+index+")");
	var tds = row.find("td");
	var text = "<td><input class='form-control' value='"+tds.eq(0).text()+"' type='text'></td>"+
		"<td><input class='form-control' value='"+tds.eq(1).text()+"' type='text'></td>"+
		"<td>"+tds.eq(2).text()+"</td>"+"<td>"+tds.eq(3).text()+"</td>"+
		"<td><a href='javascript:save("+index+", "+id+")' class='t_m'><i class='fa fa-floppy-o'></i><span>保存</span></a><a href='javascript:cancelModify("+index+", "+id+")' class='t_m'><i class='fa fa-trash'></i><span>取消</span></a></td>";
		
	row.html(text);
}

function cancelModify(index, id){
	var row = $("#content tr:gt(0):eq("+index+")");
	var tds = row.find("td");
	
	var relation = "";
	if(tds.eq(2).find("select").val() == 0){
		relation = "部分代替";
	}else if(tds.eq(2).find("select").val() == 1){
		relation = "完全代替";
	}
	
	var text = "<td>"+tds.eq(0).find("input").val()+"</td><td>"+tds.eq(1).find("input").val()+"</td>"+
	"<td>"+tds.eq(2).text()+"</td>"+
	"<td>"+tds.eq(3).text()+"</td>"+	
	"<td><a href='javascript:modify("+index+", "+id+")' class='t_m'><i class='fa fa-pencil-square-o'></i><span>修改</span></a><a href='javascript:del("+id+")' class='t_m'><i class='fa fa-trash'></i><span>删除</span></a></td>";
	row.html(text);
}

//保存修改结果
function save(index, id){
	
	var row = $("#content tr:gt(0):eq("+index+")");
	var tds = row.find("td");
	var data = {
		'id': id,	
		'mainWord':tds.eq(0).find("input").val(),
		'synWord':tds.eq(1).find("input").val(),
		'username':tds.eq(2).text(),
		'date':tds.eq(3).text(),
		_csrf : $("#csrf").val(),
	};
	
	console.log(data.username);
	if(data.mainWord == null || data.mainWord.length == 0){
		alert("请输入同义词1");
		return;
	}
	if(data.synWord == null || data.synWord.length == 0){
		alert("请输入同义词2");
		return;
	}
	
	var url = "";
	if(data.id > 0){
		url = "alterSynonym.html";
	}else{
		url = "addSynonym.html";
	}
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		success:function(){
			window.location.reload();
		},
	});
}

function del(id){
	if(id == null || id.length == 0)
		return false;
	
	myModal("#myModal",deleting,id);
}
//执行删除操作
function deleting(id){
	$.ajax({
		url: 'delSynonym.html',
		type: 'post',
		data: {
			'id': id,
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

function add(){
	var table = $("#content");
	
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() < 10 ? "0"+date.getMonth() : date.getMonth();
	var day = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var hour = date.getHours() < 10 ? "0"+date.getHours() : date.getHours();
	var minute = date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes();
	var second = date.getSeconds() < 10 ? "0"+date.getSeconds() : date.getSeconds();
	var currentTime = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	
	var text = "<tr id='"+currentTime+"'><td><input class='form-control' value='' type='text'></td>"+
		"<td><input class='form-control' value='' type='text'></td>"+
		"<td>"+$("#username").val()+"</td>"+"<td>"+currentTime+"</td>"+
		"<td><a href='javascript:save("+0+", "+0+")' class='t_m'><i class='fa fa-floppy-o'></i><span>保存</span></a><a href=\"javascript:cancelAdd('"+currentTime+"')\" class='t_m'><i class='fa fa-trash'></i><span>取消</span></a></td></tr>";

	table.prepend(text);
}

function cancelAdd(id){
	var trobj = document.getElementById(id);
	trobj.parentNode.removeChild(trobj);
}