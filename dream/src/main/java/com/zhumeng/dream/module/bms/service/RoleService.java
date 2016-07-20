package com.zhumeng.dream.module.bms.service;

import java.util.List;

import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;

public interface RoleService {

	Page<Role> findByPage(Page<Role> pager, List<PropertyFilter> filters);

	void saveOrUpdate(Role role);

	String delete(Long[] ids) throws Exception;

	Role get(Long id);

	Role getBy(String string, String sUPER_ROLE);

	List<Role> getAllRole();

}
