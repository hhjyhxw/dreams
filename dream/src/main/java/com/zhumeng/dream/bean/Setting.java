package com.zhumeng.dream.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Bean类 - 系统配置
 * 
 * @author zhanghaitao
 */

public class Setting implements Serializable{

	// 货币种类（人民币、美元、欧元、英磅、加拿大元、澳元、卢布、港币、新台币、韩元、新加坡元、新西兰元、日元、马元、瑞士法郎、瑞典克朗、丹麦克朗、兹罗提、挪威克朗、福林、捷克克朗、葡币）
	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	};
	
	// 小数位精确方式（四舍五入、向上取整、向下取整）
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	public enum OperatorType {
		multiply,divide,plus,subtract
	}
	// 库存预占时间点（下订单、订单付款、订单发货）
	public enum StoreFreezeTime {
		order, payment, ship
	}
	
	// 水印位置（无、左上、右上、居中、左下、右下）
	public enum WatermarkPosition {
		no, topLeft, topRight, center, bottomLeft, bottomRight
	}
	public enum ScoreType {
		disable,orderAmount,goodsSet
	}
	public enum RebateType {
		disable,orderAmount,goodsSet
	}
	//上传图片目录分类
	public enum ImageType {
		friendLink, goodsData
	}
	
	public static final String HOT_SEARCH_SEPARATOR = ",";// 热门搜索分隔符
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符
	public static final String LOGO_UPLOAD_NAME = "logo";// Logo图片文件名称(不包含扩张名)
	public static final String DEFAULT_BIG_GOODS_IMAGE_FILE_NAME = "default_big_goods_image";// 默认商品图片（大）文件名称(不包含扩展名)
	public static final String DEFAULT_SMALL_GOODS_IMAGE_FILE_NAME = "default_small_goods_image";// 默认商品图片（小）文件名称(不包含扩展名)
	public static final String  DEFAULT_SMALL_LISTGOODS_IMAGE_FILE_NAM = "default_small_listgoods_image";// 商品列表小图文件名称(不包含扩展名)
	public static final String  DEFAULT_MIDDLE_LISTGOODS_IMAGE_FILE_NAM = "default_middle_listgoods_image";// 商品列表中图文件名称(不包含扩展名)
	public static final String  DEFAULT_SHOW_GOODS_IMAGE_FILE_NAM = "default_show_goods_image";// 商品详细展示图文件名称(不包含扩展名)
	public static final String DEFAULT_THUMBNAIL_GOODS_IMAGE_FILE_NAME = "default_thumbnail_goods_image";// 商品缩略图文件名称(不包含扩展名)
	public static final String WATERMARK_IMAGE_FILE_NAME = "watermark";// 水印图片文件名称(不包含扩展名)
	public static final String UPLOAD_IMAGE_DIR = "/upload/image/";// 图片文件上传目录
	public static final String UPLOAD_MEDIA_DIR = "/upload/media/";// 媒体文件上传目录
	public static final String UPLOAD_FILE_DIR = "/upload/file/";// 其它文件上传目录
    
	private String systemName;// 系统名称
	private String systemVersion;// 系统版本
	private String systemDescription;// 系统描述
	private String contextPath;
	private String imageUploadPath;
	private String imageBrowsePath;
    private Boolean isShowPoweredInfo;
    private String shopName;// 网店名称
    private String shopUrl;// 网店网址
    private String goodsphotoUrl;
    private String shopLogoPath;// 网店Logo
	private String hotSearch;// 热门搜索关键词
	private String metaKeywords;// 首页页面关键词
	private String metaDescription;// 首页页面描述
	private String address;// 联系地址
	private String phone;// 联系电话
	private String zipCode;// 邮编
	private String email;// 联系email
	private String certtext;//备案号
	private CurrencyType currencyType;// 货币种类
	private String currencySign;// 货币符号
	private String currencyUnit;// 货币单位
	private Integer priceScale;// 商品价格精确位数
	private RoundType priceRoundType;// 商品价格精确方式
	private Integer orderScale;// 订单金额精确位数
	private RoundType orderRoundType;// 订单金额精确方式
	private Integer storeAlertCount;// 库存报警数量
	private StoreFreezeTime storeFreezeTime;// 库存预占时间点
	private Integer uploadLimit;// 文件上传最大值,0表示无限制,单位KB
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位：分钟,0表示永久锁定)
	private Boolean isRegisterEnabled;// 是否开放注册
	private String watermarkImagePath; // 水印图片路径
	private WatermarkPosition watermarkPosition; // 水印位置
	private Integer watermarkAlpha;// 水印透明度
	
	private Integer bigGoodsImageWidth;// 商品图片（大）宽度
	private Integer bigGoodsImageHeight;// 商品图片（大）高度
	private Integer showGoodsImageHeight;
	private Integer showGoodsImageWidth;
	private Integer middleGoodsImageHeight;
	private Integer middleGoodsImageWidth;
	private Integer thumbnailGoodsImageWidth;
	private Integer thumbnailGoodsImageHeight;
	
	private String defaultBigGoodsImagePath;// 默认商品原图大图
	private String defaultShowGoodsImagePath;//默认商品详情展示
	private String defaultSmallGoodsImagePath;// 默认商品列表图
	private String defaultThumbnailGoodsImagePath;// 默认缩略图
	
	private Boolean isShowMarketPrice;
	private String defaultMarketPriceOperator;
	private String defaultMarketPriceNumber;
	private String smtpFromMail;// 发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;// SMTP服务器端口
	private String smtpUsername;// SMTP用户名
	private String smtpPassword;// SMTP密码
	private String scoreType;//积分类型
	private Integer scoreScale;//积分比例
	private String rebateType;//返利类型
	private BigDecimal rebateScale;//返利比例
	private Integer rebateCycle;//返利结算周期
	private Integer buildHtmlDelayTime;
	private Integer lbsRadius;//LBS半径
	private Boolean isGzipEnabled;//是否开启GZIP功能
	//评论设置
	private Boolean isCommentEnabled;//是否启用
	private Boolean isCommentCaptchaEnabled;//是否启用验证码
	private String commentAuthority;// 发表评论权限
	private String commentDisplayType;// 显示方式
	//短信接口配置
	private String smsApiUrl;
	private String smsApiUsername;
	private String smsApiPassword;
	
	private String freightTemplate;//默认运费模板
	private Integer orderUnPay;
	private Double orderUnPayMsg;//支付超时提醒 alter by leiyi
	private Integer orderUncompleted;
	private Integer sendMsgTime;
	
	
	private String bBusTy;     //业务类型号
	private String pyrAct;     //付款人账号
	private String pyrNam;     //付款人户名
	private String pyrAdr;     //付款人地址
	private String pMBTyp;      //业务种类编码
	private String ccyCod;      //币种
	private String taxRate;      //税率
	private String agrNo;      //协议号
	
	
	private String partnerNo; //商户号
	private String partnerKey;//支付秘钥
	private String appid; //公众号appid
	private String appsecret; //公众号调用接口的凭证
	private String notifyUrl; //微信支付回调通知URL
	private String payUrl;    //微信支付调起支付URL
	
	private Integer handingFeeScale; //手续费精度
	
	private Integer rateSacle;//费率精度
	
	private String showGiftUrl;//微信礼包详情跳转URL
	
	private String omsChange;//物流一二期切换    1一期 ，2二期
	private String activityUrl;//活动url
	private BigDecimal scoreRate;//积分折算金额率
	
	private BigDecimal longChangeGui;//龙币兑换桂币率
	private BigDecimal guiChangeRmb;//桂币兑换人民币率
	
	private BigDecimal  postageAmount;//邮费
	private BigDecimal  postageAmountLimit;//邮费
	
	
	private String  proxyterOpenidURL;//获取代销员OPENID url
	
	public String getFreightTemplate() {
		return freightTemplate;
	}

	public void setFreightTemplate(String freightTemplate) {
		this.freightTemplate = freightTemplate;
	}

	public Integer getRebateCycle() {
		return rebateCycle;
	}

	public void setRebateCycle(Integer rebateCycle) {
		this.rebateCycle = rebateCycle;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}


	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public Integer getOrderUnPay() {
		return orderUnPay;
	}

	public void setOrderUnPay(Integer orderUnPay) {
		this.orderUnPay = orderUnPay;
	}



	public Double getOrderUnPayMsg() {
		return orderUnPayMsg;
	}

	public void setOrderUnPayMsg(Double orderUnPayMsg) {
		this.orderUnPayMsg = orderUnPayMsg;
	}

	public Integer getOrderUncompleted() {
		return orderUncompleted;
	}

	public void setOrderUncompleted(Integer orderUncompleted) {
		this.orderUncompleted = orderUncompleted;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}


	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public Integer getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	public Integer getOrderScale() {
		return orderScale;
	}

	public void setOrderScale(Integer orderScale) {
		this.orderScale = orderScale;
	}

	public RoundType getOrderRoundType() {
		return orderRoundType;
	}

	public void setOrderRoundType(RoundType orderRoundType) {
		this.orderRoundType = orderRoundType;
	}

	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public Integer getStoreAlertCount() {
		return storeAlertCount;
	}

	public void setStoreAlertCount(Integer storeAlertCount) {
		this.storeAlertCount = storeAlertCount;
	}

	public StoreFreezeTime getStoreFreezeTime() {
		return storeFreezeTime;
	}

	public void setStoreFreezeTime(StoreFreezeTime storeFreezeTime) {
		this.storeFreezeTime = storeFreezeTime;
	}

	public Integer getUploadLimit() {
		return uploadLimit;
	}

	public void setUploadLimit(Integer uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}

	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}

	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	public Integer getBigGoodsImageWidth() {
		return bigGoodsImageWidth;
	}

	public void setBigGoodsImageWidth(Integer bigGoodsImageWidth) {
		this.bigGoodsImageWidth = bigGoodsImageWidth;
	}

	public Integer getBigGoodsImageHeight() {
		return bigGoodsImageHeight;
	}

	public void setBigGoodsImageHeight(Integer bigGoodsImageHeight) {
		this.bigGoodsImageHeight = bigGoodsImageHeight;
	}


	public Integer getShowGoodsImageHeight() {
		return showGoodsImageHeight;
	}

	public void setShowGoodsImageHeight(Integer showGoodsImageHeight) {
		this.showGoodsImageHeight = showGoodsImageHeight;
	}

	public Integer getShowGoodsImageWidth() {
		return showGoodsImageWidth;
	}

	public void setShowGoodsImageWidth(Integer showGoodsImageWidth) {
		this.showGoodsImageWidth = showGoodsImageWidth;
	}

	public Integer getMiddleGoodsImageHeight() {
		return middleGoodsImageHeight;
	}

	public void setMiddleGoodsImageHeight(Integer middleGoodsImageHeight) {
		this.middleGoodsImageHeight = middleGoodsImageHeight;
	}

	public Integer getMiddleGoodsImageWidth() {
		return middleGoodsImageWidth;
	}

	public void setMiddleGoodsImageWidth(Integer middleGoodsImageWidth) {
		this.middleGoodsImageWidth = middleGoodsImageWidth;
	}

	public Integer getThumbnailGoodsImageWidth() {
		return thumbnailGoodsImageWidth;
	}

	public void setThumbnailGoodsImageWidth(Integer thumbnailGoodsImageWidth) {
		this.thumbnailGoodsImageWidth = thumbnailGoodsImageWidth;
	}

	public Integer getThumbnailGoodsImageHeight() {
		return thumbnailGoodsImageHeight;
	}

	public void setThumbnailGoodsImageHeight(Integer thumbnailGoodsImageHeight) {
		this.thumbnailGoodsImageHeight = thumbnailGoodsImageHeight;
	}

	public String getDefaultBigGoodsImagePath() {
		return defaultBigGoodsImagePath;
	}

	public void setDefaultBigGoodsImagePath(String defaultBigGoodsImagePath) {
		this.defaultBigGoodsImagePath = defaultBigGoodsImagePath;
	}

	
	
	public String getDefaultShowGoodsImagePath() {
		return defaultShowGoodsImagePath;
	}

	public void setDefaultShowGoodsImagePath(String defaultShowGoodsImagePath) {
		this.defaultShowGoodsImagePath = defaultShowGoodsImagePath;
	}

	public String getDefaultSmallGoodsImagePath() {
		return defaultSmallGoodsImagePath;
	}

	public void setDefaultSmallGoodsImagePath(String defaultSmallGoodsImagePath) {
		this.defaultSmallGoodsImagePath = defaultSmallGoodsImagePath;
	}

	public String getDefaultThumbnailGoodsImagePath() {
		return defaultThumbnailGoodsImagePath;
	}

	public void setDefaultThumbnailGoodsImagePath(String defaultThumbnailGoodsImagePath) {
		this.defaultThumbnailGoodsImagePath = defaultThumbnailGoodsImagePath;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}


	public Boolean getIsGzipEnabled() {
		return isGzipEnabled;
	}

	public void setIsGzipEnabled(Boolean isGzipEnabled) {
		this.isGzipEnabled = isGzipEnabled;
	}
	
	public Boolean getIsCommentEnabled() {
		return isCommentEnabled;
	}

	public void setIsCommentEnabled(Boolean isCommentEnabled) {
		this.isCommentEnabled = isCommentEnabled;
	}

	public Boolean getIsCommentCaptchaEnabled() {
		return isCommentCaptchaEnabled;
	}

	public void setIsCommentCaptchaEnabled(Boolean isCommentCaptchaEnabled) {
		this.isCommentCaptchaEnabled = isCommentCaptchaEnabled;
	}

	public String getCommentAuthority() {
		return commentAuthority;
	}

	public void setCommentAuthority(String commentAuthority) {
		this.commentAuthority = commentAuthority;
	}

	public String getCommentDisplayType() {
		return commentDisplayType;
	}

	public void setCommentDisplayType(String commentDisplayType) {
		this.commentDisplayType = commentDisplayType;
	}
	
	public String getSmsApiUrl() {
		return smsApiUrl;
	}

	public void setSmsApiUrl(String smsApiUrl) {
		this.smsApiUrl = smsApiUrl;
	}

	public String getSmsApiUsername() {
		return smsApiUsername;
	}

	public void setSmsApiUsername(String smsApiUsername) {
		this.smsApiUsername = smsApiUsername;
	}

	public String getSmsApiPassword() {
		return smsApiPassword;
	}

	public void setSmsApiPassword(String smsApiPassword) {
		this.smsApiPassword = smsApiPassword;
	}
	
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getImageUploadPath() {
		return imageUploadPath;
	}

	public void setImageUploadPath(String imageUploadPath) {
		this.imageUploadPath = imageUploadPath;
	}

	public String getImageBrowsePath() {
		return imageBrowsePath;
	}

	public void setImageBrowsePath(String imageBrowsePath) {
		this.imageBrowsePath = imageBrowsePath;
	}

	public Boolean getIsShowPoweredInfo() {
		return isShowPoweredInfo;
	}

	public void setIsShowPoweredInfo(Boolean isShowPoweredInfo) {
		this.isShowPoweredInfo = isShowPoweredInfo;
	}

	public String getShopLogoPath() {
		return shopLogoPath;
	}

	public void setShopLogoPath(String shopLogoPath) {
		this.shopLogoPath = shopLogoPath;
	}

	public Boolean getIsRegisterEnabled() {
		return isRegisterEnabled;
	}

	public void setIsRegisterEnabled(Boolean isRegisterEnabled) {
		this.isRegisterEnabled = isRegisterEnabled;
	}

	public Boolean getIsShowMarketPrice() {
		return isShowMarketPrice;
	}

	public void setIsShowMarketPrice(Boolean isShowMarketPrice) {
		this.isShowMarketPrice = isShowMarketPrice;
	}

	public String getDefaultMarketPriceOperator() {
		return defaultMarketPriceOperator;
	}

	public void setDefaultMarketPriceOperator(String defaultMarketPriceOperator) {
		this.defaultMarketPriceOperator = defaultMarketPriceOperator;
	}

	public String getDefaultMarketPriceNumber() {
		return defaultMarketPriceNumber;
	}

	public void setDefaultMarketPriceNumber(String defaultMarketPriceNumber) {
		this.defaultMarketPriceNumber = defaultMarketPriceNumber;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public Integer getScoreScale() {
		return scoreScale;
	}

	public void setScoreScale(Integer scoreScale) {
		this.scoreScale = scoreScale;
	}
	
	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public BigDecimal getRebateScale() {
		return rebateScale;
	}

	public void setRebateScale(BigDecimal rebateScale) {
		this.rebateScale = rebateScale;
	}

	public Integer getBuildHtmlDelayTime() {
		return buildHtmlDelayTime;
	}

	public void setBuildHtmlDelayTime(Integer buildHtmlDelayTime) {
		this.buildHtmlDelayTime = buildHtmlDelayTime;
	}

	public Integer getLbsRadius() {
		return lbsRadius;
	}

	public void setLbsRadius(Integer lbsRadius) {
		this.lbsRadius = lbsRadius;
	}
	
	public Integer getSendMsgTime() {
		return sendMsgTime;
	}

	public void setSendMsgTime(Integer sendMsgTime) {
		this.sendMsgTime = sendMsgTime;
	}

	
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	// 获取热门搜索关键词集合
	public List<String> getHotSearchList() {
		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
	}
	

	public String getBBusTy() {
		return bBusTy;
	}

	public void setBBusTy(String bBusTy) {
		this.bBusTy = bBusTy;
	}

	public String getPyrAct() {
		return pyrAct;
	}

	public void setPyrAct(String pyrAct) {
		this.pyrAct = pyrAct;
	}

	public String getPyrNam() {
		return pyrNam;
	}

	public void setPyrNam(String pyrNam) {
		this.pyrNam = pyrNam;
	}

	public String getPyrAdr() {
		return pyrAdr;
	}

	public void setPyrAdr(String pyrAdr) {
		this.pyrAdr = pyrAdr;
	}

	public String getPMBTyp() {
		return pMBTyp;
	}

	public void setPMBTyp(String pMBTyp) {
		this.pMBTyp = pMBTyp;
	}

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}


	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getbBusTy() {
		return bBusTy;
	}

	public void setbBusTy(String bBusTy) {
		this.bBusTy = bBusTy;
	}

	public String getpMBTyp() {
		return pMBTyp;
	}

	public void setpMBTyp(String pMBTyp) {
		this.pMBTyp = pMBTyp;
	}
	
	public String getAgrNo() {
		return agrNo;
	}

	public void setAgrNo(String agrNo) {
		this.agrNo = agrNo;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getGoodsphotoUrl() {
		return goodsphotoUrl;
	}

	public void setGoodsphotoUrl(String goodsphotoUrl) {
		this.goodsphotoUrl = goodsphotoUrl;
	}

	public Integer getHandingFeeScale() {
		return handingFeeScale;
	}

	public void setHandingFeeScale(Integer handingFeeScale) {
		this.handingFeeScale = handingFeeScale;
	}

	public Integer getRateSacle() {
		return rateSacle;
	}

	public void setRateSacle(Integer rateSacle) {
		this.rateSacle = rateSacle;
	}

	public String getShowGiftUrl() {
		return showGiftUrl;
	}

	public void setShowGiftUrl(String showGiftUrl) {
		this.showGiftUrl = showGiftUrl;
	}

	public String getOmsChange() {
		return omsChange;
	}

	public void setOmsChange(String omsChange) {
		this.omsChange = omsChange;
	}

	public String getActivityUrl() {
		return activityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	public BigDecimal getScoreRate() {
		return scoreRate;
	}

	public void setScoreRate(BigDecimal scoreRate) {
		this.scoreRate = scoreRate;
	}

    public BigDecimal getPostageAmount() {
        return postageAmount;
    }

    public void setPostageAmount(BigDecimal postageAmount) {
        this.postageAmount = postageAmount;
    }

    public String getProxyterOpenidURL() {
        return proxyterOpenidURL;
    }

    public void setProxyterOpenidURL(String proxyterOpenidURL) {
        this.proxyterOpenidURL = proxyterOpenidURL;
    }

	public BigDecimal getLongChangeGui() {
		return longChangeGui;
	}

	public void setLongChangeGui(BigDecimal longChangeGui) {
		this.longChangeGui = longChangeGui;
	}

	public BigDecimal getGuiChangeRmb() {
		return guiChangeRmb;
	}

	public void setGuiChangeRmb(BigDecimal guiChangeRmb) {
		this.guiChangeRmb = guiChangeRmb;
	}

    public BigDecimal getPostageAmountLimit() {
        return postageAmountLimit;
    }

    public void setPostageAmountLimit(BigDecimal postageAmountLimit) {
        this.postageAmountLimit = postageAmountLimit;
    }
	
}