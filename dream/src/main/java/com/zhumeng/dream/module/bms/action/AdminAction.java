package com.zhumeng.dream.module.bms.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.util.Assert;
import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.entity.Role;
import com.zhumeng.dream.module.CrudActionSupport;
import com.zhumeng.dream.module.bms.service.AdminService;
import com.zhumeng.dream.module.bms.service.RoleService;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;
import com.zhumeng.dream.util.others.StringUtil;
import com.zhumeng.dream.util.reflection.ReflectionUtils;
import com.zhumeng.dream.util.struts.Struts2Utils;
@Namespace("/bms")
public class AdminAction extends CrudActionSupport<Admin>{

	private static final long serialVersionUID = 1L;
	private final String SUPER_ROLE="超级管理员 ";
	
	@Resource
	private RoleService roleServiceImpl;
	@Resource
	private AdminService adminServiceImpl;
	
	private Admin admin = new Admin();
	private String oldPassword;
	private String isEdit;
	private String isAccountEnabledFlag;
	private Role superRole;
	private Admin currentAdmin;
	
	private List<Role> allRoleList;
	
	private List<Long> checkedRoleIds;
	
	

	/**
	 * 不需要在生成 实体类admin 的get() set()方法 
	 * 在页面中直接使用属性 无需对象点属性 
	 * 例如 <input type="text" name="username" value="${username}"/>
	 */
	@Override
	public Admin getModel() {
	    return admin;
	}
	
//	/**
//	 * 所用供应商(此方法为新增，20150331)
//	 * @autor zhaimingming
//	 * @return
//	 */
//	public List<Supplier> getAllSupplier(){
//		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		PropertyFilter filter2 = new PropertyFilter("EQE_auditStatus","auditPass");
//		filters.add(filter2);
//		return supplierService.getSupplier(null, filters);
//	}
	
	
	public String list() {
			superRole = roleServiceImpl.getBy("name", SUPER_ROLE);
			if(pager==null){
				pager = new Page<Admin>();
			}
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
			//filters.add(new PropertyFilter("EQB_isAccountEnabled","1"));
			pager = adminServiceImpl.findByPage(pager, filters);
			return LIST;
	}

	@Override
	public String input() throws Exception {
		superRole = roleServiceImpl.getBy("name", SUPER_ROLE);
		currentAdmin = adminServiceImpl.getAdmin(this.getLoginUser().id);
		Long currentsupplierid = admin.getSupplierid();
		setAttribute("currentsupplierid", currentsupplierid);
		List<Role> currentroleList = admin.getRoleList();
		setAttribute("currentroleList", currentroleList);
		if(currentsupplierid!=null){
		//Supplier currentSupplier = supplierService.getSupplier(currentsupplierid);
		//setAttribute("currentSupplier", currentSupplier);
		}
		if("true".equals(isEdit)){
			return "edit";
		}else{
			return INPUT;
		}
	}

