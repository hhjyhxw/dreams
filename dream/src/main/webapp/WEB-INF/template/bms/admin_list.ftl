<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理员列表 - Powered By mall</title>
<meta name="Author" content="mall Team" />
<meta name="Copyright" content="mall" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/bms/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/bms/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/base.js"></script>
<script type="text/javascript" src="${base}/template/bms/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		管理员列表&nbsp;总记录数: ${pager.totalCount} (共${pager.totalPages}页)
	</div>
	<div class="body">
		<form id="listForm" action="admin!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='admin!input.action'" value="添加管理员" hidefocus />
				&nbsp;&nbsp;
				用户名：
				<input type="text" name="filter_LIKES_username" value="${Parameters['filter_LIKES_username']!}"/>
				&nbsp;&nbsp;
				姓名：
				<input type="text" name="filter_LIKES_name" value="${Parameters['filter_LIKES_name']!}"/>
				&nbsp;&nbsp;
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="email" hidefocus>E-mail</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="roleList" hidefocus>所属角色</a>
					</th>
					<th>
						<a href="#" class="sort" name="department" hidefocus>所属部门</a>
					</th>
					<th>
						<a href="#" class="sort" name="loginDate" hidefocus>最后登录时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="loginIp" hidefocus>最后登录IP</a>
					</th>
					<th>
						<span>状态</span>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as admin>
					<tr>
						<td>
							<input type="checkbox" <#if admin.roleList.contains(superRole)> disabled title="超级管理员不允许删除!"<#else> name="ids" value="${admin.id}"</#if>  />
						</td>
						<td>
							${admin.username}
						</td>
						<td>
							${admin.email}
						</td>
						<td>
							${(admin.name)!}
						</td>
						<td>
							<#list admin.roleList as role>
								${role.name}&nbsp;&nbsp;
							</#list>
						</td>
						<td>
							${(admin.department)!}
						</td>
						<td>
							<#if admin.loginDate??>
								<span title="${admin.loginDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.loginDate?string("yyyy-MM-dd")}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
							${(admin.loginIp)!}
						</td>
						<td>
							<#if admin.isAccountEnabled && !admin.isAccountLocked && !admin.isAccountExpired>
								<span class="green">正常</span>
							<#elseif !admin.isAccountEnabled>
								<span class="red"> 未启用 </span>
							<#elseif admin.isAccountLocked>
								<span class="red"> 已锁定 </span>
							<#elseif admin.isAccountExpired>
								<span class="red"> 已过期 </span>
							</#if>
						</td>
						<td>
							<span title="${admin.createDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.createDate}</span>
						</td>
						<td>
							<a href="admin!input.action?id=${admin.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="admin!delete.action" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "/WEB-INF/template/bms/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>