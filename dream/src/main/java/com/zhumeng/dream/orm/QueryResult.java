package com.zhumeng.dream.orm;

import java.util.List;

/**
 * 
	 * @filename ：QueryResult.java   
	 * @description :查询结果集
	 * @version ：   V 1.0
	 * @author : lizhong
	 * @create : 2011-8-1 下午01:15:12  
	 * @Copyright: hyzy Corporation 2014    
	 * 
	 * Modification History:
	 * 	Date			Author			Version			Description
	 *--------------------------------------------------------------
	 *2011-8-1 下午01:15:12
 */
public class QueryResult<T> {
	/** 记录集 **/
	private List<T> resultList;
	/** 总记录数 **/
	private Long totalRecord;

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}
}