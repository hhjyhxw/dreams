package com.zhumeng.dream.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义验证码认证异常
 * @author Administrator
 *
 */
public class IncorrectCaptchaException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;
	
	public IncorrectCaptchaException() {
         super();
    }
    public IncorrectCaptchaException(String message, Throwable cause) {
         super(message, cause);
    }
    public IncorrectCaptchaException(String message) {
         super(message);
    }
    public IncorrectCaptchaException(Throwable cause) {
         super(cause);
    }

}
