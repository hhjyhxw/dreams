package com.zhumeng.dream.module.bms.action;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.zhumeng.dream.module.CrudActionSupport;
/**
 * @filename      : LoginAction.java
 * @description   : 登陆实际上由 shiro框架过滤器来实现     过滤器执行登陆后 执行
 * @author        : 
 * @create        : 2013-4-7 上午11:52:45
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-7 上午11:52:45
 */
@Namespace("/bms")
@Results( { @Result(name = CrudActionSupport.INDEX, location = "/bms/page!main.action", type = "redirect") })
public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = -6971159733443044638L;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	// 登录页面
	public String execute(){

		if(SecurityUtils.getSubject().isAuthenticated()){
			logger.info("用户："+ SecurityUtils.getSubject().getPrincipal() +"已授权,直接进入");
			return CrudActionSupport.INDEX;
		}
		return SUCCESS;
	}

	/**
	 * 用户跳转
	 * @return
	 */
	public String login(){
		return "idx";
	}


}
