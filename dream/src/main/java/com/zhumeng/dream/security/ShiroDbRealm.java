/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.zhumeng.dream.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.module.bms.service.AdminService;
import com.octo.captcha.service.CaptchaService;

public class ShiroDbRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	@Resource(name="adminServiceImpl")
	protected AdminService adminServiceImpl;
	

	public AdminService getAdminServiceImpl() {
		return adminServiceImpl;
	}

	public void setAdminServiceImpl(AdminService adminServiceImpl) {
		this.adminServiceImpl = adminServiceImpl;
	}

	@SuppressWarnings("restriction")
	@Resource
	private CaptchaService captchaServices;
	

	 public ShiroDbRealm() {
//	        setCredentialsMatcher(new Sha256CredentialsMatcher());
	        setCredentialsMatcher(new Md5CredentialsMatcher());
	    }
	/**
	 * 认证回调函数, 登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		boolean isCaptcha = validateCaptcha(token);
		if(!isCaptcha){
			throw new IncorrectCaptchaException("验证码错误.");
		}
		
		Admin user = adminServiceImpl.getAdminByLoginName(token.getUsername());
		if (user != null) {
//			byte[] salt = Encodes.decodeHex(user.getPassword());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUsername(), user.getName()),
			user.getPassword(),  getName());
//			return new SimpleAuthenticationInfo(user.getUsername(),
//					user.getPassword(), getName());
		} else {
			return null;
		}
	}
	
	/**
	 * 校验验证码.
	 * @author:
	 * @param token 认真令牌
	 * @return
	 */
	protected boolean validateCaptcha(CaptchaUsernamePasswordToken token) {
		String captchaID = (String) SecurityUtils.getSubject().getSession().getId();
		String challengeResponse = StringUtils.upperCase(token.getCaptcha());
		if(log.isDebugEnabled())
			log.debug("校验验证码: captchaID ="+ captchaID+ ";challengeResponse="+challengeResponse);
		
		return captchaServices.validateResponseForID(captchaID, challengeResponse);
	}
	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		Admin user = adminServiceImpl.getAdminByLoginName(shiroUser.loginName);
		
		user.setLoginIp(SecurityUtils.getSubject().getSession().getHost());
		user.setLoginDate(SecurityUtils.getSubject().getSession().getLastAccessTime());
		adminServiceImpl.update(user);
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.addRoles(user.getRoleList());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (Role role : user.getRoleList()) {
				info.addRole(role.getName());
				info.addStringPermissions(role.getAuthorityList());
			}
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(adminServiceImpl.HASH_ALGORITHM);
		matcher.setHashIterations(adminServiceImpl.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}
    */

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;

		public ShiroUser(Long id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}
		
		public Long getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this, "loginName");
		}

		/**
		 * 重载equals,只比较loginName
		 */
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj, "loginName");
		}
	}
}
