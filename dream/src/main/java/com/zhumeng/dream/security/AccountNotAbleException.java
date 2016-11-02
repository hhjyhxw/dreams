package com.zhumeng.dream.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义验证码认证异常
 * @author Administrator
 *
 */
public class AccountNotAbleException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;
	
	public AccountNotAbleException() {
         super();
    }
    public AccountNotAbleException(String message, Throwable cause) {
         super(message, cause);
    }
    public AccountNotAbleException(String message) {
         super(message);
    }
    public AccountNotAbleException(Throwable cause) {
         super(cause);
    }
}
