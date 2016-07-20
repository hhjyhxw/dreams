<#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By mall</title>
<meta name="Author" content="mall Team" />
<meta name="Copyright" content="mall" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/bms/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/bms/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="body">
		<@shiro.hasAnyPermissions name="ROLE_GOODS_MENU">
			<dl>
				<dt>
					<span>商品管理</span>
				</dt>
				<@shiro.hasPermission name="ROLE_PUBLICGOODS_MANAGE">
					<dd>
						<a href="#" target="mainFrame">公共商品</a>
					</dd>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="ROLE_PUBLICGOODS_MANAGE">
					<dd>
						<a href="#" target="mainFrame">自营商品</a>
					</dd>
				</@shiro.hasPermission>
				
			</dl>
				
		</@shiro.hasAnyPermissions>	
	</div>
</body>
</html>