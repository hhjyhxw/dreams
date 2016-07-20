package com.zhumeng.dream.module.bms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.module.CrudActionSupport;
import com.zhumeng.dream.module.bms.service.RoleService;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;
import com.zhumeng.dream.util.collections.Collections3;
import com.zhumeng.dream.util.struts.Struts2Utils;

@Namespace("/bms")
@Action("role")
public class RoleAction extends CrudActionSupport<Role>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private RoleService roleServiceImpl;
	
	private Role role;
	
	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Role getModel() {
		return role;
	}

	@Override
	public String list() throws Exception {
		if(pager==null){
			pager = new Page<Role>();
		}
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		pager = roleServiceImpl.findByPage(pager, filters);
		return LIST;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		role.setAuthorityListStore(Collections3.convertToString(role.getAuthoritysList(), ","));
		roleServiceImpl.saveOrUpdate(role);
		redirectUrl = "role!list.action";
		return SUCCESS;
	}

	@Override
	public String delete() {
		try {
			roleServiceImpl.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("角色中存在用户！请先删除用户！");
		}
		
		return ajaxJsonSuccessMessage("删除成功！");
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id==null){
			role = new Role();
		}else{
			role = roleServiceImpl.get(id);
		}
	}

}
