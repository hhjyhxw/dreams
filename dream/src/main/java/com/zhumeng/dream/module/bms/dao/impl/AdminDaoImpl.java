package com.zhumeng.dream.module.bms.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.module.bms.dao.AdminDao;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;
import com.zhumeng.dream.orm.hibernate.HibernateDao;

@Repository//("adminDaoImpl")
public class AdminDaoImpl   extends HibernateDao<Admin, Long> implements AdminDao{

	/**
	 * 
	  * @author            :
	  * @param username     用户名
	  * @return            :
	 */
	public Admin get(String username) {
		try{
			String hql="from Admin where username=?";
			System.out.println("className====="+this.getClass().getName());
			Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).list().get(0);
			return admin;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
//
//	public Admin get(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void saveEntity(Admin user) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void update(Admin user) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void saveOrUpdate(Admin user) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public Page<Admin> findPage(Page<Admin> page, List<PropertyFilter> filters) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<Admin> getAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
