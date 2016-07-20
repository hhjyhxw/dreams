package com.zhumeng.dream.util.others;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import com.zhumeng.dream.bean.Setting;
import com.zhumeng.dream.bean.Setting.RoundType;
import com.zhumeng.dream.util.ehcache.EhCacheConfigUtil;
/**
 * 工具类 - 系统配置
 * 
 * @author 
 */

public class SettingUtil {
	
	public static final String SETTING_FILE_NAME = "mall-config.xml";// 系统配置文件名称
	public static final String SETTING_CACHE_KEY = "setting";// settging缓存Key
	/**
	 * 获取系统配置信息
	 * 
	 * @return Setting对象
	 */
	public static Setting getSetting() {
		
		Setting setting = (Setting) EhCacheConfigUtil.getFromCache(SETTING_CACHE_KEY);
		
		if (setting != null) {
			return setting;
		}
		File settingFile = null;
		Document document = null;
		try {
			String settingFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + SETTING_FILE_NAME;
			settingFile = new File(settingFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(settingFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Node freightTemplateNode = document.selectSingleNode("/mall/setting/freightTemplate");
		Node systemNameNode = document.selectSingleNode("/mall/setting/systemName");
		Node systemVersionNode = document.selectSingleNode("/mall/setting/systemVersion");
		Node systemDescriptionNode = document.selectSingleNode("/mall/setting/systemDescription");
		Node contextPathNode = document.selectSingleNode("/mall/setting/contextPath");
		Node imageUploadPathNode = document.selectSingleNode("/mall/setting/imageUploadPath");
		Node imageBrowsePathNode = document.selectSingleNode("/mall/setting/imageBrowsePath");
		Node isShowPoweredInfoNode = document.selectSingleNode("/mall/setting/isShowPoweredInfo");
		Node shopNameNode = document.selectSingleNode("/mall/setting/shopName");
		Node shopUrlNode = document.selectSingleNode("/mall/setting/shopUrl");
		Node goodsphotoUrlNode = document.selectSingleNode("/mall/setting/goodsphotoUrl");
		Node shopLogoPathNode = document.selectSingleNode("/mall/setting/shopLogoPath");
		Node hotSearchNode = document.selectSingleNode("/mall/setting/hotSearch");
		Node metaKeywordsNode = document.selectSingleNode("/mall/setting/metaKeywords");
		Node metaDescriptionNode = document.selectSingleNode("/mall/setting/metaDescription");
		Node addressNode = document.selectSingleNode("/mall/setting/address");
		Node phoneNode = document.selectSingleNode("/mall/setting/phone");
		Node zipCodeNode = document.selectSingleNode("/mall/setting/zipCode");
		Node emailNode = document.selectSingleNode("/mall/setting/email");
		Node certtextNode = document.selectSingleNode("/mall/setting/certtext");
		Node currencyTypeNode = document.selectSingleNode("/mall/setting/currencyType");
		Node currencySignNode = document.selectSingleNode("/mall/setting/currencySign");
		Node currencyUnitNode = document.selectSingleNode("/mall/setting/currencyUnit");
		Node priceScaleNode = document.selectSingleNode("/mall/setting/priceScale");
		Node handingFeeScaleNode = document.selectSingleNode("/mall/setting/handingFeeScale");
		Node rateSacleNode = document.selectSingleNode("/mall/setting/rateSacle");
		Node showGiftUrlNode = document.selectSingleNode("/mall/setting/showGiftUrl");
		Node priceRoundTypeNode = document.selectSingleNode("/mall/setting/priceRoundType");
		Node storeAlertCountNode = document.selectSingleNode("/mall/setting/storeAlertCount");
		Node storeFreezeTimeNode = document.selectSingleNode("/mall/setting/storeFreezeTime");
		Node uploadLimitNode = document.selectSingleNode("/mall/setting/uploadLimit");
		Node isLoginFailureLockNode = document.selectSingleNode("/mall/setting/isLoginFailureLock");
		Node loginFailureLockCountNode = document.selectSingleNode("/mall/setting/loginFailureLockCount");
		Node loginFailureLockTimeNode = document.selectSingleNode("/mall/setting/loginFailureLockTime");
		Node isRegisterEnabledNode = document.selectSingleNode("/mall/setting/isRegisterEnabled");
		Node watermarkImagePathNode = document.selectSingleNode("/mall/setting/watermarkImagePath");
		Node watermarkPositionNode = document.selectSingleNode("/mall/setting/watermarkPosition");
		Node watermarkAlphaNode = document.selectSingleNode("/mall/setting/watermarkAlpha");
		Node bigGoodsImageWidthNode = document.selectSingleNode("/mall/setting/bigGoodsImageWidth");
		Node bigGoodsImageHeightNode = document.selectSingleNode("/mall/setting/bigGoodsImageHeight");
		Node showGoodsImageWidthNode = document.selectSingleNode("/mall/setting/showGoodsImageWidth");
		Node showGoodsImageHeightNode = document.selectSingleNode("/mall/setting/showGoodsImageHeight");
		Node middleGoodsImageWidthNode = document.selectSingleNode("/mall/setting/middleGoodsImageWidth");
		Node middleGoodsImageHeightNode = document.selectSingleNode("/mall/setting/middleGoodsImageHeight");
//		Node smallGoodsImageWidthNode = document.selectSingleNode("/mall/setting/smallGoodsImageWidth");
//		Node smallGoodsImageHeightNode = document.selectSingleNode("/mall/setting/smallGoodsImageHeight");
		Node thumbnailGoodsImageWidthNode = document.selectSingleNode("/mall/setting/thumbnailGoodsImageWidth");
		Node thumbnailGoodsImageHeightNode = document.selectSingleNode("/mall/setting/thumbnailGoodsImageHeight");
		Node defaultBigGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultBigGoodsImagePath");
		Node defaultShowGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultShowGoodsImagePath");
		Node defaultSmallGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultSmallGoodsImagePath");
		Node defaultThumbnailGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultThumbnailGoodsImagePath");
		Node isShowMarketPriceNode = document.selectSingleNode("/mall/setting/isShowMarketPrice");
		Node defaultMarketPriceOperatorNode = document.selectSingleNode("/mall/setting/defaultMarketPriceOperator");
		Node defaultMarketPriceNumberNode = document.selectSingleNode("/mall/setting/defaultMarketPriceNumber");
		Node smtpFromMailNode = document.selectSingleNode("/mall/setting/smtpFromMail");
		Node smtpHostNode = document.selectSingleNode("/mall/setting/smtpHost");
		Node smtpPortNode = document.selectSingleNode("/mall/setting/smtpPort");
		Node smtpUsernameNode = document.selectSingleNode("/mall/setting/smtpUsername");
		Node smtpPasswordNode = document.selectSingleNode("/mall/setting/smtpPassword");
		Node scoreTypeNode = document.selectSingleNode("/mall/setting/scoreType");
		Node scoreScaleNode = document.selectSingleNode("/mall/setting/scoreScale");
		
		Node rebateTypeNode = document.selectSingleNode("/mall/setting/rebateType");
		Node rebateScaleNode = document.selectSingleNode("/mall/setting/rebateScale");
		Node rebateCycleNode = document.selectSingleNode("/mall/setting/rebateCycle");
		
		
		Node buildHtmlDelayTimeNode = document.selectSingleNode("/mall/setting/buildHtmlDelayTime");
		Node lbsRadiusNode = document.selectSingleNode("/mall/setting/lbsRadius");//zht
		Node isGzipEnabledNode = document.selectSingleNode("/mall/setting/isGzipEnabled");
		Node isCommentEnabledNode = document.selectSingleNode("/mall/setting/isCommentEnabled");
		Node isCommentCaptchaEnabledNode = document.selectSingleNode("/mall/setting/isCommentCaptchaEnabled");
		Node commentAuthorityNode = document.selectSingleNode("/mall/setting/commentAuthority");
		Node commentDisplayTypeNode = document.selectSingleNode("/mall/setting/commentDisplayType");
		Node smsApiUrlNode = document.selectSingleNode("/mall/setting/smsApiUrl");
		Node smsApiUsernameNode = document.selectSingleNode("/mall/setting/smsApiUsername");
		Node smsApiPasswordNode = document.selectSingleNode("/mall/setting/smsApiPassword");
		Node orderUnPayNode = document.selectSingleNode("/mall/setting/orderUnPay");
		Node orderUnPayMsgNode = document.selectSingleNode("/mall/setting/orderUnPayMsg");
		Node orderUncompletedNode = document.selectSingleNode("/mall/setting/orderUncompleted");
		Node sendMsgTimeNode = document.selectSingleNode("/mall/setting/sendMsgTime");
		Node partnerNoNode = document.selectSingleNode("/mall/setting/partnerNo");
		Node partnerKeyNode = document.selectSingleNode("/mall/setting/partnerKey");
		Node appidNode = document.selectSingleNode("/mall/setting/appid");
		Node appsecretNode = document.selectSingleNode("/mall/setting/appsecret");
		Node notifyUrlNode = document.selectSingleNode("/mall/setting/notifyUrl");
		Node payUrlNode    = document.selectSingleNode("/mall/setting/payUrl");
		
		Node bBusTyNode = document.selectSingleNode("/mall/setting/bBusTy");   //pl
		Node pyrActNode = document.selectSingleNode("/mall/setting/pyrAct");
		Node pyrNamNode = document.selectSingleNode("/mall/setting/pyrNam");
		Node pyrAdrNode = document.selectSingleNode("/mall/setting/pyrAdr");
		Node pMBTypNode = document.selectSingleNode("/mall/setting/pMBTyp");
		Node ccyCodNode = document.selectSingleNode("/mall/setting/ccyCod");
		Node taxRateNode = document.selectSingleNode("/mall/setting/taxRate");
		Node agrNoNode = document.selectSingleNode("/mall/setting/agrNo");
		Node omsChangeNode = document.selectSingleNode("/mall/setting/omsChange");//物流一、二期切换开关
		Node activityUrlNode = document.selectSingleNode("/mall/setting/activityUrl");//活动url
		Node scoreRateNode = document.selectSingleNode("/mall/setting/scoreRate");//积分折算金额率
		Node postageAmountNode = document.selectSingleNode("/mall/setting/postageAmount");//邮费
		Node postageAmountLimitNode = document.selectSingleNode("/mall/setting/postageAmountLimit");//邮费额度设置
		  //TODO
	    Node proxyterOpenidURL = document.selectSingleNode("/mall/setting/proxyterOpenidURL");//获取代销员OPENID url
	    
		Node longChangeGui = document.selectSingleNode("/mall/setting/longChangeGui");//龙币兑换桂币率
	    Node guiChangeRmb = document.selectSingleNode("/mall/setting/guiChangeRmb");//桂币兑换人民币率
	        
	       
	
		
		setting = new Setting();
		setting.setFreightTemplate(freightTemplateNode.getText());
		setting.setSystemName(systemNameNode.getText());
		setting.setSystemVersion(systemVersionNode.getText());
		setting.setSystemDescription(systemDescriptionNode.getText());
		setting.setContextPath(contextPathNode.getText());
		setting.setImageUploadPath(imageUploadPathNode.getText());
		setting.setImageBrowsePath(imageBrowsePathNode.getText());
		setting.setIsShowPoweredInfo(Boolean.valueOf(isShowPoweredInfoNode.getText()));
		setting.setShopName(shopNameNode.getText());
		setting.setShopUrl(shopUrlNode.getText());
		setting.setGoodsphotoUrl(goodsphotoUrlNode.getText());
		setting.setShopLogoPath(shopLogoPathNode.getText());
		setting.setHotSearch(hotSearchNode.getText());
		setting.setMetaKeywords(metaKeywordsNode.getText());
		setting.setMetaDescription(metaDescriptionNode.getText());
		setting.setAddress(addressNode.getText());
		setting.setPhone(phoneNode.getText());
		setting.setZipCode(zipCodeNode.getText());
		setting.setEmail(emailNode.getText());
		setting.setCerttext(certtextNode.getText());
		setting.setCurrencySign(currencySignNode.getText());
		setting.setCurrencyUnit(currencyUnitNode.getText());
		setting.setPriceScale(Integer.valueOf(priceScaleNode.getText()));
		setting.setRateSacle(Integer.parseInt(rateSacleNode.getText()));
		setting.setShowGiftUrl(showGiftUrlNode.getText());
		setting.setHandingFeeScale(Integer.parseInt(handingFeeScaleNode.getText()));
		setting.setPriceRoundType(RoundType.valueOf(priceRoundTypeNode.getText()));
		setting.setStoreAlertCount(Integer.parseInt(storeAlertCountNode.getText()));
		setting.setUploadLimit(Integer.parseInt(uploadLimitNode.getText()));
		setting.setIsLoginFailureLock(Boolean.valueOf(isLoginFailureLockNode.getText()));
		setting.setLoginFailureLockCount(Integer.valueOf(loginFailureLockCountNode.getText()));
		setting.setLoginFailureLockTime(Integer.parseInt(loginFailureLockTimeNode.getText()));
		setting.setIsRegisterEnabled(Boolean.valueOf(isRegisterEnabledNode.getText()));
		setting.setWatermarkImagePath(watermarkImagePathNode.getText());
		setting.setWatermarkAlpha(Integer.parseInt(watermarkAlphaNode.getText()));
		setting.setBigGoodsImageWidth(Integer.parseInt(bigGoodsImageWidthNode.getText()));
		setting.setBigGoodsImageHeight(Integer.parseInt(bigGoodsImageHeightNode.getText()));
		setting.setShowGoodsImageWidth(Integer.parseInt(showGoodsImageWidthNode.getText()));
		setting.setShowGoodsImageHeight(Integer.parseInt(showGoodsImageHeightNode.getText()));
		setting.setMiddleGoodsImageWidth(Integer.parseInt(middleGoodsImageWidthNode.getText()));
		setting.setMiddleGoodsImageHeight(Integer.parseInt(middleGoodsImageHeightNode.getText()));
//		setting.setSmallGoodsImageWidth(Integer.parseInt(smallGoodsImageWidthNode.getText()));
//		setting.setSmallGoodsImageHeight(Integer.parseInt(smallGoodsImageHeightNode.getText()));
		setting.setThumbnailGoodsImageWidth(Integer.parseInt(thumbnailGoodsImageWidthNode.getText()));
		setting.setThumbnailGoodsImageHeight(Integer.parseInt(thumbnailGoodsImageHeightNode.getText()));
		setting.setDefaultBigGoodsImagePath(defaultBigGoodsImagePathNode.getText());
		setting.setDefaultShowGoodsImagePath(defaultShowGoodsImagePathNode.getText());
		setting.setDefaultSmallGoodsImagePath(defaultSmallGoodsImagePathNode.getText());
		setting.setDefaultThumbnailGoodsImagePath(defaultThumbnailGoodsImagePathNode.getText());
		setting.setIsShowMarketPrice(Boolean.valueOf(isShowMarketPriceNode.getText()));
		setting.setDefaultMarketPriceOperator(defaultMarketPriceOperatorNode.getText());
		setting.setDefaultMarketPriceNumber(defaultMarketPriceNumberNode.getText());
		setting.setSmtpFromMail(smtpFromMailNode.getText());
		setting.setSmtpHost(smtpHostNode.getText());
		setting.setSmtpPort(Integer.parseInt(smtpPortNode.getText()));
		setting.setSmtpUsername(smtpUsernameNode.getText());
		setting.setSmtpPassword(smtpPasswordNode.getText());
		setting.setScoreType(scoreTypeNode.getText());
		setting.setScoreScale(Integer.parseInt(scoreScaleNode.getText()));
		//setting.setRebateType(rebateTypeNode.getText());
		//setting.setRebateScale(new BigDecimal(rebateScaleNode.getText()));
		//setting.setRebateCycle(Integer.parseInt(rebateCycleNode.getText()));
		setting.setBuildHtmlDelayTime(Integer.parseInt(buildHtmlDelayTimeNode.getText()));
		setting.setLbsRadius(Integer.parseInt(lbsRadiusNode.getText()));//zht
		setting.setIsGzipEnabled(Boolean.valueOf(isGzipEnabledNode.getText()));
		setting.setIsCommentEnabled(Boolean.valueOf(isCommentEnabledNode.getText()));
		setting.setIsCommentCaptchaEnabled(Boolean.valueOf(isCommentCaptchaEnabledNode.getText()));
		setting.setCommentAuthority(commentAuthorityNode.getText());
		setting.setCommentDisplayType(commentDisplayTypeNode.getText());
		setting.setSmsApiUrl(smsApiUrlNode.getText());
		setting.setSmsApiUsername(smsApiUsernameNode.getText());
		setting.setSmsApiPassword(smsApiPasswordNode.getText());
		setting.setOrderUnPay(Integer.parseInt(orderUnPayNode.getText()));
		setting.setOrderUnPayMsg(Double.parseDouble(orderUnPayMsgNode.getText()));
		setting.setOrderUncompleted(Integer.parseInt(orderUncompletedNode.getText()));
		setting.setSendMsgTime(Integer.parseInt(sendMsgTimeNode.getText()));
		setting.setPartnerNo(partnerNoNode.getText());
		setting.setPartnerKey(partnerKeyNode.getText());
		setting.setAppid(appidNode.getText());
		setting.setAppsecret(appsecretNode.getText());
		setting.setNotifyUrl(notifyUrlNode.getText());
		setting.setPayUrl(payUrlNode.getText());
		
		setting.setBBusTy(bBusTyNode.getText());  //pl
		setting.setPyrAct(pyrActNode.getText());
		setting.setPyrNam(pyrNamNode.getText());
		setting.setPyrAdr(pyrAdrNode.getText());
		setting.setPMBTyp(pMBTypNode.getText());
		setting.setCcyCod(ccyCodNode.getText());
		setting.setTaxRate(taxRateNode.getText());
		setting.setAgrNo(agrNoNode.getText());
		setting.setOmsChange(omsChangeNode.getText());
		setting.setActivityUrl(activityUrlNode.getText());
		setting.setScoreRate(new BigDecimal(scoreRateNode.getText()));
		setting.setPostageAmount(new BigDecimal(postageAmountNode.getText()));
		setting.setPostageAmountLimit(new BigDecimal(postageAmountLimitNode.getText()));
		setting.setProxyterOpenidURL(proxyterOpenidURL.getText());
		setting.setLongChangeGui(new BigDecimal(longChangeGui.getText()));
		setting.setGuiChangeRmb(new BigDecimal(guiChangeRmb.getText()));
		
		EhCacheConfigUtil.putInCache(SETTING_CACHE_KEY, setting);
		return setting;
	}
	
	/**
	 * 更新系统配置信息
	 * 
	 * @param setting
	 *          SystemConfig对象
	 */
	public static void update(Setting setting) {
		File settingFile = null;
		Document document = null;
		try {
			String settingFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + SETTING_FILE_NAME;
			settingFile = new File(settingFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(settingFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		Element settingElement = rootElement.element("setting");
		
		
		Node freightTemplateNode = document.selectSingleNode("/mall/setting/freightTemplate");
		Node systemNameNode = document.selectSingleNode("/mall/setting/systemName");
		Node systemVersionNode = document.selectSingleNode("/mall/setting/systemVersion");
		Node systemDescriptionNode = document.selectSingleNode("/mall/setting/systemDescription");
		Node contextPathNode = document.selectSingleNode("/mall/setting/contextPath");
		Node imageUploadPathNode = document.selectSingleNode("/mall/setting/imageUploadPath");
		Node imageBrowsePathNode = document.selectSingleNode("/mall/setting/imageBrowsePath");
		Node isShowPoweredInfoNode = document.selectSingleNode("/mall/setting/isShowPoweredInfo");
		Node shopNameNode = document.selectSingleNode("/mall/setting/shopName");
		Node shopUrlNode = document.selectSingleNode("/mall/setting/shopUrl");
		Node goodsphotoUrlNode = document.selectSingleNode("/mall/setting/goodsphotoUrl");
		Node shopLogoPathNode = document.selectSingleNode("/mall/setting/shopLogoPath");
		Node hotSearchNode = document.selectSingleNode("/mall/setting/hotSearch");
		Node metaKeywordsNode = document.selectSingleNode("/mall/setting/metaKeywords");
		Node metaDescriptionNode = document.selectSingleNode("/mall/setting/metaDescription");
		Node addressNode = document.selectSingleNode("/mall/setting/address");
		Node phoneNode = document.selectSingleNode("/mall/setting/phone");
		Node zipCodeNode = document.selectSingleNode("/mall/setting/zipCode");
		Node emailNode = document.selectSingleNode("/mall/setting/email");
		Node certtextNode = document.selectSingleNode("/mall/setting/certtext");
		Node currencyTypeNode = document.selectSingleNode("/mall/setting/currencyType");
		Node currencySignNode = document.selectSingleNode("/mall/setting/currencySign");
		Node currencyUnitNode = document.selectSingleNode("/mall/setting/currencyUnit");
		Node priceScaleNode = document.selectSingleNode("/mall/setting/priceScale");
		Node handingFeeScaleNode = document.selectSingleNode("/mall/setting/handingFeeScale");
		Node rateSacleNode = document.selectSingleNode("/mall/setting/rateSacle");
		Node showGiftUrlNode = document.selectSingleNode("/mall/setting/showGiftUrl");
		Node priceRoundTypeNode = document.selectSingleNode("/mall/setting/priceRoundType");
		Node storeAlertCountNode = document.selectSingleNode("/mall/setting/storeAlertCount");
		Node storeFreezeTimeNode = document.selectSingleNode("/mall/setting/storeFreezeTime");
		Node uploadLimitNode = document.selectSingleNode("/mall/setting/uploadLimit");
		Node isLoginFailureLockNode = document.selectSingleNode("/mall/setting/isLoginFailureLock");
		Node loginFailureLockCountNode = document.selectSingleNode("/mall/setting/loginFailureLockCount");
		Node loginFailureLockTimeNode = document.selectSingleNode("/mall/setting/loginFailureLockTime");
		Node isRegisterEnabledNode = document.selectSingleNode("/mall/setting/isRegisterEnabled");
		Node watermarkImagePathNode = document.selectSingleNode("/mall/setting/watermarkImagePath");
		Node watermarkPositionNode = document.selectSingleNode("/mall/setting/watermarkPosition");
		Node watermarkAlphaNode = document.selectSingleNode("/mall/setting/watermarkAlpha");
		Node bigGoodsImageWidthNode = document.selectSingleNode("/mall/setting/bigGoodsImageWidth");
		Node bigGoodsImageHeightNode = document.selectSingleNode("/mall/setting/bigGoodsImageHeight");
		Node showGoodsImageWidthNode = document.selectSingleNode("/mall/setting/showGoodsImageWidth");
		Node showGoodsImageHeightNode = document.selectSingleNode("/mall/setting/showGoodsImageHeight");
		Node middleGoodsImageWidthNode = document.selectSingleNode("/mall/setting/middleGoodsImageWidth");
		Node middleGoodsImageHeightNode = document.selectSingleNode("/mall/setting/middleGoodsImageHeight");
//		Node smallGoodsImageWidthNode = document.selectSingleNode("/mall/setting/smallGoodsImageWidth");
//		Node smallGoodsImageHeightNode = document.selectSingleNode("/mall/setting/smallGoodsImageHeight");
		Node thumbnailGoodsImageWidthNode = document.selectSingleNode("/mall/setting/thumbnailGoodsImageWidth");
		Node thumbnailGoodsImageHeightNode = document.selectSingleNode("/mall/setting/thumbnailGoodsImageHeight");
		Node defaultBigGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultBigGoodsImagePath");
		Node defaultShowGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultShowGoodsImagePath");
		Node defaultSmallGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultSmallGoodsImagePath");
		Node defaultThumbnailGoodsImagePathNode = document.selectSingleNode("/mall/setting/defaultThumbnailGoodsImagePath");
		Node isShowMarketPriceNode = document.selectSingleNode("/mall/setting/isShowMarketPrice");
		Node defaultMarketPriceOperatorNode = document.selectSingleNode("/mall/setting/defaultMarketPriceOperator");
		Node defaultMarketPriceNumberNode = document.selectSingleNode("/mall/setting/defaultMarketPriceNumber");
		Node smtpFromMailNode = document.selectSingleNode("/mall/setting/smtpFromMail");
		Node smtpHostNode = document.selectSingleNode("/mall/setting/smtpHost");
		Node smtpPortNode = document.selectSingleNode("/mall/setting/smtpPort");
		Node smtpUsernameNode = document.selectSingleNode("/mall/setting/smtpUsername");
		Node smtpPasswordNode = document.selectSingleNode("/mall/setting/smtpPassword");
		Node scoreTypeNode = document.selectSingleNode("/mall/setting/scoreType");
		Node scoreScaleNode = document.selectSingleNode("/mall/setting/scoreScale");
		Node rebateTypeNode = document.selectSingleNode("/mall/setting/rebateType");
		Node rebateScaleNode = document.selectSingleNode("/mall/setting/rebateScale");
		Node rebateCycleNode = document.selectSingleNode("/mall/setting/rebateCycle");
		Node buildHtmlDelayTimeNode = document.selectSingleNode("/mall/setting/buildHtmlDelayTime");
		Node lbsRadiusNode = document.selectSingleNode("/mall/setting/lbsRadius");//zht
		Node isGzipEnabledNode = document.selectSingleNode("/mall/setting/isGzipEnabled");
		Node isCommentEnabledNode = document.selectSingleNode("/mall/setting/isCommentEnabled");
		Node isCommentCaptchaEnabledNode = document.selectSingleNode("/mall/setting/isCommentCaptchaEnabled");
		Node commentAuthorityNode = document.selectSingleNode("/mall/setting/commentAuthority");
		Node commentDisplayTypeNode = document.selectSingleNode("/mall/setting/commentDisplayType");
		Node smsApiUrlNode = document.selectSingleNode("/mall/setting/smsApiUrl");
		Node smsApiUsernameNode = document.selectSingleNode("/mall/setting/smsApiUsername");
		Node smsApiPasswordNode = document.selectSingleNode("/mall/setting/smsApiPassword");
		Node orderUnPayNode = document.selectSingleNode("/mall/setting/orderUnPay");
		Node orderUnPayMsgNode = document.selectSingleNode("/mall/setting/orderUnPayMsg");
		Node orderUncompletedNode = document.selectSingleNode("/mall/setting/orderUncompleted");
		Node sendMsgTimeNode =  document.selectSingleNode("/mall/setting/sendMsgTime");
		Node partnerNoNode  = document.selectSingleNode("/mall/setting/partnerNo");
		Node partnerKeyNode = document.selectSingleNode("/mall/setting/partnerKey");
		Node appidNode      = document.selectSingleNode("/mall/setting/appid");
		Node appsecretNode  = document.selectSingleNode("/mall/setting/appsecret");
		Node notifyUrlNode  = document.selectSingleNode("/mall/setting/notifyUrl");
		Node payUrlNode     = document.selectSingleNode("/mall/setting/payUrl");
		
		Node bBusTyNode = document.selectSingleNode("/mall/setting/bBusTy");
		Node pyrActNode = document.selectSingleNode("/mall/setting/pyrAct");
		Node pyrNamNode = document.selectSingleNode("/mall/setting/pyrNam");
		Node pyrAdrNode = document.selectSingleNode("/mall/setting/pyrAdr");
		Node pMBTypNode = document.selectSingleNode("/mall/setting/pMBTyp");
		Node ccyCodNode = document.selectSingleNode("/mall/setting/ccyCod");
		Node taxRateNode = document.selectSingleNode("/mall/setting/taxRate");
		Node agrNoNode = document.selectSingleNode("/mall/setting/agrNo");
		Node omsChangeNode = document.selectSingleNode("/mall/setting/omsChange");
		Node activityUrlNode = document.selectSingleNode("/mall/setting/activityUrl");
		Node scoreRateNode = document.selectSingleNode("/mall/setting/scoreRate");//积分折算金额率
		Node postageAmountNode  = document.selectSingleNode("/mall/setting/postageAmount");
		//TODO
		//邮费额度设置
		Node postageAmountLimitNode = document.selectSingleNode("/mall/setting/postageAmountLimit");
		
		Node longChangeGuiNode = document.selectSingleNode("/mall/setting/longChangeGui");//龙币兑换桂币率
	    Node guiChangeRmbNode = document.selectSingleNode("/mall/setting/guiChangeRmb");//桂币兑换人民币率
	
		
		if(freightTemplateNode == null)freightTemplateNode = settingElement.addElement("freightTemplate");
		if(systemNameNode == null)systemNameNode = settingElement.addElement("systemName");
		if(systemVersionNode == null)systemVersionNode = settingElement.addElement("systemVersion");
		if(systemDescriptionNode == null)systemDescriptionNode = settingElement.addElement("systemDescription");
		if(buildHtmlDelayTimeNode == null)buildHtmlDelayTimeNode = settingElement.addElement("buildHtmlDelayTime");
		if(lbsRadiusNode == null)lbsRadiusNode = settingElement.addElement("lbsRadius");//zht
		if(scoreScaleNode == null)scoreScaleNode = settingElement.addElement("scoreScale");
		if(scoreTypeNode == null)scoreTypeNode = settingElement.addElement("scoreType");
		
		if(rebateScaleNode == null)rebateScaleNode = settingElement.addElement("rebateScale");
		if(rebateTypeNode == null)rebateTypeNode = settingElement.addElement("rebateType");
		if(rebateCycleNode == null)rebateCycleNode = settingElement.addElement("rebateCycle");
		
		if(defaultMarketPriceNumberNode == null)defaultMarketPriceNumberNode = settingElement.addElement("defaultMarketPriceNumber");
		if(defaultMarketPriceOperatorNode == null)defaultMarketPriceOperatorNode = settingElement.addElement("defaultMarketPriceOperator");
		if(thumbnailGoodsImageHeightNode == null)thumbnailGoodsImageHeightNode = settingElement.addElement("thumbnailGoodsImageHeight");
		if(isShowMarketPriceNode == null)isShowMarketPriceNode = settingElement.addElement("isShowMarketPrice");
		if(thumbnailGoodsImageWidthNode == null)thumbnailGoodsImageWidthNode = settingElement.addElement("thumbnailGoodsImageWidth");
		if(isRegisterEnabledNode == null)isRegisterEnabledNode = settingElement.addElement("isRegisterEnabled");
		if(isShowPoweredInfoNode == null)isShowPoweredInfoNode = settingElement.addElement("isShowPoweredInfo");
		if(imageBrowsePathNode == null)imageBrowsePathNode = settingElement.addElement("imageBrowsePath");
		if(imageUploadPathNode == null)imageUploadPathNode = settingElement.addElement("imageUploadPath");
		if(contextPathNode == null)contextPathNode = settingElement.addElement("contextPath");
		if(isLoginFailureLockNode == null)isLoginFailureLockNode = settingElement.addElement("isLoginFailureLock");
		if(loginFailureLockCountNode == null)loginFailureLockCountNode = settingElement.addElement("loginFailureLockCount");
		if(loginFailureLockTimeNode == null)loginFailureLockTimeNode = settingElement.addElement("loginFailureLockTime");
		if(currencyTypeNode == null)currencyTypeNode = settingElement.addElement("currencyType");
		if(currencySignNode == null)currencySignNode = settingElement.addElement("currencySign");
		if(currencyUnitNode == null)currencyUnitNode = settingElement.addElement("currencyUnit");
		if(priceScaleNode == null)priceScaleNode = settingElement.addElement("priceScale");
		if(handingFeeScaleNode == null)handingFeeScaleNode = settingElement.addElement("handingFeeScale");
		if(rateSacleNode == null)rateSacleNode = settingElement.addElement("rateSacle");
		if(showGiftUrlNode==null)showGiftUrlNode = settingElement.addElement("showGiftUrl");
		if(priceRoundTypeNode == null)priceRoundTypeNode = settingElement.addElement("priceRoundType");
		if(shopNameNode == null)shopNameNode  = settingElement.addElement("shopName");
		if(shopUrlNode == null) shopUrlNode = settingElement.addElement("shopUrl");
		if(goodsphotoUrlNode == null)goodsphotoUrlNode  = settingElement.addElement("goodsphotoUrl");
		if(shopLogoPathNode == null) shopLogoPathNode = settingElement.addElement("shopLogo");
		if(hotSearchNode == null) hotSearchNode = settingElement.addElement("hotSearch");
		if(metaKeywordsNode == null) metaKeywordsNode = settingElement.addElement("metaKeywords");
		if(metaDescriptionNode ==null) metaDescriptionNode = settingElement.addElement("metaDescription");
		if(addressNode == null)addressNode = settingElement.addElement("address");
		if(phoneNode == null)phoneNode = settingElement.addElement("phone");
		if(zipCodeNode == null)zipCodeNode = settingElement.addElement("zipCode");
		if(emailNode == null)emailNode = settingElement.addElement("email");
		if(certtextNode == null)certtextNode = settingElement.addElement("certtext");
		if(storeAlertCountNode == null)storeAlertCountNode = settingElement.addElement("storeAlertCount");
		if(storeFreezeTimeNode == null)storeFreezeTimeNode = settingElement.addElement("storeFreezeTime");
		if(uploadLimitNode == null)uploadLimitNode = settingElement.addElement("uploadLimit");
		if(defaultBigGoodsImagePathNode == null)defaultBigGoodsImagePathNode = settingElement.addElement("defaultBigGoodsImagePath");
		if(defaultShowGoodsImagePathNode == null)defaultShowGoodsImagePathNode = settingElement.addElement("defaultShowGoodsImagePath");
		if(defaultSmallGoodsImagePathNode == null)defaultSmallGoodsImagePathNode = settingElement.addElement("defaultSmallGoodsImagePath");
		if(defaultThumbnailGoodsImagePathNode == null)defaultThumbnailGoodsImagePathNode = settingElement.addElement("defaultThumbnailGoodsImagePath");
		if(watermarkImagePathNode == null)watermarkImagePathNode = settingElement.addElement("watermarkImagePath");
		if(watermarkPositionNode == null)watermarkPositionNode = settingElement.addElement("watermarkPosition");
		if(watermarkAlphaNode == null)watermarkAlphaNode = settingElement.addElement("watermarkAlpha");
		if(bigGoodsImageWidthNode == null)bigGoodsImageWidthNode = settingElement.addElement("bigGoodsImageWidth");
		if(bigGoodsImageHeightNode == null)bigGoodsImageHeightNode = settingElement.addElement("bigGoodsImageHeight");
		if(showGoodsImageWidthNode == null)showGoodsImageWidthNode = settingElement.addElement("showGoodsImageWidth");
		if(showGoodsImageHeightNode == null)showGoodsImageHeightNode = settingElement.addElement("showGoodsImageHeight");
		if(middleGoodsImageWidthNode == null)middleGoodsImageWidthNode = settingElement.addElement("middleGoodsImageWidth");
		if(middleGoodsImageHeightNode == null)middleGoodsImageHeightNode = settingElement.addElement("middleGoodsImageHeight");
//		if(smallGoodsImageWidthNode == null)smallGoodsImageWidthNode = settingElement.addElement("smallGoodsImageWidth");
//		if(smallGoodsImageHeightNode == null)smallGoodsImageHeightNode = settingElement.addElement("smallGoodsImageHeight");
		if(smtpFromMailNode == null)smtpFromMailNode = settingElement.addElement("smtpFromMail");
		if(smtpHostNode == null)smtpHostNode = settingElement.addElement("smtpHost");
		if(smtpPortNode == null)smtpPortNode = settingElement.addElement("smtpPort");
		if(smtpUsernameNode == null)smtpUsernameNode = settingElement.addElement("smtpUsername");
		if(smtpPasswordNode == null)smtpPasswordNode = settingElement.addElement("smtpPassword");
		if(isGzipEnabledNode == null)isGzipEnabledNode = settingElement.addElement("isGzipEnabled");
		if(isCommentEnabledNode == null)isCommentEnabledNode = settingElement.addElement("isCommentEnabled");
		if(isCommentCaptchaEnabledNode == null)isCommentCaptchaEnabledNode = settingElement.addElement("isCommentCaptchaEnabled");
		if(commentAuthorityNode == null) settingElement.addElement("commentAuthority");
		if(commentDisplayTypeNode == null)commentDisplayTypeNode = settingElement.addElement("commentDisplayType");
		if(smsApiUrlNode == null) smsApiUrlNode = settingElement.addElement("smsApiUrl");
		if(smsApiUsernameNode == null) smsApiUsernameNode = settingElement.addElement("smsApiUsername");
		if(smsApiPasswordNode == null) smsApiPasswordNode = settingElement.addElement("smsApiPassword");
		if(orderUnPayNode == null) orderUnPayNode = settingElement.addElement("orderUnPay");
		if(orderUnPayMsgNode == null) orderUnPayMsgNode = settingElement.addElement("orderUnPayMsg");
		if(orderUncompletedNode == null) orderUncompletedNode = settingElement.addElement("smsApiPassword");
		if(sendMsgTimeNode == null) sendMsgTimeNode = settingElement.addElement("sendMsgTime");
		if(partnerNoNode == null) partnerNoNode  = settingElement.addElement("partnerNo"); 
		if(partnerKeyNode == null) partnerKeyNode  = settingElement.addElement("partnerKey"); 
		
		if(bBusTyNode == null) bBusTyNode = settingElement.addElement("bBusTy");  //pl
		if(pyrActNode == null) pyrActNode = settingElement.addElement("pyrAct");
		if(pyrNamNode == null) pyrNamNode = settingElement.addElement("pyrNam");
		if(pyrAdrNode == null) pyrAdrNode = settingElement.addElement("pyrAdr");
		if(pMBTypNode == null) pMBTypNode = settingElement.addElement("pMBTyp");
		if(ccyCodNode == null) ccyCodNode = settingElement.addElement("ccyCod");
		if(taxRateNode == null) taxRateNode = settingElement.addElement("taxRate");
		if(agrNoNode == null) agrNoNode = settingElement.addElement("agrNo");
		if(omsChangeNode == null) omsChangeNode = settingElement.addElement("omsChange");
		if(activityUrlNode == null) activityUrlNode = settingElement.addElement("activityUrl");
		if(scoreRateNode == null) scoreRateNode = settingElement.addElement("scoreRate");
		if(postageAmountNode == null) postageAmountNode = settingElement.addElement("postageAmount");
		//TODO
		if(postageAmountLimitNode == null) postageAmountLimitNode = settingElement.addElement("postageAmountLimit");
		
		
		if(longChangeGuiNode == null) longChangeGuiNode = settingElement.addElement("longChangeGui");//龙币兑换桂币率
		if(guiChangeRmbNode == null) guiChangeRmbNode = settingElement.addElement("guiChangeRmb");//桂币兑换人民币率
		
	

		freightTemplateNode.setText(setting.getFreightTemplate());
		systemNameNode.setText(setting.getSystemName());
		systemVersionNode.setText(setting.getSystemVersion());
		systemDescriptionNode.setText(setting.getSystemDescription());
		contextPathNode.setText(setting.getContextPath());
		imageUploadPathNode.setText(setting.getImageUploadPath());
		imageBrowsePathNode.setText(setting.getImageBrowsePath());
		isShowPoweredInfoNode.setText(String.valueOf(setting.getIsShowPoweredInfo()));
		isRegisterEnabledNode.setText(String.valueOf(setting.getIsRegisterEnabled()));
		thumbnailGoodsImageWidthNode.setText(String.valueOf(setting.getThumbnailGoodsImageWidth()));
		thumbnailGoodsImageHeightNode.setText(String.valueOf(setting.getThumbnailGoodsImageHeight()));
		isShowMarketPriceNode.setText(String.valueOf(setting.getIsShowMarketPrice()));
		defaultMarketPriceOperatorNode.setText(String.valueOf(setting.getDefaultMarketPriceOperator()));
		defaultMarketPriceNumberNode.setText(String.valueOf(setting.getDefaultMarketPriceNumber()));
		scoreTypeNode.setText(setting.getScoreType());
		scoreScaleNode.setText(String.valueOf(setting.getScoreScale()));
		//rebateTypeNode.setText(setting.getRebateType());
		//rebateScaleNode.setText(String.valueOf(setting.getRebateScale()));
		//rebateCycleNode.setText(String.valueOf(setting.getRebateCycle()));
		buildHtmlDelayTimeNode.setText(String.valueOf(setting.getBuildHtmlDelayTime()));
		lbsRadiusNode.setText(String.valueOf(setting.getLbsRadius()));//zht
		isLoginFailureLockNode.setText(String.valueOf(setting.getIsLoginFailureLock()));
		loginFailureLockCountNode.setText(String.valueOf(setting.getLoginFailureLockCount()));
		loginFailureLockTimeNode.setText(String.valueOf(setting.getLoginFailureLockTime()));
		currencyTypeNode.setText(String.valueOf(setting.getCurrencyType()));
		currencySignNode.setText(setting.getCurrencySign());
		currencyUnitNode.setText(setting.getCurrencyUnit());
		priceScaleNode.setText(String.valueOf(setting.getPriceScale()));
		handingFeeScaleNode.setText(String.valueOf(setting.getHandingFeeScale()));
		rateSacleNode.setText(String.valueOf(setting.getRateSacle()));
		showGiftUrlNode.setText(String.valueOf(setting.getShowGiftUrl()));
		priceRoundTypeNode.setText(String.valueOf(setting.getPriceRoundType()));
		shopNameNode.setText(setting.getShopName());
		shopUrlNode.setText(setting.getShopUrl());
		goodsphotoUrlNode.setText(setting.getGoodsphotoUrl());
		hotSearchNode.setText(setting.getHotSearch());
		metaKeywordsNode.setText(setting.getMetaKeywords());
		metaDescriptionNode.setText(setting.getMetaDescription());
		addressNode.setText(setting.getAddress());
		phoneNode.setText(setting.getPhone());
		zipCodeNode.setText(setting.getZipCode());
		emailNode.setText(setting.getEmail());
		certtextNode.setText(setting.getCerttext());
		storeAlertCountNode.setText(String.valueOf(setting.getStoreAlertCount()));
		storeFreezeTimeNode.setText(String.valueOf(setting.getStoreFreezeTime()));
		uploadLimitNode.setText(String.valueOf(setting.getUploadLimit()));
		defaultBigGoodsImagePathNode.setText(setting.getDefaultBigGoodsImagePath());
		defaultShowGoodsImagePathNode.setText(setting.getDefaultShowGoodsImagePath());
		defaultSmallGoodsImagePathNode.setText(setting.getDefaultSmallGoodsImagePath());
		defaultThumbnailGoodsImagePathNode.setText(setting.getDefaultThumbnailGoodsImagePath());
		watermarkImagePathNode.setText(setting.getWatermarkImagePath());
		watermarkPositionNode.setText(String.valueOf(setting.getWatermarkPosition()));
		watermarkAlphaNode.setText(String.valueOf(setting.getWatermarkAlpha()));
		bigGoodsImageWidthNode.setText(String.valueOf(setting.getBigGoodsImageWidth()));
		bigGoodsImageHeightNode.setText(String.valueOf(setting.getBigGoodsImageHeight()));
		showGoodsImageWidthNode.setText(String.valueOf(setting.getShowGoodsImageWidth()));
		showGoodsImageHeightNode.setText(String.valueOf(setting.getShowGoodsImageHeight()));
		middleGoodsImageWidthNode.setText(String.valueOf(setting.getMiddleGoodsImageWidth()));
		middleGoodsImageHeightNode.setText(String.valueOf(setting.getMiddleGoodsImageHeight()));
//		smallGoodsImageWidthNode.setText(String.valueOf(setting.getSmallGoodsImageWidth()));
//		smallGoodsImageHeightNode.setText(String.valueOf(setting.getSmallGoodsImageHeight()));
//		smtpFromMailNode.setText(setting.getSmtpFromMail());
//		smtpHostNode.setText(setting.getSmtpHost());
//		smtpPortNode.setText(String.valueOf(setting.getSmtpPort()));
//		smtpUsernameNode.setText(String.valueOf(setting.getSmtpUsername()));
//		smtpPasswordNode.setText(setting.getSmtpPassword());
		isGzipEnabledNode.setText(String.valueOf(setting.getIsGzipEnabled()));
		isCommentEnabledNode.setText(String.valueOf(setting.getIsCommentEnabled()));
		isCommentCaptchaEnabledNode.setText(String.valueOf(setting.getIsCommentCaptchaEnabled()));
		commentAuthorityNode.setText(setting.getCommentAuthority());
		commentDisplayTypeNode.setText(setting.getCommentDisplayType());
		smsApiUrlNode.setText(setting.getSmsApiUrl());
		smsApiUsernameNode.setText(setting.getSmsApiUsername());
		smsApiPasswordNode.setText(setting.getSmsApiPassword());
		orderUnPayNode.setText(String.valueOf(setting.getOrderUnPay()));
		orderUnPayMsgNode.setText(String.valueOf(setting.getOrderUnPayMsg()));
		orderUncompletedNode.setText(String.valueOf(setting.getOrderUncompleted()));
		sendMsgTimeNode.setText(String.valueOf(setting.getSendMsgTime()));
		partnerNoNode.setText(String.valueOf(setting.getPartnerNo()));
		partnerKeyNode.setText(String.valueOf(setting.getPartnerKey()));
		appidNode.setText(setting.getAppid());
		appsecretNode.setText(setting.getAppsecret());
		notifyUrlNode.setText(setting.getNotifyUrl());
		payUrlNode.setText(setting.getPayUrl());
		
		bBusTyNode.setText(setting.getBBusTy()); //pl
		pyrActNode.setText(setting.getPyrAct());
		pyrNamNode.setText(setting.getPyrNam());
		pyrAdrNode.setText(setting.getPyrAdr());
		pMBTypNode.setText(setting.getPMBTyp());
		ccyCodNode.setText(setting.getCcyCod());
		taxRateNode.setText(setting.getTaxRate());
		
		agrNoNode.setText(setting.getAgrNo());
		omsChangeNode.setText(setting.getOmsChange());
		activityUrlNode.setText(setting.getActivityUrl());
		scoreRateNode.setText(setting.getScoreRate().toString());
		
		postageAmountNode.setText(setting.getPostageAmount().toString());
		//TODO
		postageAmountLimitNode.setText(setting.getPostageAmountLimit().toString());
		
		
		longChangeGuiNode.setText(setting.getLongChangeGui().toString());
		guiChangeRmbNode.setText(setting.getGuiChangeRmb().toString());
		
		
		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
			outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
			outputFormat.setIndent(true);// 设置是否缩进
			outputFormat.setIndent("	");// 以TAB方式实现缩进
			outputFormat.setNewlines(true);// 设置是否换行
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(settingFile), outputFormat);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		EhCacheConfigUtil.flush();
	}
	
	/**
	 * 刷新系统配置信息
	 * 
	 */
	public void flush() {
		EhCacheConfigUtil.flush();
	}
	
	/**
	 * 获取精度处理后的商品价格
	 * 
	 */
	public static BigDecimal getPriceScaleBigDecimal(BigDecimal price) {
		if(price==null)
			return null;
		Integer priceScale = getSetting().getPriceScale();
		RoundType priceRoundType = getSetting().getPriceRoundType();
		if (priceRoundType == RoundType.roundHalfUp) {
			return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
		} else if (priceRoundType == RoundType.roundUp) {
			return price.setScale(priceScale, BigDecimal.ROUND_UP);
		} else {
			return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
		}
	}
	
	/**
	 * 获取精度处理后的订单额
	 * 
	 */
	public static BigDecimal getOrderScaleBigDecimal(BigDecimal orderAmount) {
		Integer orderScale = getSetting().getOrderScale();
		RoundType orderRoundType = getSetting().getOrderRoundType();
		if (orderRoundType == RoundType.roundHalfUp) {
			return orderAmount.setScale(orderScale, BigDecimal.ROUND_HALF_UP);
		} else if (orderRoundType == RoundType.roundUp) {
			return orderAmount.setScale(orderScale, BigDecimal.ROUND_UP);
		} else {
			return orderAmount.setScale(orderScale, BigDecimal.ROUND_DOWN);
		}
	}
	
	/**
	 * 100 --> ￥100.00
	 * @autor zhanghaitao
	 * @param amount
	 * @return
	 */
	public static String getPriceCurrencyFormat(BigDecimal amount){
		String currencySign = getSetting().getCurrencySign();
		amount=getPriceScaleBigDecimal(amount);
		return currencySign+amount.toString();
	}
	
	/**
	 * 获取商品价格货币格式字符串
	 * 
	 */
	public static String getPriceCurrencyFormat() {
		Integer priceScale = getSetting().getPriceScale();
		String currencySign = getSetting().getCurrencySign();
		if (priceScale == 0) {
			return currencySign + "#0";
		} else if (priceScale == 1) {
			return currencySign + "#0.0";
		} else if (priceScale == 2) {
			return currencySign + "#0.00";
		} else if (priceScale == 3) {
			return currencySign + "#0.000";
		} else if (priceScale == 4) {
			return currencySign + "#0.0000";
		} else {
			return currencySign + "#0.00000";
		}
	}
	
	/**
	 * 获取商品价格货币格式字符串,不加"￥"符号
	 * 
	 */
	public static String getPriceFormat() {
		Integer priceScale = getSetting().getPriceScale();
		if (priceScale == 0) {
			return "#0";
		} else if (priceScale == 1) {
			return "#0.0";
		} else if (priceScale == 2) {
			return "#0.00";
		} else if (priceScale == 3) {
			return "#0.000";
		} else if (priceScale == 4) {
			return "#0.0000";
		} else {
			return "#0.00000";
		}
	}
	
	/**
	 * 获取商品价格货币格式字符串（包含货币单位）
	 * 
	 */
	public static String getPriceUnitCurrencyFormat() {
		Integer priceScale = getSetting().getPriceScale();
		String currencySign = getSetting().getCurrencySign();
		String currencyUnit = getSetting().getCurrencyUnit();
		if (priceScale == 0) {
			return currencySign + "#0" + currencyUnit;
		} else if (priceScale == 1) {
			return currencySign + "#0.0" + currencyUnit;
		} else if (priceScale == 2) {
			return currencySign + "#0.00" + currencyUnit;
		} else if (priceScale == 3) {
			return currencySign + "#0.000" + currencyUnit;
		} else if (priceScale == 4) {
			return currencySign + "#0.0000" + currencyUnit;
		} else {
			return currencySign + "#0.00000" + currencyUnit;
		}
	}
	
	/**
	 * 获取订单价格货币格式字符串,不加"￥"符号
	 * 
	 */
	public static String getOrderFormat() {
		Integer orderScale = getSetting().getOrderScale();
		if (orderScale == 0) {
			return "#0";
		} else if (orderScale == 1) {
			return "#0.0";
		} else if (orderScale == 2) {
			return "#0.00";
		} else if (orderScale == 3) {
			return "#0.000";
		} else if (orderScale == 4) {
			return "#0.0000";
		} else {
			return "#0.00000";
		}
	}
	
	/**
	 * 获取订单价格货币格式字符串
	 * 
	 */
	public static String getOrderCurrencyFormat() {
		Integer orderScale = getSetting().getOrderScale();
		String currencySign = getSetting().getCurrencySign();
		if (orderScale == 0) {
			return currencySign + "#0";
		} else if (orderScale == 1) {
			return currencySign + "#0.0";
		} else if (orderScale == 2) {
			return currencySign + "#0.00";
		} else if (orderScale == 3) {
			return currencySign + "#0.000";
		} else if (orderScale == 4) {
			return currencySign + "#0.0000";
		} else {
			return currencySign + "#0.00000";
		}
	}
	
	/**
	 * 获取订单价格货币格式字符串（包含货币单位）
	 * 
	 */
	public static String getOrderUnitCurrencyFormat() {
		Integer orderScale = getSetting().getOrderScale();
		String currencySign = getSetting().getCurrencySign();
		String currencyUnit = getSetting().getCurrencyUnit();
		if (orderScale == 0) {
			return currencySign + "#0" + currencyUnit;
		} else if (orderScale == 1) {
			return currencySign + "#0.0" + currencyUnit;
		} else if (orderScale == 2) {
			return currencySign + "#0.00" + currencyUnit;
		} else if (orderScale == 3) {
			return currencySign + "#0.000" + currencyUnit;
		} else if (orderScale == 4) {
			return currencySign + "#0.0000" + currencyUnit;
		} else {
			return currencySign + "#0.00000" + currencyUnit;
		}
	}

	
	/**
	 * 订单金额支付转换 两位小数点 
	 * 
	 */
	public static BigDecimal getTradeScaleBigDecimal(BigDecimal price) {
		Integer priceScale = 2;
		RoundType priceRoundType = getSetting().getPriceRoundType();
		if (priceRoundType == RoundType.roundHalfUp) {
			return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
		} else if (priceRoundType == RoundType.roundUp) {
			return price.setScale(priceScale, BigDecimal.ROUND_UP);
		} else {
			return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
		}
	}
	
	
	/**
	 * 获取精度处理后的手续费
	 * 
	 */
	public static BigDecimal getHandingFeeScaleBigDecimal(BigDecimal handingFee) {
		if(handingFee==null)
			return null;
		Integer handingFeeScale = getSetting().getHandingFeeScale();
		RoundType priceRoundType = getSetting().getPriceRoundType();
		if (priceRoundType == RoundType.roundHalfUp) {
			return handingFee.setScale(handingFeeScale, BigDecimal.ROUND_HALF_UP);
		} else if (priceRoundType == RoundType.roundUp) {
			return handingFee.setScale(handingFeeScale, BigDecimal.ROUND_UP);
		} else {
			return handingFee.setScale(handingFeeScale, BigDecimal.ROUND_DOWN);
		}
	}
	
	
	/**
	 * 获取精度处理后的费率
	 * 
	 */
	public static BigDecimal getRateScaleBigDecimal(BigDecimal rate) {
		if(rate==null)
			return null;
		Integer rateScale = getSetting().getRateSacle();
		RoundType priceRoundType = getSetting().getPriceRoundType();
		if (priceRoundType == RoundType.roundHalfUp) {
			return rate.setScale(rateScale, BigDecimal.ROUND_HALF_UP);
		} else if (priceRoundType == RoundType.roundUp) {
			return rate.setScale(rateScale, BigDecimal.ROUND_UP);
		} else {
			return rate.setScale(rateScale, BigDecimal.ROUND_DOWN);
		}
	}
	
	public static Number percentToDecimal(String percent){
		NumberFormat nf=NumberFormat.getPercentInstance();
		try {
			return nf.parse(percent);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String decimalToPercent(String decimal,int integerDigits,int fractionDigits){
		NumberFormat nf=NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(integerDigits); 
		nf.setMaximumFractionDigits(fractionDigits); 
		return nf.format(decimal);
	}
	
}