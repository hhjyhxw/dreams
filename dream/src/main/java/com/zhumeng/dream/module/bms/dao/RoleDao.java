package com.zhumeng.dream.module.bms.dao;

import java.util.List;

import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;

public interface RoleDao{

	void saveOrUpdate(Role entity);

	void saveEntity(Role role);

	Role get(Long id);

	List<Role> getAll();

	Page<Role> findPage(Page<Role> page, List<PropertyFilter> filters);

	Role get(String propertyName, String value);

	Integer getCountByAdminAndRole(Long id);

	void delete(Role role);


}
