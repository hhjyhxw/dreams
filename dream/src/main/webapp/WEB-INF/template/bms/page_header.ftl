<#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>海韵之友管理中心 - Powered By mall</title>
<meta name="Author" content="mall Team" />
<meta name="Copyright" content="mall" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/bms/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/bms/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/base.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $menuItem = $("#menu .menuItem");
	var $previousMenuItem;
	$menuItem.click( function() {
		var $this = $(this);
		if ($previousMenuItem != null) {
			$previousMenuItem.removeClass("current");
		}
		$previousMenuItem = $this;
		$this.addClass("current");
	})

})
</script>
</head>
<body class="header">
	<div class="body">
		<div class="bodyLeft">
			<div class="logo"></div>
		</div>
		<div class="bodyRight">
			<div class="link">
				<span class="welcome">
					<strong><@shiro.principal/><#if supplier??>(${supplier.companyName})</#if></strong>&nbsp;您好!&nbsp;
				</span>
				<a href="page!index.action" target="mainFrame">后台首页</a>
			</div>
			<div id="menu" class="menu">
				<ul>
					<@shiro.hasPermission name="ROLE_SYSTEM_MENU">
					<li class="menuItem">
						<a href="menu!sys.action" target="menuFrame" hidefocus>系统管理</a>
					</li>
					 </@shiro.hasPermission>
					 <@shiro.hasPermission name="ROLE_GOODS_MENU">
					<li class="menuItem">
						<a href="menu!goods.action" target="menuFrame" hidefocus>商品管理</a>
					</li>
					</@shiro.hasPermission>
	            </ul>
	            <div class="info">
					<a class="profile" href="admin!input.action?isEdit=true" target="mainFrame">个人资料</a>
					<a class="logout" href="${base}/bms/logout" target="_top">退出</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>