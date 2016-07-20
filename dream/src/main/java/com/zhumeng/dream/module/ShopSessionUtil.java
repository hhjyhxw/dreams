package com.zhumeng.dream.module;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.type.TypeReference;

import com.opensymphony.xwork2.ActionContext;
import com.zhumeng.dream.entity.Member;

/**
 * @filename : SessionShopUtil.java
 * @description : TODO(一句话描述该类做什么)
 * @author : zhanghaitao
 * @create :
 * @copyright : hyzy Corporation 2014
 *
 *            Modification History: Date Author Version
 *            --------------------------------------
 * 
 */
public class ShopSessionUtil {
    static Logger log = Logger.getLogger(ShopSessionUtil.class);

    /** 保存登录会员ID的Session名称 */
    public static final String SESSION_MARK_MEMBER_USER_ID = "smUserId";
    /** 保存登录会员ID的Session名称 */
    public static final String SESSION_MARK_MEMBER_USER_NAME = "smUserName";
    /** 保存登录会员ID的Session名称 */
    public static final String SESSION_MARK_MEMBER_USER_NICK = "smUserNick";
    /** 保存登录零售商openid的Session名称 */
    public static final String SESSION_MARK_SUPPLIER_OPENID = "smSupplierOpenid";
    /** 保存登零售商supplierid的Session名称 */
    public static final String SESSION_MARK_SUPPLIER_ID = "smSupplierId";
    /** 保存登零售商supplier的Session名称 */
    public static final String SESSION_MARK_SUPPLIER = "smSupplier";
    /** 保存导航零售店的SupplierID */
    public static final String SESSION_NAV_SUPPLIER = "navSupplier";
    /** 保存兑换活动的ID */
    public static final String SESSION_MARK_EXCHANGE_ACTIVITT_ID = "exchangeActivityId";

    /**
     * 从Session中获取零售商ID
     * 
     * @return 当前登陆的零售商ID
     */
    public static Long getSupplierID() {

        Object supplierId = getSession().get("smSupplierId");
        // 测试用
        if (supplierId == null) {
            supplierId = new Long(68);
            getSession().put("smSupplierId", supplierId);
        }
        return supplierId == null ? -1 : (Long) supplierId;
    }

    /**
     * 登陆成功时设置的session，并设置相应的cookie
     * 
     * 
     * @param member
     */
    public static void setSessionAboutMember(Member member) {
        try {

            log.info("setSessionAboutMember  start-------------------");
            log.info("setSessionAboutMember member.getId():++++++++++++++++++++++++++++++" + member.getId());
            getSession().put(SESSION_MARK_MEMBER_USER_ID, member.getId());
            // 这里先默认，mobile优先，(如果想哪个登录，就设置哪个，需要添加UserName字段)
            if (StringUtils.isNotBlank(member.getMobile())) {// member.getEmail()
                getSession().put(SESSION_MARK_MEMBER_USER_NAME, member.getMobile());
            }/*
              * else{ getSession().put(SESSION_MARK_MEMBER_USER_NAME,
              * member.getEmail()); }
              */
            getSession().put(SESSION_MARK_MEMBER_USER_NICK, member.getNick());

            // 更新购物车信息(把之前没登录的时候添加的商品综合到第一个登录的用户身上)
            log.info("setSessionAboutMember  end-------------------");
            log.info("operateCarItemCookie  start-------------------");
            // log.info("setUserCookie-------------------");
            // log.info("member.getNick()|"+member.getNick());
            setUserCookie(member.getNick());
            setUserIdCookie(member.getId().toString());
            // log.info("setUserCookie---222222222zhi xing l ");
            // 百分点传用户id,如果有冲突，请通知一下
            // setUserCookie(member.getId().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            // log.info("setSessionAboutMember  catch问题-------------------");
        }
    }

    /**
     * 仅仅设置session(比如账户验证、找回密码等)
     * 
     * @autor zhanghaitao
     * @param member
     */
    public static void setMemberSessionOnly(Member member) {
        getSession().put(SESSION_MARK_MEMBER_USER_ID, member.getId());
    }

    // 获取sessionMap
    private static Map<String, Object> getSession() {
        ActionContext actionContext = ActionContext.getContext();
        return actionContext.getSession();
    }

    /**
     * 当前是否有用户登录(前台的昵称(cookie)和后台的memberId(session)，都得有)
     * 
     * @autor zhanghaitao
     * @return logined?true:false;
     */
    public static boolean isLogin() {
        Map<String, Cookie> cookieMap = readCookieMap();
        if (null != cookieMap) {
            // log.info("isLogin---------------- ");
            // log.info("Member.MEMBER_COOKIE_NAME|"+readCookieMap().get(Member.MEMBER_COOKIE_NAME));
            // log.info("isLogin------222222222 ");
            if (readCookieMap().get(Member.MEMBER_COOKIE_NAME) == null) {// 前台昵称为空，也认为没登录

                return false;
            }
        }
        Object userId = getSession().get(SESSION_MARK_MEMBER_USER_ID);// 后台session
        if (null == userId || StringUtils.equals(String.valueOf(userId).trim(), "")) {
            return false;
        }
        return true;
    }

  

    protected static Map<String, Cookie> readCookieMap() {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
            return cookieMap;
        }
        else {
            return null;
        }
    }

    public static void addCookie(String name, String value, Integer maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        ServletActionContext.getResponse().addCookie(cookie);
    }

    private static Long getMemberId() {// 只判断id
        Object memberId = getSession().get(ShopSessionUtil.SESSION_MARK_MEMBER_USER_ID);
        return memberId == null ? -1 : (Long) memberId;
    }



    // 为了静态页面设置cookie，其它地方未用
    private static void setUserCookie(String value) {
        try {
            int time = 0;
            if (value != null) {// 如果value不为空，则认为是真正的生效值
                time = Member.MEMBER_COOKIE_MAX_AGE;
                value = URLEncoder.encode(value, "UTF-8");
            }
            addCookie(Member.MEMBER_COOKIE_NAME, value, time);
            // log.info("setUserCookie------------");
            // log.info("addCookie yong hu ni chen|"+value);
            // log.info("setUserCookie------------22222222");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 为静态页面设置用户id cookie，其它地方未用
     * 
     * @param value
     * @author zhanghaitao
     */
    private static void setUserIdCookie(String value) {
        try {
            int time = 0;
            if (value != null) {// 如果value不为空，则认为是真正的生效值
                time = Member.MEMBER_COOKIE_MAX_AGE;
                value = URLEncoder.encode(value, "UTF-8");
            }
            addCookie(Member.MEMBER_COOKIE_NAME_ID, value, time);
            // log.info("setUserCookie------------");
            // log.info("addCookie yong hu ni chen|"+value);
            // log.info("setUserCookie------------22222222");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
