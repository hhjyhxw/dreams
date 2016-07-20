<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑角色 - Powered By mall</title>
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
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $allChecked = $("#validateForm .allChecked");
	
	$allChecked.click( function() {
		var $this = $(this);
		var $thisCheckbox = $this.parent().parent().find("td :checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.attr("checked", false);
			$this.find(":checkbox").attr("checked", false);
		} else {
			$thisCheckbox.attr("checked", true);
			$this.find(":checkbox").attr("checked", true);
		}
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"name": "required"
		},
		messages: {
			"name": "请填写角色名称"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择管理权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
})
</script>
</head>
<body class="input role">
	<div class="bar">
		<#if id??>编辑角色<#else>添加角色</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="role!save.action" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						角色名称: 
					</th>
					<td>
						<input type="text" name="name" class="formText" value="${(name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="description" class="formTextarea">${(description)!}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="javascript:void(0);" class="allChecked" title="点击全选此类权限"><input type="checkbox" name="authoritysList" class="roleAuthorityList" value="ROLE_SYSTEM_MENU"<#if (!id?? || authorityList.contains("ROLE_SYSTEM_MENU"))!> checked</#if> />系统管理 </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="authoritysList" class="roleAuthorityList" value="ROLE_ADMIN_MANAGE"<#if (!id?? || authorityList.contains("ROLE_ADMIN_MANAGE"))!> checked</#if> />用户管理
						</label>
						<label>
							<input type="checkbox" name="authoritysList" class="roleAuthorityList" value="ROLE_ROLE_MANAGE"<#if (!id?? || authorityList.contains("ROLE_ROLE_MANAGE"))!> checked</#if> />角色管理
						</label>
					</td>
				</tr>
				
				<tr class="authorityList">
					<th>
						<a href="javascript:void(0);" class="allChecked" title="点击全选此类权限"><input type="checkbox" name="authoritysList" class="roleAuthorityList" value="ROLE_GOODS_MENU"<#if (!id?? || authorityList.contains("ROLE_GOODS_MENU"))!> checked</#if> />商品管理</a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="authoritysList" class="roleAuthorityList" value="ROLE_PUBLICGOODS_MANAGE"<#if (!id?? || authorityList.contains("ROLE_PUBLICGOODS_MANAGE"))!> checked</#if> />公共商品
						</label>
					</td>
				</tr>
				
				<#if (isSystem)!false>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: </b>系统内置角色不允许修改!</span>
						</td>
					</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>