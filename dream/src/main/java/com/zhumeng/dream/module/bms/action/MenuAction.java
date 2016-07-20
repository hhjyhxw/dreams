// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.zhumeng.dream.module.bms.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @filename      : MenuAction.java
 * @description   : 菜单跳转action
 * @author        : 
 * @create        : 2016-7-19 上午11:52:02
 * @copyright     : dream Corporation 
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2016-7-19  上午11:52:02
 */
@Namespace("/bms")
public class MenuAction extends ActionSupport
{

	private static final long serialVersionUID = 0x59b93787839cd405L;

	public MenuAction()
	{
	}

	public String setting()
	{
		return "setting";
	}
	public String member()
	{
		return "member";
	}

	public String goods()
	{
		return "goods";
	}

	public String content()
	{
		return "content";
	}

	public String trade()
	{
		return "trade";
	}

	public String sys()
	{
		return "sys";
	}

	public String main()
	{
		return "main";
	}

	public String header()
	{
		return "header";
	}
	
	public String info(){
		return "info";
	}
	
	//管理员菜单
	

	
public String promotion(){
		return "promotion";
	}

	
	public String sales()
	{
		return "sales";
	}
	
	public String clear(){
		return "clear";
	}
	public String supplier(){
		return "supplier";
	}

}
