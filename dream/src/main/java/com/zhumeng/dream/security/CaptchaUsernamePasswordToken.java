/**
 * 
 */
package com.zhumeng.dream.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 *
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	private String captcha;
	
	
	public CaptchaUsernamePasswordToken(){
		super();
	}
	
	public CaptchaUsernamePasswordToken(final String username, final String password, final String captcha,final boolean rememberMe){
		this(username, password != null ? password.toCharArray() : null, rememberMe, null,captcha);
	}
	
	public CaptchaUsernamePasswordToken(String username,char[] password,
            boolean rememberMe, String host,String captcha){
		super(username, password, rememberMe, host);
        this.captcha = captcha;
	}
	
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	 
}
