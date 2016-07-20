package com.zhumeng.dream.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "T_MEMBER")
public class Member extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	public static final String PASSWORD_RECOVER_MOBILE_VERIFY_CODE="repwdmobileVerifyCode";
	public static final String PASSWORD_RECOVER_EMAIL_VERIFY_CODE="repwdemailVerifyCode";
	
	/**保存登录会员ID的Session名称*/
	public static final String MEMBER_USER_ID_SESSION_NAME = "memberUserId";
	/**保存登录来源URL的Session名称*/
	public static final String LOGIN_REDIRECTION_URL_SESSION_NAME = "redirectUrl";
	public static final String MEMBER_UNION_ID_SESSION_NAME="memberUnionId";
	/**保存二次认证获取的openId */
	public static final String MEMBER_OPEN_ID_SESSION_NAME="memberOpenId";
	
	/**保存二次认证获取的gh(微信公共号) */
	public static final String MEMBER_GH_SESSION_NAME="memberGh";
	
	/**手机认证cookie验证码标识*/
	public static final String MEMBER_MOBILE_COOKIE_NAME = "mobileVerifyCode";
	
	/**用户cookie验证码标识*/
	public static final String MEMBER_COOKIE_NAME = "memberUsername";
	
	/**用户id cookie标识*/
	public static final String MEMBER_COOKIE_NAME_ID = "memberUsernameId";
	
	/**保存未登录会员购物车项集合的Cookie最大有效时间（单位：秒）*/
	public static final int MEMBER_COOKIE_MAX_AGE = 72000;
	
	/**附近小店的菜单点进来的值1保存到Session*/
	public static final String MEMBER_MENU_LOADING = "menuLoading";
	
	public static final String MEMBER_WX_USER_INFO = "wxUserInfo"; 
	/**
	 * 默认密码
	 */
	public static final String MEMBER_DEFAULT_PASSWORD = "123456"; 
	/**
	 * 手机登录类型
	 */
	public static final String MEMBER_LOGINTYPE_MB = "mb"; 
	public static final String MEMBER_LOGINTYPE_WX = "wx"; 
	
	
//	
//	/**用户微信经度*/
//	public static final String MEMBER_WEIXINLNG = "member_weixinlng";
//	
//	/**用户微信纬度*/
//	public static final String MEMBER_WEIXINLAT = "member_weixinlat";

	private String address;
	private String areaStore;
	/**生日*/
	private Date birth;
	/**余额 */
	private BigDecimal deposit;
	//private String email;
	//private String emailVerifyCode;
	private Integer gender;//1男 2女
	private Boolean isAccountEnabled=true;
	private Boolean isAccountLocked=false;
	private Date lockedDate;
	private Date loginDate;
	private Integer loginFailureCount;
	private String loginIp;
	//private String memberRankId;
	private String mobile;
	private String nick;
	private String password;
	private String passwordRecoverKey;
	//private String phone;
	private String registerIp;
	//private String safeAnswer;
	//private String safeQuestion;
	private Integer score;    //总积分，不会因积分兑换而减少
	private Integer usableScore;  //可用积分，用于积分兑换，数值会变动
	//private Boolean isEmailActivate=false;
	private Boolean isMobileActivate=false;
	private String mobileVerifyCode;
	private Long gradeId;
	private String gradeName;
	private String userName;
	private String openId;
    private double latitude; //纬度
    private double longitude;//经度
    private String weiXinGh;//gh号
	private String bd09Lng;
	private String bd09Lat;
	private String headImgUrl;//微信头像url
	private String unionId;//微信unionid
	/**个人中心的福利导航是否显示  0不显示    1显示 */
	//private Integer welfare;
	/**所在公司或者门店的名字*/
	private String companyName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsMobileActivate() {
		return isMobileActivate;
	}

	public void setIsMobileActivate(Boolean isMobileActivate) {
		this.isMobileActivate = isMobileActivate;
	}

	public String getMobileVerifyCode() {
		return mobileVerifyCode;
	}

	public void setMobileVerifyCode(String mobileVerifyCode) {
		this.mobileVerifyCode = mobileVerifyCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaStore() {
		return areaStore;
	}

	public void setAreaStore(String areaStore) {
		this.areaStore = areaStore;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

//	public String getMemberRankId() {
//		return memberRankId;
//	}
//
//	public void setMemberRankId(String memberRankId) {
//		this.memberRankId = memberRankId;
//	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

//	public String getSafeAnswer() {
//		return safeAnswer;
//	}
//
//	public void setSafeAnswer(String safeAnswer) {
//		this.safeAnswer = safeAnswer;
//	}
//
//	public String getSafeQuestion() {
//		return safeQuestion;
//	}
//
//	public void setSafeQuestion(String safeQuestion) {
//		this.safeQuestion = safeQuestion;
//	}

	public Integer getScore() {
		if(score==null)
			score=0;
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public Integer getUsableScore() {
		if(usableScore==null)
			usableScore=0;
		return usableScore;
	}

	public void setUsableScore(Integer usableScore) {
		this.usableScore = usableScore;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getWeiXinGh() {
		return weiXinGh;
	}

	public void setWeiXinGh(String weiXinGh) {
		this.weiXinGh = weiXinGh;
	}
	@Transient
	public String getBd09Lng() {
		return bd09Lng;
	}

	public void setBd09Lng(String bd09Lng) {
		this.bd09Lng = bd09Lng;
	}
	@Transient
	public String getBd09Lat() {
		return bd09Lat;
	}

	public void setBd09Lat(String bd09Lat) {
		this.bd09Lat = bd09Lat;
	}
	
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * 获取随机密码
	 * @autor zhanghaitao
	 * @return
	 */
	public static String getRandomPwd(){
		//String str="01234 56789 abcde fghig klmno pqrst uvwxy zABCD EFGHI GKLMN OPQRS TUVWX YZ!@# $%^&* ()_+";
		String str="0123456789";
		//int pwdLength=10;
		int pwdLength=6;
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		int nowCount=0;
		for(int i=0;i<pwdLength;i++){
			//nowCount=random.nextInt(73);//73 =字符串总长度减1
			nowCount=random.nextInt(10);
			sb.append(str.substring(nowCount, nowCount+1));
		}
		return sb.toString();
	}

//	public Integer getWelfare() {
//		return welfare;
//	}
//
//	public void setWelfare(Integer welfare) {
//		this.welfare = welfare;
//	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	

	
}
