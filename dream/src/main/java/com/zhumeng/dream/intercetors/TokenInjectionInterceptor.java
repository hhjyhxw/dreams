/**
 * 
 */
package com.zhumeng.dream.intercetors;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zhumeng.dream.util.others.RandomGUIDUtil;

/**
 * @filename: TokenInjectionInterceptor.java
 * @description:  对页面请求后，即为该页面生成token
 * @version: v 1.0
 * @author: wucong
 * @create: 下午02:55:30
 * @CopyRight: hyzy Corporation 2014
 * Modification History:
 * Date          Author          Version         Description
 * --------------------------------------------------------------
 */
public class TokenInjectionInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5446717618965213871L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String contextPath = request.getRequestURI();
		
		/**
		 * 异步处理的action。不做token处理。
		 */
		if(StringUtils.endsWith(contextPath, ".ajax") || StringUtils.endsWith(contextPath, ".json")){
			return invocation.invoke();	
		}
		String strGUID = RandomGUIDUtil.newGuid();						//生成令牌
		session.put("request_token", strGUID);
		request.setAttribute("selfToken", strGUID);
		return invocation.invoke();	//否则正常运行
	}

}
