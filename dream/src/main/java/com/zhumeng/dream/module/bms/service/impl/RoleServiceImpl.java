package com.zhumeng.dream.module.bms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.intercetors.ServiceException;
import com.zhumeng.dream.module.bms.dao.RoleDao;
import com.zhumeng.dream.module.bms.service.RoleService;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Resource(name="roleDaoImpl")
	private RoleDao roleDaoImpl;
	
	public void saveOrUpdate(Role entity){
		roleDaoImpl.saveOrUpdate(entity);
	}
	public void save(Role role){
		roleDaoImpl.saveEntity(role);
	}
	
	public Role get(Long id){
		return roleDaoImpl.get(id);
	}
	
	public List<Role> getAllRole(){
		return roleDaoImpl.getAll();
	}
	
	/**
	 * 分页查询
	 * @param page 分页对象 
	 * @param filters
	 * @return
	 */
	public Page<Role> findByPage(final Page<Role> page, final List<PropertyFilter> filters){
		return roleDaoImpl.findPage(page, filters);
	}
	
	/**
	 * 通过属性字段获取对象 
	 * @autor Wangxy
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public Role getBy(String propertyName, String value){
		return roleDaoImpl.get(propertyName, value);
	}
	
	/**
	 * 根据id删除对应角色信息
	 * @autor Wangxy
	 * @param id
	 * @throws Exception 
	 */
	public String delete(Long[] ids) throws Exception{
		String info = "";
		for(Long id : ids){
			//得到对应角色
			Role role = roleDaoImpl.get(id);
			//验证角色下用户是否为空
			Integer count = roleDaoImpl.getCountByAdminAndRole(id);
			if(count!=0){
				//return "";
				throw new ServiceException();
			}
			//删除角色
			roleDaoImpl.delete(role);
		}
		return info;
	}
}
