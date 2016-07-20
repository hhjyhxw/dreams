package com.zhumeng.dream.module.bms.service;

import java.util.List;

import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;

public interface AdminService {

	Admin getAdminByLoginName(String username);

	void update(Admin user);

	Page<Admin> findByPage(Page<Admin> pager, List<PropertyFilter> filters);

	Admin getAdmin(Long id);

	void saveOrUpdate(Admin admin);

	String delete(Long[] ids);


}
