package com.zhumeng.dream.orm.hibernate;

/**   
 * @filename     : Oracle11gDialect.java  
 * @description  : TODO (用一句话描述该文件做什么) 
 * @version      : V 1.0
 * @author       : zhanghaitao
 * @create       : 2015-7-22 下午11:19:07  
 * @Copyright    : hyzy Corporation 2015    
 * 
 * Modification History:
 * 	Date			Author			Version			Description
 *--------------------------------------------------------------
 *2015-7-22 下午11:19:07
 */


import org.hibernate.dialect.Oracle10gDialect;


public class Oracle11gDialect extends Oracle10gDialect {

	public Oracle11gDialect() {

		super();
		registerHibernateType(100, "string");// binary_float
		registerHibernateType(101, "string");// binary_double
	}

}
