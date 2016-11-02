package com.zhumeng.dream.module.bms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.module.bms.action.AdminAction;
import com.zhumeng.dream.module.bms.dao.AdminDao;
import com.zhumeng.dream.module.bms.dao.RoleDao;
import com.zhumeng.dream.module.bms.service.AdminService;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;
import com.zhumeng.dream.util.encode.Digests;
import com.zhumeng.dream.util.encode.Encodes;
@SuppressWarnings("restriction")
@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	@Resource//(name="adminDaoImpl")
	private AdminDao adminDaoImpl;
	@Resource//(name="roleDaoImpl")
	private RoleDao roleDaoImpl;
	
	public Admin getAdminByLoginName(String username){
		return adminDaoImpl.get(username);
	}
	
	public Admin getAdmin(Long id){
		return adminDaoImpl.get(id);
	}
	public void save(Admin user){
		adminDaoImpl.saveEntity(user);
	}
	
	public void update(Admin user){
		adminDaoImpl.update(user);
	}
	
	public void saveOrUpdate(Admin user){
		adminDaoImpl.saveOrUpdate(user);
	}
	
//	public void delete(Long[] ids){
//		if(ids != null){
//			for(Long pk : ids){
//				Admin admin=this.getAdmin(pk);
//				AdminAction.mergeByCheckedIds(admin.getRoleList(), null, Role.class);
//				adminDaoImpl.delete(admin);
//			}
//		}
//	}
//	
	/**
	 * 分页查询
	 * @param page 分页对象 
	 * @param filters
	 * @return
	 */
	public Page<Admin> findByPage(final Page<Admin> page, final List<PropertyFilter> filters){
		return adminDaoImpl.findPage(page, filters);
	}
	
	/**
	 * 获取所有管理员列表
	 * @autor chengkunxf
	 * @return
	 */
	public List<Admin> getAll(){
		return adminDaoImpl.getAll();
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(Admin user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
//		user.setPassword(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public String delete(Long[] ids) {
		String result = null;;
		if(ids != null){
			for(Long pk : ids){
				Admin admin=this.getAdmin(pk);
				if(admin.getIsAccountLocked()){
					result = "锁定用户不能删除";
					break;
				}else{
					AdminAction.mergeByCheckedIds(admin.getRoleList(), null, Role.class);
					adminDaoImpl.delete(admin);
				}
			}
		}
		return result;
	}

}
