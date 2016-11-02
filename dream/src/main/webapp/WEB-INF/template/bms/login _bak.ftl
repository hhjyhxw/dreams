<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>逐梦-后台登录</title>
<link href="${base}/template/webshop/css/dialog.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/webshop/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/webshop/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/base.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/admin.js"></script>
<script type="text/javascript">

// 登录页面若在框架内，则跳出框架
if (self != top) {
	top.location = self.location;
};
		
		
$().ready( function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isRememberUsername = $("#isRememberUsername");

	// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
	if(getCookie("adminUsername") != null) {
		$isRememberUsername.attr("checked", true);
		$username.val(getCookie("adminUsername"));
		$password.focus();
	} else {
		$isRememberUsername.attr("checked", false);
		$username.focus();
	}

	// 提交表单验证,记住登录用户名
	$loginForm.submit( function() {
		if ($username.val() == "") {
			$.dialog({type: "warn", content: "请输入您的用户名!", modal: true, autoCloseTime: 1500});
			return false;
		}
		if ($password.val() == "") {
			$.dialog({type: "warn", content: "请输入您的密码!", modal: true, autoCloseTime: 1500});
			return false;
		}
		if ($captcha.val() == "") {
			$.dialog({type: "warn", content: "请输入您的验证码!", modal: true, autoCloseTime: 1500});	
			return false;
		}
		/**
		if ($isRememberUsername.attr("checked") == true) {
			var expires = new Date();
			expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 7);
			setCookie("adminUsername", $username.val(), expires);
		} else {
			removeCookie("adminUsername");
		}
		*/
		
	});

	// 刷新验证码
	$captchaImage.click( function() {
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp;
		$captchaImage.attr("src", imageSrc);
	});
	
	
	
	<#if shiroLoginFailure??>
		<#if shiroLoginFailure == "org.apache.shiro.authc.ExcessiveAttemptsException">
			 $.dialog({type: "warn", content: "超过最大登陆次数，账号被临时锁定", modal: true, autoCloseTime: 1500});
		<#elseif shiroLoginFailure == "org.apache.shiro.authc.IncorrectCredentialsException">
		    $.dialog({type: "warn", content: "用户名或密码错误", modal: true, autoCloseTime: 1500});
		<#elseif shiroLoginFailure == "com.hyzy.mall.security.IncorrectCaptchaException">	
			$.dialog({type: "warn", content: "验证码错误!", modal: true, autoCloseTime: 15000});
		<#elseif shiroLoginFailure == "com.haojie.modules.security.LockAccountException">
			$.dialog({type: "warn", content: "账号被锁定，禁止登陆", modal: true, autoCloseTime: 1500});	
		<#elseif shiroLoginFailure == "org.apache.shiro.authc.AuthenticationException">	
		    $.dialog({type: "warn", content: "登陆异常!", modal: true, autoCloseTime: 1500});
		<#elseif shiroLoginFailure == "org.apache.shiro.authc.UnknownAccountException">	
		    <#---$.dialog({type: "warn", content: "用户名不存在!", modal: true, autoCloseTime: 1500});-->
		    $.dialog({type: "warn", content: "用户名或密码错误", modal: true, autoCloseTime: 1500});
		<#else>
			$.dialog({type: "warn", content: "用户名或密码错误${shiroLoginFailure}", modal: true, autoCloseTime: 1500});	
		</#if>
	</#if>
		
		
	
});
</script>
</head>

<body>
<div class="yh_login">
	<div class="login_arre">
    	<div class="login_content">
        	<div class="login_logo"></div>
            <div class="tianxie fz14">
            
                <form id="loginForm" action="${base}/bms/login.action" method="post">
            	<table width="100%" border="0">
                  <tr>
                    <td width="22%">用户名：</td>
                    <td width="78%"><input id="username" name="username" class="xie_txt" value=""/></td>
                  </tr>
                  <tr>
                    <td>密&nbsp;&nbsp;&nbsp;码：</td>
                    <td><input type="password" id="password" name="password" value="" class="xie_txt"/></td>
                  </tr>
                  <tr>
                    <td>验证码：</td>
                    <td>
                    <input type="text" id="captcha" name="j_captcha" value="" class="xie_txt fl captcha" style="width:112px;" />
                   	<img id="captchaImage" style="display:inline-block; vertical-align:middle; margin-left:5px;" src="${base}/captcha.jpg" alt="换一张" />
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" class="btn_login" value="" hidefocus/></td>
                  </tr>
                </table>
                </form>
            </div>
      </div>
    </div>
</div>
</body>
</html>
