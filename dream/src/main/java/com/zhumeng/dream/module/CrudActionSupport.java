package com.zhumeng.dream.module;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.zhumeng.dream.bean.Setting;
import com.zhumeng.dream.entity.Admin;
import com.zhumeng.dream.entityvo.NavSupplierVO;
import com.zhumeng.dream.module.bms.service.AdminService;
import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.security.ShiroDbRealm.ShiroUser;
import com.zhumeng.dream.util.encode.JsonBinder;
import com.zhumeng.dream.util.others.SettingUtil;
import com.zhumeng.dream.util.struts.Struts2Utils;
/**
 * 
 * @filename      : CrudActionSupport.java
 * @description   : Struts2中典型CRUD Action的抽象基类.<T> CRUDAction所管理的对象类型.
 *                  主要定义了对Preparable,ModelDriven接口的使用,以及CRUD函数和返回值的命名.
 * @author        : chengkunxf
 * @create        : 2013-4-7 上午11:50:14
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-7 上午11:50:14
 */
public abstract class CrudActionSupport<T> extends ActionSupport implements ModelDriven<T>, Preparable {

	private static final long serialVersionUID = -1653204626115064950L;
	
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	public static final String SELECT  = "select";
	public static final String IMPORT  = "import";
	public static final String INFO = "info";
	public static final String SEARCH_KEY_WORD = "searchHistory";
	
	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
	public static final String RELOAD = "reload";
	/**进行批量删除 或  单体删除 后，回到集合页面,将共用删除时提交过来的分页查询值*/
	public static final String RELOADCHAIN = "reloadchain";
	public static final String INDEX = "index";
	/**回退页面， 也可以调用 rollback(URL)跳转*/
	public static final String ROLLBACK = "rollback";
	protected String redirectUrl;// 操作提示后的跳转URL,为null则返回前一页
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**选中的ids*/
	protected Long[] ids;
	
	/**当前映射id*/
	protected Long id;
	
	protected Page<T> pager;
	
//	private Long supplierID;

	
	/**
	 * 获取导航SupplierID
	 * @return 存在则返回保存的导航SupplierID，否则返回-1
	 */
	public NavSupplierVO getNavSupplier(){
		if(this.getSession().containsKey(ShopSessionUtil.SESSION_NAV_SUPPLIER)){
			return (NavSupplierVO)this.getSession(ShopSessionUtil.SESSION_NAV_SUPPLIER);
		}
		// 不存在则存放配置中的零售店名称
		//return new NavSupplierVO(this.getSetting().getShopName());
		return null;
	}
//	
//	public String getSupplierID() {
//		return getRequest().getParameter("supplierID");
//	}
//
//	public void setSupplierID(Long supplierID) {
//		this.supplierID = supplierID;
//	}
	
