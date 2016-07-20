<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>修改个人信息 - Powered By mall</title>
<meta name="Author" content="mall Team" />
<meta name="Copyright" content="mall" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/bms/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/bms/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/base.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	var $isAccountEnabledFlag =	$("#isAccountEnabledFlag");
	
	var $tab = $("#tab");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			<#if !id??>
				"username": {
					required: true,
					username: true,
					minlength: 2,
					maxlength: 	 20,
					remote: "admin!checkUsername.action"
				},
			</#if>
			"password": {
				<#if !id??>
					required: true,
				</#if>
				minlength: 4,
				maxlength: 	 20
			},
			"rePassword": {
				<#if !id??>
					required: true,
				</#if>
				equalTo: "#password"
			},
			"email": {
				required: true,
				email: true
			}
		},
		messages: {
		<#if !id??>
				"username": {
					required: "请填写用户名",
					username: "用户名只允许包含中文、英文、数字和下划线",
					minlength: "用户名必须大于等于2",
					maxlength: 	 "用户名必须小于等于20",
					remote: "用户名已存在"
				},
			</#if>
			"password": {
				<#if !id??>
					required: "请填写密码",
				</#if>
				minlength: "密码必须大于等于4",
				maxlength: 	 "密码必须小于等于20"
			},
			"rePassword": {
				<#if !id??>
					required: "请填写重复密码",
				</#if>
				equalTo: "两次密码输入不一致"
			},
			"email": {
				required: "请填写E-mail",
				email: "E-mail格式不正确"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		修改个人信息
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="admin!update.action" method="post">
			<input type="hidden" name="id" value="${id}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="个人资料" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${(username)!}
						<input type="hidden" name="username" class="formText" value="${(username)!}" />
					</td>
				</tr>
				<tr>
					<th>
						密 码: 
					</th>
					<td>
						<input type="hidden" name="oldPassword" id="oldPassword" class="formText" value="${password}"/>
						<input type="password" name="password" id="password" class="formText" title="密码长度只允许在4-20之间" />
						<#if !id??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						重复密码: 
					</th>
					<td>
						<input type="password" name="rePassword" class="formText" />
						<#if !id??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						E-mail: 
					</th>
					<td>
						<input type="text" name="email" class="formText" value="${(email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				
				<tr class="roleList">
					<th>
						管理角色: 
					</th>
					<td>
						<#list roleList as role>
							<label>
								${role.name}
							</label>
						</#list>
					</td>
				</tr>
				
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<#if isAccountEnabled>
							启用
						<#else>
							停用
						</#if>
					</td>
				</tr>
				
				<tr>
					<th>&nbsp;</th>
					<td>
						<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						部门: 
					</th>
					<td>
						<input type="text" name="department" class="formText" value="${(department)!}" />
					</td>
				</tr>
				<tr>
					<th>
						姓名: 
					</th>
					<td>
						<input type="text" name="name" class="formText" value="${(name)!}" />
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>