/**
 * 
 */
package com.zhumeng.dream.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @filename      : CaptchaFormAuthenticationFilter.java
 * @description   : 重新shiro过滤器FormAuthenticationFilter 带验证码参数
 * @author        : chengkunxf
 * @create        : 2013-4-7 上午11:55:09
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-7 上午11:55:09
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationWithLockFilter {
   public static final String DEFAULT_CAPTCHA_PARAM = "j_captcha";
   private String captchaParam =DEFAULT_CAPTCHA_PARAM;
   protected Logger logger = LoggerFactory.getLogger(getClass());
	
   public String getCaptchaParam() {
       return captchaParam;
   }
   
   protected String getCaptcha(ServletRequest request) {
       return WebUtils.getCleanParam(request, getCaptchaParam());
   }
   
   /**
    * 重写创建用户密码令牌
    */
   protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
	   	   
	       String username = getUsername(request);
	       String password = getPassword(request);
	       String captcha = getCaptcha(request);
	       boolean rememberMe = isRememberMe(request);
	       String host = getHost(request);
	       logger.info("username="+username + ";Host="+host);
	       return new CaptchaUsernamePasswordToken(username, password.toCharArray(), rememberMe, host,captcha);
	}
}