	@Override
	public String save() throws Exception {
		try {
			if(admin == null){
				addActionError("请选择一个用户");
				return ERROR;
			}
			
			//HibernateUtils.mergeByCheckedIds(admin.getRoleList(), checkedRoleIds, Role.class);
		mergeByCheckedIds(admin.getRoleList(), checkedRoleIds, Role.class);
			
		
			//新增
			if(admin.getId()==null){
				admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
			}else{//修改
				if(StringUtils.isNotEmpty(admin.getPassword())){
					admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
				}else{
					admin.setPassword(oldPassword);
				}
			}
			admin.setIsAccountEnabled(Boolean.valueOf(isAccountEnabledFlag));
			adminServiceImpl.saveOrUpdate(admin);
			
			//add 2015/06/30 创建管理员成功推送致如风达物流系统
			List<Admin> adminList=new ArrayList<Admin>();
			adminList.add(admin);
			//新增结束
			
			try {
				SecurityUtils.getSecurityManager().checkPermission(SecurityUtils.getSubject().getPrincipals(), "ROLE_ADMIN");
				redirectUrl="admin!list.action";
			} catch (AuthorizationException e) {
				redirectUrl="admin!input.action?id="+getLoginUser().getId()+"&isEdit=true";
			}
			
	//		if(SecurityUtils.getSecurityManager().isPermitted(SecurityUtils.getSubject().getPrincipals(), "ROLE_ADMIN")){
	//			redirectUrl="admin!list.action";
	//		}else{
	//			redirectUrl="admin!input.action?id="+getLoginUser().getId()+"&isEdit=true";
	//		}
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}

	@Override
	public String delete() throws Exception {
		try{
			//String info = adminServiceImpl.delete(ids);
			String result = adminServiceImpl.delete(ids);
			if(StringUtil.checkObj(result)){
				ajaxJsonErrorMessage(result);
			}else{
				ajaxJsonSuccessMessage("删除成功！");
			}
		}catch(Exception e){
			ajaxJsonErrorMessage("删除失败！");
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			admin = adminServiceImpl.getAdmin(id);
		} else {
			if("true".equals(isEdit)){
				admin = adminServiceImpl.getAdmin(this.getLoginUser().id);
			}else{
				admin = new Admin();
			}
		}
	}

	
	public String checkUsername(){
		if(adminServiceImpl.getAdminByLoginName(getParameter("username"))!=null){
			//用户名已存在
			return ajaxJson("false");
		};
		return ajaxJson("true");
	}
	/**
	 * 根据角色获取 零售商列表
	 * @return
	 */
	public String getSupplierList(){
		String roleId = getParameter("roleId");
		Role role =	roleServiceImpl.get(Long.parseLong(roleId));
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if(role.getName().indexOf("零售商")>=0){
			PropertyFilter filter = new PropertyFilter("EQE_supplierType","retailer");
			filters.add(filter);
			PropertyFilter filter2 = new PropertyFilter("EQE_auditStatus","auditPass");
			filters.add(filter2);
		}
		if(role.getName().indexOf("供应商")>=0){
			PropertyFilter filter = new PropertyFilter("NEQE_supplierType","retailer");
			filters.add(filter);
		}
		if(role.getName().indexOf("管理员")>=0){
			PropertyFilter filter2 = new PropertyFilter("EQE_auditStatus","auditPass");
			filters.add(filter2);
		}
//		List<Supplier> list = supplierService.getSupplier(null, filters);
//		JSONArray array = new JSONArray();
//		for(Supplier s:list){
//			JSONObject o = new JSONObject();
//			o.put("sid", s.getId()+"");
//			o.put("name", s.getCompanyName());
//			array.add(o);
//		}
//		return ajaxJson(array.toString());
		return null;
	}
	
	
	/**
	 * 获取当前登录人的信息
	 * @return
	 * @throws Exception 
	 */
	public String edit() throws Exception{
		id = this.getLoginUser().id;
//		admin = accountService.getAdmin(id);
//		setAttribute("admin", admin);
		return INPUT;
	}
	
	/**
	 * 修改个人资料
	 * @autor 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		Admin oldAdmin = adminServiceImpl.getAdmin(id);
		if(oldAdmin == null){
			addActionError("请选择一个用户");
			return ERROR;
		}
		//修改密码
		if(StringUtils.isNotEmpty(admin.getPassword())){
			oldAdmin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		}
		//修改邮箱
		if(StringUtils.isNotEmpty(admin.getEmail())){
			oldAdmin.setEmail(admin.getEmail());
		}
		//修改部门
		if(StringUtils.isNotEmpty(admin.getDepartment())){
			oldAdmin.setDepartment(admin.getDepartment());
		}
		//修改姓名
		if(StringUtils.isNotEmpty(admin.getName())){
			oldAdmin.setName(admin.getName());
		}
		adminServiceImpl.saveOrUpdate(oldAdmin);
		
		try {
			SecurityUtils.getSecurityManager().checkPermission(SecurityUtils.getSubject().getPrincipals(), "ROLE_ADMIN");
			redirectUrl="admin!list.action";
		} catch (AuthorizationException e) {
			redirectUrl="admin!input.action?id="+getLoginUser().getId()+"&isEdit=true";
		}
		
		return SUCCESS;
	}

	/**
	 * 根据对象ID集合, 整理合并集合.
	 * 
	 * 页面发送变更后的子对象id列表时,在Hibernate中删除整个原来的子对象集合再根据页面id列表创建一个全新的集合这种看似最简单的做法是不行的.
	 * 因此采用如此的整合算法：在源集合中删除id不在目标集合中的对象,根据目标集合中的id创建对象并添加到源集合中.
	 * 因为新建对象只有ID被赋值, 因此本函数不适合于cascade-save-or-update自动持久化子对象的设置.
	 * 
	 * @param srcObjects 源集合,元素为对象.
	 * @param checkedIds  目标集合,元素为ID.
	 * @param clazz  集合中对象的类型,必须为IdEntity子类
	 */
	public static <T extends Role> void mergeByCheckedIds(final Collection<T> srcObjects,
			final Collection<Long> checkedIds, final Class<T> clazz) {

		//参数校验
		Assert.notNull(srcObjects, "scrObjects不能为空");
		Assert.notNull(clazz, "clazz不能为空");

		//目标集合为空, 删除源集合中所有对象后直接返回.
		if (checkedIds == null) {
			srcObjects.clear();
			return;
		}

		//遍历源对象集合,如果其id不在目标ID集合中的对象删除.
		//同时,在目标集合中删除已在源集合中的id,使得目标集合中剩下的id均为源集合中没有的id.
		Iterator<T> srcIterator = srcObjects.iterator();
		try {

			while (srcIterator.hasNext()) {
				T element = srcIterator.next();
				Long id = element.getId();

				if (!checkedIds.contains(id)) {
					srcIterator.remove();
				} else {
					checkedIds.remove(id);
				}
			}

			//ID集合目前剩余的id均不在源集合中,创建对象,为id属性赋值并添加到源集合中.
			for (Long id : checkedIds) {
				T element = clazz.newInstance();
				element.setId(id);
				srcObjects.add(element);
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	public List<Role> getAllRoleList() {
		return roleServiceImpl.getAllRole();
	}

	public void setAllRoleList(List<Role> allRoleList) {
		this.allRoleList = allRoleList;
	}


	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}


	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsAccountEnabledFlag() {
		return isAccountEnabledFlag;
	}

	public void setIsAccountEnabledFlag(String isAccountEnabledFlag) {
		this.isAccountEnabledFlag = isAccountEnabledFlag;
	}

	public Role getSuperRole() {
		return superRole;
	}

	public void setSuperRole(Role superRole) {
		this.superRole = superRole;
	}

	public Admin getCurrentAdmin() {
		return currentAdmin;
	}

	public void setCurrentAdmin(Admin currentAdmin) {
		this.currentAdmin = currentAdmin;
	}

    public static void main(String[] args) {  
        //方法一:利用locale获取默认使用的资源文件  
       // Locale local  = Locale.getDefault();  
       // ResourceBundle localResource = ResourceBundle.getBundle("resourse");  
          
        //方法二：直接在资源文件名后面加上"_语言_国家"，如_zh_CN、_en_US等，使用指定的资源文件  
    	//Thread.currentThread().getContextClassLoader().getResource("i18n_zh_CN.properties");
        ResourceBundle localResource = ResourceBundle.getBundle("i18n_zh_CN");//"i18n_zh_CN.properties");  
          
        String val = localResource.getString("TradeStatus.unprocessed");  
        System.out.println(val);     
    }  
}
