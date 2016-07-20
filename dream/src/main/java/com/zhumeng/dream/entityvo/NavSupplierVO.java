package com.zhumeng.dream.entityvo;
import com.zhumeng.dream.entity.Supplier;
public class NavSupplierVO {
	private Long id;
	private String shopName;
	
	public NavSupplierVO(Supplier supplier){
		this.id=supplier.getId();
		this.shopName=supplier.getCompanyName();
	}
	public NavSupplierVO(String defaultShopName){
		this.id=-1L;
		this.shopName=defaultShopName;
	}

//	public NavSupplierVO(){
//		id=-1L;
//		this.name="";
//	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
 	
	
	
}
