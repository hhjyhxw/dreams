/**    
  * @package com.hyzycommon 
  * @description : TODO(用一句话描述该包做什么) 
  * @Copyright: hyzy Corporation 2014    
  */
package com.zhumeng.dream.intercetors;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**   
 * @filename ：TokenAtionInterceptor.java   
 * @description : TODO(防止表单重复提交) 
 * 使用说明：在struts.xml配置该拦截器之前，必须提前配置TokenInjectionInterceptor
 * TokenInjectionInterceptor为页面生成token
 * 在页面上使用<input id="selfToken" name="selfToken" type="hidden" value="${selfToken}">进行引用
 * (如果那么name使用token，默认会引用struts自带的token值)
 * 最后进入本拦截器TokenAtionInterceptor进行判断
 * @version ：   V 1.0
 * @author : wucong
 * @create : 2011-10-31 上午11:31:52  
 * @Copyright: hyzy Corporation 2014    
 * 
 * Modification History:
 * 	Date			Author			Version			Description
 *--------------------------------------------------------------
 *2011-10-31 上午11:31:52
 */
public class TokenAtionInterceptor extends MethodFilterInterceptor {

	/**
	  * @Fields serialVersionUID
	  */ 
	private static final long serialVersionUID = 2112305172613231489L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String contextPath = request.getRequestURI();
		
		/**
		 * 异步处理的action。不做token处理。
		 */
		/*if(StringUtils.endsWith(contextPath, ".ajax") || StringUtils.endsWith(contextPath, ".json")){
			return invocation.invoke();	
		}*/
		
		String strRequestToken = (String)session.get("request_token");	//取出会话中的令牌
		String strToken = request.getParameter("selfToken");				//页面中的令牌
		
		
		if(strToken != null){
			if(strRequestToken != null && !strRequestToken.equals(strToken)){	//重复提交，重置令牌
				//如果是ajax
				if(StringUtils.endsWith(contextPath, ".ajax") || StringUtils.endsWith(contextPath, ".json")){
					ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
					ServletActionContext.getResponse().setContentType("text/plain");
					PrintWriter pw=ServletActionContext.getResponse().getWriter();
					pw.write("{\"result\":\"fail\",\"url\":\"html/invalidToken.html\"}");
					pw.flush();
					pw.close();
					return null;
				}
				return "invalidToken";
			}
		}
		return invocation.invoke();	//否则正常运行
	}

}

