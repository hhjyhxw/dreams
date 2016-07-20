package com.zhumeng.dream.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.google.common.collect.ImmutableList;

@Entity
@Table(name="T_ROLE")
public class Role extends BaseEntity {
	private static final long serialVersionUID = 3350051268935865741L;
	private Long id;
	/**
	 * 角色名
	 */
	private String name;
	
	/**
	 * 是否是系统角色
	 */
	private Boolean isSystem = false;
	
	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 权限字符串 用","号分隔
	 */
	private String authorityListStore;
	
	/**
	 * 共页面传值用 不持久化到数据库
	 */
	private List<String> authoritysList;
	
	//private List<String> permissionList = Lists.newArrayList();

	public String getDescription() {
		return description;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}
	@Column(length = 255, nullable = false, unique = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthorityListStore() {
		return authorityListStore;
	}
	public void setAuthorityListStore(String authorityListStore) {
		this.authorityListStore = authorityListStore;
	}
	
	/**
	 * 页面上传值过来的权限集合 权限框架中不使用
	 * @return
	 */
	@Transient
	public List<String> getAuthoritysList() {
		return authoritysList;
	}
	public void setAuthoritysList(List<String> authoritysList) {
		this.authoritysList = authoritysList;
	}
	
	/**
	 * 真正的权限集合
	 * @return
	 */
	@Transient
	public List<String> getAuthorityList() {
		return ImmutableList.copyOf(StringUtils.split(authorityListStore, ","));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**角色与权限的关系配置  目前不使用表关联 不使用 仅做为权限参考
	@ElementCollection
	@CollectionTable(name = "t_role_permission", joinColumns = { @JoinColumn(name = "role_id") })
	@Column(name = "permission")
	public List<String> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
	*/
	
}
