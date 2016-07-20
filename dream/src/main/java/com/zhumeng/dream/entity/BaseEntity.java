package com.zhumeng.dream.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

/**
 * 
 * @author Administrator
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	public static final int DEFAULT_LIST_COUNT = 0;//默认记录数

	private static final long serialVersionUID = -6718838800112233445L;

//	protected Long id;// ID
	
	protected Date createDate;// 创建日期
	
	protected Date modifyDate;// 修改日期
    
	
//	@SearchableId
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	@SearchableProperty(store = Store.YES)
	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@SearchableProperty(store = Store.YES)
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

//	@Override
//	public int hashCode() {
//		return id == null ? System.identityHashCode(this) : id.hashCode();
//	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
//		final BaseEntity other = (BaseEntity) obj;
//		if (id == null) {
//			if (other.getId() != null) {
//				return false;
//			}
//		} else if (!id.equals(other.getId())) {
//			return false;
//		}
		return true;
	}
}
