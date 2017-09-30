
function createAddAccountView(){
	
	showActionAccountView("新建用户");
	$("#accountSaveBtn").click(function(){
		var name = $("#create_uname").val();
		var password = $("#create_password").val();
		var obj = document.getElementsByName("role");
		var role = "";
		for(k in obj){
			if(obj[k].checked){
				if(role == ""){
					role = role + obj[k].value;
				} else {
					role = role + "," + obj[k].value;
				}
			}
		}
		if(name != "" && password != "" && role != ""){
			createAccount(name, password, role);
			$("#accountModal").css({'display':'none', 'opacity':0});
		} else {
			alert("请把账号信息填写完整。");
			return;
		}
		
	});
}

function showActionAccountView(title){
	
	$("#accountModalTitle").html(title);
	
	$("#accountModal").css({'display':'block', 'opacity':1});
	
	$("#accountCancelBtn").click(function(){
		$("#accountModal").css({'display':'none', 'opacity':0});
		
	});
	$("#accountCloseBtn").click(function(){
		$("#accountModal").css({'display':'none', 'opacity':0});
		
	});
}

function createAccount(name, password, role){
	$.ajax({
		url : "createAccount.html",
		type : "post",
		data : {
			name : name,
			password : password,
			role : role,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if(msg.success){
				location.href = "account.html";
			} else{
				promiseCommonModal("用户添加失败");
			}
		}
	});
}

function removeCreatedTr(){
	var createdTr = document.getElementById("newCreateedTr");
	if(createdTr != null){
		createdTr.parentNode.removeChild(createdTr);
	}
}

function modifyAccount(index){
	var password = $("#accountTr" + index).find("td:eq(2)").html();
	var passwordHtml = '<input id="password'+index+'" class="form-control" value="'+password+'" type="text">'
	$("#accountTr" + index).find("td:eq(2)").html(passwordHtml);
	
	var roles = $("#accountTr" + index).find("td:eq(4)").html();

	var roleArray = roles.split("，");
	var adminCheckbox = '<label class="checkbox-inline"><input type="checkbox" name="role'+index+'" value="ROLE_ADMIN">管理员</label>';
	var checkCheckbox = '<label class="checkbox-inline"><input type="checkbox" name="role'+index+'" value="ROLE_CHECK">审核员</label>';
	var annotateCheckbox = '<label class="checkbox-inline"><input type="checkbox" name="role'+index+'" value="ROLE_ANNOTATE">打标员</label>';
	for (var i = 0; i < roleArray.length; i++) {
		if(roleArray[i] == "管理员"){
			adminCheckbox = '<label class="checkbox-inline"><input type="checkbox" checked="checked" name="role'+index+'" value="ROLE_ADMIN">管理员</label>';
		}
		if(roleArray[i] == "审核员"){
			checkCheckbox = '<label class="checkbox-inline"><input type="checkbox" checked="checked" name="role'+index+'" value="ROLE_CHECK">审核员</label>';
		}
		if(roleArray[i] == "打标员"){
			annotateCheckbox = '<label class="checkbox-inline"><input type="checkbox" checked="checked" name="role'+index+'" value="ROLE_ANNOTATE">打标员</label>';
		}
	}
	var roleHtml = adminCheckbox + checkCheckbox + annotateCheckbox;
	$("#accountTr" + index).find("td:eq(4)").html(roleHtml);
	
	//修改前的角色
	var originalRoles = getRoles(index);
	
	var caozuoHTml = '<a href="javascript:saveModify('+index+', \''+roles+'\', \''+originalRoles+'\', \''+password+'\')" class="t_m"><i class="fa fa-floppy-o"></i><span>保存</span>'
						+ '</a><a href="javascript:cancelModify('+index+', \''+roles+'\', \''+password+'\')" class="t_m">'
						+ '<i class="fa fa-trash"></i><span>取消</span></a>';
	
	$("#accountTr" + index).find("td:eq(7)").html(caozuoHTml);
	
}


function getRoles(index){
	var obj = document.getElementsByName("role"+ index);
	var role = "";
	for(k in obj){
		if(obj[k].checked)
		if(role == ""){
			role = role + obj[k].value;
		} else {
			role = role + "," + obj[k].value;
		}
	}
	return role;
}
function saveModify(index, roles, originalRoles, originalPassword){
	var role = getRoles(index);
	var password = $("#password" + index).val();

	if(role != "" && password != ""){
		if(originalRoles == role && originalPassword == password){
			cancelModify(index, roles, password);
		} else {
			$.ajax({
				url : "modifyAccount.html",
				type : "post",
				data : {
					uid : index,
					password : password,
					role : role,
					_csrf : $("#csrf").val()
				},
				success : function(data) {
					var msg = JSON.parse(data);
					if(msg.success) {
						location.href = "account.html";
					} else {
						promiseCommonModal("修改用户信息出错");
					}
				}
			});
		}
	}
}

function cancelModify(index, roles, password){
	$("#accountTr" + index).find("td:eq(2)").html(password);
	$("#accountTr" + index).find("td:eq(4)").html(roles);
	
	var mntUser = $("#accountTr" + index).find("td:eq(1)").html();
	
	var caozuoHtml = '<a href="task.html" class="t_m"><i class="fa fa-tasks"></i><span>分配</span></a>'
		+ '<a href="accountMonitor.html?mntUser='+mntUser+'" class="t_m"><i class="fa fa-cog"></i><span>管理</span></a>'
		+ '<a href="javascript:modifyAccount('+index+')" class="t_m"><i class="fa fa-pencil"></i><span>修改</span></a>';
	$("#accountTr" + index).find("td:eq(7)").html(caozuoHtml);
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function accountItems(role, inputId){
	$.ajax({
		url : "accountItems.html",
		type : "post",
		data : {
			role : role,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			$("#actionInputId").val(inputId);
			$("#accountItems").html(data);
		}
	});
}

function selectedAccount(objBtn){
	var users = getSelectedValues("accountSelectCbx");
	if(users.length == 1){
		var inputId = $("#actionInputId").val();
		$("#" + inputId).val(users[0]);
	}
}

function onlyOneAccountSelect(chkObj){
	
	var checkboxs = document.getElementsByName("accountSelectCbx");
	for (var i = 0; i < checkboxs.length; i++) {
		var e = checkboxs[i];
		if (e.checked) {
			e.checked = false;
		}
	}
	chkObj.checked = true;	
}