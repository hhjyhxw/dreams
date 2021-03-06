<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>角色列表 - Powered By mall</title>
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
		角色管理&nbsp;总记录数: ${pager.totalCount} (共${pager.totalPages}页)
	</div>
	<div class="body">
		<form id="listForm" action="role!list.action" method="post">
			<div class="listBar">
			
				<input type="button" class="formButton" onclick="location.href='role!input.action'" value="添加角色" hidefocus />
				&nbsp;&nbsp;
				
				<label>角色名：</label>
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
						<a href="#" class="sort" name="name" hidefocus>角色名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="isSystem" hidefocus>系统内置</a>
					</th>
					<th>
						<a href="#" class="sort" name="description" hidefocus>描述</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as role>
					<tr>
						<td>
							<input type="checkbox"<#if role.isSystem> disabled title="系统内置权限不允许删除!"<#else> name="ids" value="${role.id}"</#if> />
						</td>
						<td>
							${role.name}
						</td>
						<td>
							<span class="${role.isSystem?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							${role.description}
						</td>
						<td>
							<span title="${role.createDate?string("yyyy-MM-dd HH:mm:ss")}">
								${role.createDate}
							</span>
						</td>
						<td>
							<a href="role!input.action?id=${role.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="role!delete.action" value="删 除" disabled hidefocus />
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