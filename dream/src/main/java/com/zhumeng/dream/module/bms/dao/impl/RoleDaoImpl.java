package com.zhumeng.dream.module.bms.dao.impl;

import java.math.BigInteger;
import org.springframework.stereotype.Repository;
import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.module.bms.dao.RoleDao;
import com.zhumeng.dream.orm.hibernate.HibernateDao;
@Repository//("roleDaoImpl")
public class RoleDaoImpl  extends HibernateDao<Role, Long> implements RoleDao{

	/**
	 * 删除用户与角色之间的关系
	 * @autor Wangxy
	 * @param id
	 */
	public void deleteAdminAndRole(Long id){
		String sql = "delete from t_admin_role WHERE role_id = ?";
		getSession().createSQLQuery(sql).setParameter(0, id).executeUpdate();
	}
	
	/**
	 * 根据传入的角色ID判断该角色下用户是否为空
	 * @autor Wangxy
	 * @param id
	 * @return
	 */
	public Integer getCountByAdminAndRole(Long id){
		String sql = "select count(1) from t_admin_role WHERE role_id = ?";
		BigInteger count = (BigInteger)getSession().createSQLQuery(sql).setParameter(0, id).uniqueResult();
		return Integer.valueOf(count.toString());
	}

	public Role get(String propertyName, String value) {
		
		return super.get(propertyName, value);
	}

}
