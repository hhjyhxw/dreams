package com.zhumeng.dream.module.bms.dao;

import java.util.List;

import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;

public interface AdminDao{

	Admin get(Long id);

	void saveEntity(Admin user);

	void update(Admin user);

	void saveOrUpdate(Admin user);

	Page<Admin> findPage(Page<Admin> page, List<PropertyFilter> filters);

	List<Admin> getAll();

	Admin get(String username);

}
