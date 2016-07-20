package com.zhumeng.dream.module.bms.action;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import com.opensymphony.xwork2.ActionSupport;
import com.zhumeng.dream.module.bms.service.AdminService;

/**
 * @filename      : PageAction.java
 * @description   : 页面框架
 * @author        : chengkunxf
 * @create        : 2013-4-7 上午11:52:28
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-7 上午11:52:28
 */
@Namespace("/bms")
public class PageAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Resource
	AdminService AdminServiceImpl;

	public String main()
	{
		return "main";
	}

	public String header()
	{
		return "header";
	}

	public String menu()
	{
		return "menu";
	}

	public String middle()
	{
		return "middle";
	}

	public String index()
	{
		return "index";
	}
	
	
	
	
	public String getJavaVersion()
	{
		return System.getProperty("java.version");
	}
	
	public String getOsName()
	{
		return System.getProperty("os.name");
	}
	
	public String getOsArch()
	{
		return System.getProperty("os.arch");
	}
	
	public String getOsVersion()
	{
		return System.getProperty("os.version");
	}
	
	public String getServerInfo()
	{
		return StringUtils.substring(ServletActionContext.getServletContext().getServerInfo(), 0, 30);
	}

	public String getServletVersion()
	{
		return (new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getMajorVersion()))).append(".").append(ServletActionContext.getServletContext().getMinorVersion()).toString();
	}
}
