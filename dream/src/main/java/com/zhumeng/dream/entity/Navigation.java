package com.zhumeng.dream.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * @filename      : Navigation.java
 * @description   : 导航管理实体
 * @author        : 
 * @create        : 2013-4-9 下午12:09:58
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-9 下午12:09:58
 */
@Entity
@Table(name = "T_NAVIGATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Navigation extends BaseEntity {

	/**
	 * @Fields serialVersionUID
	*/ 
	private static final long serialVersionUID = 8384511232877309802L;

	public enum NavigationPosition{
		/**顶部*/
		top,
		/**中间*/
		middle,
		/**底部*/
		bottom
	}
	private Long id;
	private String name;// 名称
	private NavigationPosition navigationPosition;// 位置
	private String url;// 链接地址;
	private Boolean isVisible;// 是否显示
	private Boolean isBlankTarget;// 是否在新窗口中打开
	private Integer orderList;// 排序

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public NavigationPosition getNavigationPosition() {
		return navigationPosition;
	}

	public void setNavigationPosition(NavigationPosition navigationPosition) {
		this.navigationPosition = navigationPosition;
	}

	@Column(nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(nullable = false)
	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}

	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}

	@Column(nullable = false)
	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
}
