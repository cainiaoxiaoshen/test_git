<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>登录-标准项目</title>
<jsp:include page="resource.jsp"></jsp:include>
<script src="<c:url value="/js/rsa/Barrett.js"></c:url>"></script>
<script src="<c:url value="/js/rsa/BigInt.js"></c:url>"></script>
<script src="<c:url value="/js/rsa/jQuery.md5.js"></c:url>"></script>
<script src="<c:url value="/js/rsa/RSA.js"></c:url>"></script>
</head>
<body>
	<div class="wrapper-login">
		<div class="login">
			<div class="row text-center hd-h1">
				<div class="col-md-12">
					<h2>全球食品安全标准与技术法规动态比对数据库 - 数据打标平台</h2>
				</div>
			</div>
			<div class="row cont-h1">
				<div
					class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
					<form role="form" action="j_spring_security_check" method="post" onsubmit="return loginValidate();">
						
						<c:if test="${errorMsg != null}">
						<p class="text-danger error"><i class="fa fa-exclamation-triangle"></i>${errorMsg }</p>
						</c:if>
						
						<div class="form-group input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" class="form-control" placeholder="用户名" name="j_username" id="username"/>
						</div>
						<div class="form-group input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" placeholder="密码" name="j_password" id="password"/>
							<input type="password" class="form-control left" id="encryPwd" name="encryPwd" hidden="hidden" style="display: none;">
						</div>
						<div class="form-group">
							<label class="checkbox-inline"> <input type="checkbox" checked="checked" name="_spring_security_remember_me"/>
								记住我
							</label>

						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<button class="btn btn-primary btn-block" type="submit">登录</button>

					</form>

				</div>

			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function loginValidate(){
		var password = $("#password").val();
		var username = $("#username").val();
		if(password != "" && username != ""){
			//encryptedPassword(password);
			return true;
		}
		return false;
	}
	
	function encryptedPassword(password) {
        var pass = password;
        var passlength = pass.length;
        var Exponent = "@ViewBag.strPublicKeyExponent";
        var Modulus = "@ViewBag.strPublicKeyModulus";
        if (Modulus.length > 0 && Exponent.length > 0) {
        	
        	/*
            setMaxDigits(129);
            var key = new RSAKeyPair(Exponent, "", Modulus);
            var pwdRtn = encryptedString(key, pass);
            */
            setMaxDigits(129);
            var key = new RSAKeyPair("abcdefg", "", "uuuuuuuuu");
            //var pwdMD5Twice = $.md5($.md5($("#txtPassword").attr("value")));
            var pwdRtn = encryptedString(key, pass);
            
            alert(pwdRtn);
            $('#encryPwd').attr("value", pwdRtn);
            /*
            pass = "";
            for (var i = 0; i < passlength; i++) {
                pass = pass + "*";
            }
            $('#password').val(pass);
            */
        }
    }
</script>
</html>
