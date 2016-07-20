package com.zhumeng.dream.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="T_SUPPLIER")
public class Supplier extends BaseEntity{
	
	/**零售商类型*/
	public enum SupplierType{
		/**实物*/
		physical{public String getName(){return "实物";}},
		/**服务*/
		service{public String getName(){return "服务";}},
		/**b2c*/
		b2c{public String getName(){return "B2C";}};
		public abstract String getName();
	}
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String contactPhone = new String();	//联系人手机
	private String contact = new String();		//联系人姓名
	private String companyName = new String();	//名称
	private String address = new String();	//地址
	private String supplierImg = new String();	//图片	
    private String  tag;
	private String weiXinNumber;                //微信公共号GH号
	private String weiXinName;
	private String appid;
	private String appsecret;
	private String partner;
	private String partnerkey;
	private double disatance;
    private double latitude; //纬度    
    private double longitude;//经度 
    private Long support;//是否支持送货上门 (0支持，1不支持)
    private String businessHours; //营业时间
    private SupplierType supplierType;
    
    private String openId;//清分收款的管理员对应的OPENid
    private Long eptMerchantid;//商户在移铺通系统中的ID
    private int isOpen;//是否开启店铺 (0关闭，1开启)
    
    private int deliveryScope;//配送范围
    
    private Long provinceId;//省份id
    private Long cityId;//市级id
    private Long areaId;//县区级id
    private String provinceName;
    private String cityName;
    private String areaName;
    
    private Integer orderList;
    private int isSendTo;//是否推送物流开启 (0关闭，1开启)
	/**
	 * 用来调起JS 
	 */
	private String jsTick;
	private String requestUrl;
	
	public String getContactPhone() {
		return contactPhone;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSupplierImg() {
		return supplierImg;
	}
	public void setSupplierImg(String supplierImg) {
		this.supplierImg = supplierImg;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getWeiXinNumber() {
		return weiXinNumber;
	}
	public void setWeiXinNumber(String weiXinNumber) {
		this.weiXinNumber = weiXinNumber;
	}
	public String getWeiXinName() {
		return weiXinName;
	}
	public void setWeiXinName(String weiXinName) {
		this.weiXinName = weiXinName;
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
	@Transient
	public double getDisatance() {
		return disatance;
	}
	public void setDisatance(double disatance) {
		this.disatance = disatance;
	}
	@Column(nullable = true)
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Column(nullable = true) 
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getPartnerkey() {
		return partnerkey;
	}
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
	 
	@Transient
	public String getJsTick() {
		return jsTick;
	}
	public void setJsTick(String jsTick) {
		this.jsTick = jsTick;
	}
	@Transient
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public Long getSupport() {
		return support;
	}
	public void setSupport(Long support) {
		this.support = support;
	}
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public Long getEptMerchantid() {
		return eptMerchantid;
	}
	public void setEptMerchantid(Long eptMerchantid) {
		this.eptMerchantid = eptMerchantid;
	}
	public int getDeliveryScope() {
		return deliveryScope;
	}
	public void setDeliveryScope(int deliveryScope) {
		this.deliveryScope = deliveryScope;
	}
	
	@Enumerated
	public SupplierType getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(SupplierType supplierType) {
		this.supplierType = supplierType;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getOrderList() {
		return orderList;
	}
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	public int getIsSendTo() {
		return isSendTo;
	}
	public void setIsSendTo(int isSendTo) {
		this.isSendTo = isSendTo;
	}
}