	@Resource
	AdminService adminServiceImpl;
	
//	public void info(String info){
//		logService.info(info, ServletActionContext.getRequest().getRemoteAddr(), getLoginUser().getName());
//	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public Page<T> getPager() {
		return pager;
	}

	public void setPager(Page<T> pager) {
		this.pager = pager;
	}
	
	
	
	// 获取Attribute
		public Object getAttribute(String name) {
			return ServletActionContext.getRequest().getAttribute(name);
		}


		// 获取Parameter数组
		public String[] getParameterValues(String name) {
			return getRequest().getParameterValues(name);
		}

		// 获取Session
		public Object getSession(String name) {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			return session.get(name);
		}

		// 获取Session
		public Map<String, Object> getSession() {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			return session;
		}

		// 设置Session
		public void setSession(String name, Object value) {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			session.put(name, value);
		}

		// 获取Request
		public HttpServletRequest getRequest() {
			return ServletActionContext.getRequest();
		}

		// 获取Response
		public HttpServletResponse getResponse() {
			return ServletActionContext.getResponse();
		}

		// 获取Application
		public ServletContext getApplication() {
			return ServletActionContext.getServletContext();
		}

		// AJAX输出，返回null
		public String ajax(String content, String type) {
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType(type + ";charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.getWriter().write(content);
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		// AJAX输出文本，返回null
		public String ajaxText(String text) {
			return ajax(text, "text/plain");
		}

		// AJAX输出HTML，返回null
		public String ajaxHtml(String html) {
			return ajax(html, "text/html");
		}

		// AJAX输出XML，返回null
		public String ajaxXml(String xml) {
			return ajax(xml, "text/xml");
		}

		// 根据字符串输出JSON，返回null
		public String ajaxJson(String jsonString) {
			return ajax(jsonString, "text/html");
		}
		
		// 根据Map输出JSON，返回null
		public String ajaxJson(Map<String, String> jsonMap) {
			return ajax(JsonBinder.buildNormalBinder().toJson(jsonMap), "text/html");
		}
		
		
		// 输出JSON警告消息，返回null
		public String ajaxJsonWarnMessage(String message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, WARN);
			jsonMap.put(MESSAGE, message);
			return ajax(JsonBinder.buildNormalBinder().toJson(jsonMap), "text/html");
		}
		
		// 输出JSON成功消息，返回null
		public String ajaxJsonSuccessMessage(String message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put(MESSAGE, message);
			return ajax(JsonBinder.buildNormalBinder().toJson(jsonMap), "text/html");
		}
		
		// 输出JSON错误消息，返回null
		public String ajaxJsonErrorMessage(String message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, ERROR);
			jsonMap.put(MESSAGE, message);
			return ajax(JsonBinder.buildNormalBinder().toJson(jsonMap), "text/html");
		}
		
		// 设置页面不缓存
		public void setResponseNoCache() {
			getResponse().setHeader("progma", "no-cache");
			getResponse().setHeader("Cache-Control", "no-cache");
			getResponse().setHeader("Cache-Control", "no-store");
			getResponse().setDateHeader("Expires", 0);
		}
		

    public ShiroUser getLoginUser(){
    	return (ShiroUser)SecurityUtils.getSubject().getPrincipal();
    }
    
    /**
	 * 获取当前登陆admin
		 * @autor long
		 * @param @return
		 * @return Admin
	 */
	public Admin getLoginAdmin(){
		Admin user=adminServiceImpl.getAdminByLoginName(getLoginUser().loginName);
		return user;
	}
	/**
	 * Action函数, 默认的action函数, 默认调用list()函数.
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	//-- CRUD Action函数 --//
	/**
	 * Action函数,显示Entity列表界面.
	 * 建议return SUCCESS.
	 */
	public abstract String list() throws Exception;

	/**
	 * Action函数,显示新增或修改Entity界面.
	 * 建议return INPUT.
	 */
	@Override
	public abstract String input() throws Exception;

	/**
	 * Action函数,新增或修改Entity. 
	 * 建议return RELOAD.
	 */
	public abstract String save() throws Exception;

	/**
	 * Action函数,删除Entity.
	 * 建议return RELOAD.
	 */
	public abstract String delete() throws Exception;

	//-- Preparable函数 --//
	/**
	 * 实现空的prepare()函数,屏蔽了所有Action函数都会执行的公共的二次绑定.
	 */
	public void prepare() throws Exception {
	}

	/**
	 * 定义在input()前执行二次绑定.
	 */
	public void prepareInput() throws Exception {
		prepareModel();
	}

	/**
	 * 定义在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		prepareModel();
	}
	
	/**
	 * 获取请求参数
	 * @author:chengkunxf
	 * @param parame
	 * @return
	 */
	public String getParameter(String parame){
		return Struts2Utils.getRequest().getParameter(parame);
	}
	
	/**
	 * 获取系统配置项
	 * @autor chengkunxf
	 * @return
	 */
	public Setting getSetting(){
		return SettingUtil.getSetting();
	}
	
	/**
	 * 向request里面设置值
	 * @author:chengkunxf
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key,Object value){
		Struts2Utils.getRequest().setAttribute(key, value);
	}
	
	/**
	 * 业务执行失败，将回调上次页面
	 * @param url 回调地址   非必填
	 * @return
	 */
	public String rollback(String url){
		Struts2Utils.getRequest().setAttribute("redirectUrl", url);
		return ROLLBACK;
	}
	/**
	 * 等同于prepare()的内部函数,供prepardMethodName()函数调用. 
	 */
	protected abstract void prepareModel() throws Exception;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//纯价格显示
	public String getPriceFormat(){
		return SettingUtil.getPriceFormat();
	}
	// 设置分销价格式转换 货币符+价格
	public String getPriceCurrencyFormat(){
		return SettingUtil.getPriceCurrencyFormat();
	}
	//货币符 + 价格 + 货币单位
	public String getPriceUnitCurrencyFormat(){
		return SettingUtil.getPriceUnitCurrencyFormat();
	}
	//获取cookie
	public Cookie[] getCookie(){
		return getRequest().getCookies();
	}
	/**
	 * 将搜索处理后记录存入cookie
	  * @author            : zhaimm
	  * @param keyWord 新搜索词
	  * @throws UnsupportedEncodingException            :
	 */
	public void addKeyWordToCookie(String keyWord) throws UnsupportedEncodingException{
		Cookie[] cookieArray = getCookie();
		if(null!=cookieArray){
			String cookieValue=null;
			for(Cookie c:cookieArray){
				if(c.getName().equalsIgnoreCase(SEARCH_KEY_WORD)){
					cookieValue=URLDecoder.decode(c.getValue(), "utf-8");
					break;
				}
			}
			if(cookieValue!=null){
				String newCookie = keyWordsFilterAndOrder(keyWord, cookieValue);
				System.out.println(newCookie);
				addCookie(SEARCH_KEY_WORD, URLEncoder.encode(newCookie, "utf-8"), 3600*24*6);
			}else{
				//用户第一次搜索
				addCookie(SEARCH_KEY_WORD, URLEncoder.encode(keyWord, "utf-8"), 3600*24*6);
			}
		}else{
			//用户第一次搜索
			addCookie(SEARCH_KEY_WORD, URLEncoder.encode(keyWord, "utf-8"), 3600*24*6);
		}
	}
	//保存到cookie
	public void addCookie(String name,String value,Integer maxAge){
		Cookie cookie = new Cookie(name,value);
		cookie.setPath(this.getRequest().getContextPath() + "/");
		if(maxAge>0){
			cookie.setMaxAge(maxAge);
		}
		this.getResponse().addCookie(cookie);
	}
	/**
	 * 搜索词去重及重置
	  * @author            : zhaimm
	  * @param keyWord  新搜索的关键词
	  * @param cookieValue  cookie中保存的搜索记录的字符串
	  * @return            :
	 */
	public String keyWordsFilterAndOrder(String keyWord,String cookieValue){
		String[] splitArray = cookieValue.split(",");
		List<String> list=new LinkedList<String>();
		for(String string:splitArray){
			list.add(string);
		}
		if(!list.contains(keyWord)){
			list.add(0, keyWord);
			StringBuffer sb=new StringBuffer();
			String topElement="";
			int i=0;
			Iterator<String> iterator = list.iterator();
			while(iterator.hasNext()){
				topElement=iterator.next();
				sb.append(topElement);
				i++;
				if(i>=10){
					//最多保留10个搜索关键词
					break;
				}
				if(iterator.hasNext()){
					sb.append(",");
				}
			}
			return sb.toString();
		}else{
			return cookieValue;
		}
		
	}
	
	 public T getModel(){
		 
		return null;
		 
	 }
}
