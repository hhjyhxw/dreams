/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: HibernateDao.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package com.zhumeng.dream.orm.hibernate;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.compass.gps.device.hibernate.HibernateSyncTransactionFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zhumeng.dream.orm.Page;
import com.zhumeng.dream.orm.PropertyFilter;
import com.zhumeng.dream.orm.PropertyFilter.MatchType;
import com.zhumeng.dream.orm.QueryResult;
import com.zhumeng.dream.util.bean.SQLColumnToBean;
import com.zhumeng.dream.util.others.ClassUtils;
import com.zhumeng.dream.util.reflection.ReflectionUtils;


/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author calvin
 */
public class HibernateDao<T, PK extends Serializable> extends SimpleHibernateDao<T, PK> {
	
	/**
	 * 用于Dao层子类的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- 分页查询函数 --//

	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResult(result);
		return page;
	}
	
	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String[] aliasBeans, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria c = createCriteria(criterions);
		c= createAlias(c, aliasBeans);
		
		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page) {

		//Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		if(page.getFirst() != -1 && page.getPageSize() != -1){
			//hibernate的firstResult的序号从0开始
			c.setFirstResult(page.getFirst() - 1);
			c.setMaxResults(page.getPageSize());
		}

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = 0l;
		try{
			totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	//-- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(criterions);
	}
	
	
	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public Long countResult(List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		Criteria c = createCriteria(criterions);
		return countCriteriaResult(c);
	}
	
	/**
	 * add by wucong
	 * 支持查找属性  并排序
	 * @param filters
	 * @param orderByProperty
	 * @param isAsc
	 * @return
	 */
	public List<T> find(List<PropertyFilter> filters,String orderByProperty, boolean isAsc) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(orderByProperty,isAsc,criterions);
	}
	
	/**
	 * 按属性过滤条件列表查找对象列表.
	 * 对象联合
	 */
	public List<T> find(List<PropertyFilter> filters, final String[] alias) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(alias, criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return findPage(page, criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 *   对象联合
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters, final String[] aliasBeans) {
		
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		
		return findPage(page, aliasBeans, criterions);
	}
	
	
	
	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildCriterion(final String propertyName, final Object propertyValue, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		//根据MatchType构造criterion
		switch (matchType) {
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			break;
		case LIKEEXACT:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.EXACT);
			break;
		case LIKESTART:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.START);
			break;
		case LIKEEND:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.END);
			break;
		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
			break;
		case NEQ:
			criterion = Restrictions.ne(propertyName, propertyValue);
			break;
		case ISN:
			criterion = Restrictions.isNull(propertyName);
			break;
		case ISNN:
			criterion = Restrictions.isNotNull(propertyName);
			break;
		case IN:
            criterion = Restrictions.in(propertyName, (Object[])propertyValue);
            break;
			
		}
		return criterion;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			/*if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
				Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter
						.getMatchType());
				criterionList.add(criterion);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}*/
			//update by wucong
		  //  System.out.println("+++++++++++++++++++++++++++++++++++++++filter:"+filter);
			if(filter==null)
			    continue;
		    if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
				Disjunction disjunction = Restrictions.disjunction();
				for (Object matchValue : filter.getMatchValues()) {
					Criterion criterion = buildCriterion(filter.getPropertyName(),matchValue, filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					for (Object matchValue : filter.getMatchValues()) {
						Criterion criterion = buildCriterion(param,matchValue, filter.getMatchType());
						disjunction.add(criterion);
					}
				}
				criterionList.add(disjunction);
			}
		}
		//将逻辑信息删除掉
//		criterionList.add(Restrictions.gt("status", -100) );
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
	
	/**
	 * 获取分页数据 
	 * @author:lizhong
	 * @param <T>
	 * @param firstIndex 开始索引 
	 * @param maxResult 需要获取的记录数 如果'maxResult'的值和'firstIndex'的值都为'-1',则不进行分页.
	 * @param wheresql where条件语句,传入值时要注意不用附加where字符,并且使用命名参数，从param1开始
	 * 如:" o.username= :param1 and o.email= param2....."
	 * @param params where语句占位符所代表的参数数组.
	 * @param orderBy 用来拼接sql里面的orderby 语句,map里面key存储实体的属性名,值为降序或升序(desc or asc)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<T> getScrollData(int firstIndex, int maxResult, String wheresql, Object[] params, LinkedHashMap<String,String> orderBy) {
		String where = wheresql==null || "".equals(wheresql.trim()) ? "" : " where "+wheresql; 
		String entityName = entityClass.getName();
		String hql = "select o from "+entityName+" o "+where+buildOrderby(orderBy);
		Query query =  getSession().createQuery(hql);
		setQueryParam(query,params);
		if(firstIndex != -1 && maxResult != -1){
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
		}
		QueryResult<T> qr = new QueryResult<T>();
		qr.setResultList(query.list());
		query = getSession().createQuery("select count("+getCountField(entityClass)+") from "+entityName+" o "+where);
		setQueryParam(query,params);
		qr.setTotalRecord((Long)query.uniqueResult());
		return qr;
	}
	

	/**
	 * 获取分页数据,排序,但不进行条件判断.
	 * @author:lizhong
	 * @param firstIndex开始索引
	 * @param maxResult 需要获取的记录数
	 * @param orderBy 用来拼接sql里面的orderby 语句,map里面key存储实体的属性名,值为降序或升序(desc or asc)
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int maxResult, LinkedHashMap<String,String> orderBy) {
		return getScrollData(firstIndex,maxResult, null, null, orderBy);
	}

	/**
	 * 获取分页数据,附加条件判断,但不排序
	 * @author:lizhong
	 * @param <T>
	 * @param firstIndex 开始索引
	 * @param maxResult 需要获取的记录数
	 * @param wheresql where条件语句,传入值时要注意不用附加where字符,并且使用命名参数，从param1开始
	 * 如:" o.username= :param1 and o.email= param2....."
	 * @param params where语句占位符所代表的参数数组.
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int maxResult, String wheresql, Object[] params) {
		return getScrollData(firstIndex,maxResult, wheresql, params, null);
		
	}

	/**
	 * 获取分页数据.
	 * @author:lizhong
	 * @param <T>
	 * @param firstIndex 开始索引
	 * @param maxResult 需要获取的记录数
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int maxResult) {
		return getScrollData(firstIndex,maxResult, null, null, null);
	}
	

	/**
	 * 获取全部数据
	 * @author:lizhong
	 *
	 */
	public QueryResult<T> getScrollData() {
		return getScrollData(-1, -1);
	}
	
	/**
	  * 获取全部数据 附加条件判断和排序
	  * @author            : lizhong
      * @param wheresql    : where条件语句,传入值时要注意不用附加where字符,并且使用命名参数，从param1开始,如:" o.username= :param1 and o.email= param2....."
      * @param params      : where语句占位符所代表的参数数组.
      * @param orderBy     : 用来拼接sql里面的orderby 语句,map里面key存储实体的属性名,值为降序或升序(desc or asc)
	  * @return
	 */
    public QueryResult<T> getScrollData(String wheresql,Object[] params, LinkedHashMap<String,String> orderBy) {
        return getScrollData(-1, -1,wheresql,params,orderBy);
    }
	
	/**
	 * 组拼order by语句 如:order by o.email desc , o.username asc
	 * @author: lizhong
	 * @param orderBy key为实体属性 , value为desc/asc.如下:
	 * orderBy.put("email", "desc");
	 * orderBy.put("username", "asc");
	 * 拼接成的sql语句：order by o.email desc,o.username asc
	 * @return
	 */
	
	public static String buildOrderby(LinkedHashMap<String,String> orderBy){
		StringBuilder order = new StringBuilder();
		if(orderBy != null && orderBy.size()>0){
			order.append(" order by ");
			for(Entry<String,String> entry: orderBy.entrySet()){
				order.append(" o.").append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
			}
			order.deleteCharAt(order.length()-1);
		}
		return order.toString();
	}
	/**
	 * 为where语句设置位置参数的值,位置参数从1开始.如:where o.username=?1 and o.email=?2 
	 * @author: lizhong
	 * @param query 
	 * @param params 参数数组
	 */
	public static void setQueryParam(Query query,Object[] params){
		String param = "";
		if(params!=null){
			for(int i=0;i<params.length;i++){
				param = "param"+(i+1);
				query.setParameter(param, params[i]);
			}
		}
	}
	
	/**
	 * 获取统计属性,该方法是为了解决hibernate解析联合主键select count(o) from Xxx o语句BUG而增加,
	 * hibernate对此jpql解析后的sql为select count(field1,field2,...),显示使用count()统计多个字段是错误的
	 * 简单点可以使用HQL的这种写法:"select count(*) from Object"
	 * @author: lizhong
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	protected static <E> String getCountField(Class<E> clazz){
		String out = "o";
		try {
			PropertyDescriptor[] propertys = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for(PropertyDescriptor property : propertys){
				Method method = property.getReadMethod();
				if(method!=null && method.isAnnotationPresent(EmbeddedId.class)){
					PropertyDescriptor[] props = 
						Introspector.getBeanInfo(property.getPropertyType()).getPropertyDescriptors();
					out = "o." + property.getName() + "." + (!props[1].getName().equals("class")
							? props[1].getName() : props[0].getName()) ;
					break;
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	
	/**
	 * 离线(detached)查询
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria query) {
		return query.getExecutableCriteria(this.getSession()).list();
	}
	/**
	 * 离线(detached)查询
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria query, int firstIndex, int maxResult) {
		return query.getExecutableCriteria(this.getSession()).setFirstResult(firstIndex)
				.setMaxResults(maxResult)
				.list();
	}
	
	public List<T> execList(String hql ,List<Object> values){
		Assert.hasText(hql, "queryString不能为空");
		Query query = getSession().createQuery(hql);
		if (values != null) {
			int i = 0;
			for(Object obj:values)
				query.setParameter(i++, obj);
		}
		return query.list();
	}
	
	
	/*
	 * add by wucong
	 * 用于sql查询
	 */
	public Page<T> findPageBySql(final Page<T> page, final String sql, final Object... values) {
		Assert.notNull(page, "page不能为空");
		/*
		 * 这里没有写sql的orderBy 是因为当存在多表关联时,order by 的属性有可能会在多张表之间可能存在重复
		 * 那么就会出现标识符无效等问题
		 * 所以所有的sql语句不可继续拼装，必须不可改变
		 */
		Query q = createSqlQuery(sql, values);
		if (page.isAutoCount()) {
			long totalCount = countSqlResult(sql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToSqlQuery(q, page);
		
		List result = q.list();
		page.setResult(result);
		return page;
	}
	
	/*
	 * 创建sql查询总数语句
	 */
	public Query createCountSqlQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/*
	 * 创建sql查询语句
	 */
	public Query createSqlQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createSQLQuery(queryString).addEntity(entityClass);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/*
	 * 计算sql返回结果的总记录数
	 */
	protected long countSqlResult(final String sql, final Object... values) {
		String countSql = prepareCountSql(sql);
		BigDecimal count = findUniqueBySql(countSql, values);
		return count.longValue();
	}
	
	/*
	 * 对查询总记录数的sql语句进行处理
	 */
	private String prepareCountSql(String orgSql) {
		String fromHql = orgSql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
//		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}
	
	/*
	 * 只获取一条记录
	 */
	public <X> X findUniqueBySql(final String sql, final Object... values) {
		return (X) createCountSqlQuery(sql, values).uniqueResult();
	}
	
	/*
	 * 给分页设置参数
	 */
	protected Query setPageParameterToSqlQuery(final Query q, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		
		return q;
	}
	
	 public static String camelToUnderline(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (Character.isUpperCase(c)){  
	               sb.append('_');  
	               sb.append(Character.toLowerCase(c));  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString();  
	   }  
	 
	 /**
	     * 获得表名
	     * 
	     * @param clazz 映射到数据库的po类
	     * @return String
	     */
	    @SuppressWarnings("unchecked")
	    protected  String getTableName(Class clazz) {
	        Table annotation = (Table)clazz.getAnnotation(Table.class);
	        if(annotation != null){
	            return annotation.name();
	        }
	 
	        return null;
	    }
	    
	    /**
	     * 根据查询条件查取指定属性的实体对象信息列表
	    
	     *
	    
	     * @author: zhaozongzhan
	    
	     * @create: 2016年1月8日 上午10:43:43
	    
	     *
	    
	     * @param showFieldNames entity属性列表
	     * @param queryFilters 查询条件
	     * @return
	     */
	    public List<T> getAppointedFieldNamesByEntitys(List<String> showFieldNames, List<PropertyFilter> queryFilters) {
	        Criterion[] criterions = buildCriterionByPropertyFilter(queryFilters);
	        List entitys = findShowFields(showFieldNames, criterions);
	        if(entitys!=null&&entitys.size()>0){
	            List<T> entitylist=new ArrayList<T>();
	        setArrayValuesToEntity(entitys , entitylist, showFieldNames);
	        return entitylist;
	        }else return null;
	    }
	  
	    
	    /**
	     * 
	    
	     *根据查询条件查取指定属性的唯一实体对象信息
	    
	     * @author: zhaozongzhan
	    
	     * @create: 2016年1月14日 下午5:57:01
	    
	     *
	    
	     * @param showFieldNames
	     * @param queryFilters
	     * @return
	     */
	    public T getAppointedFieldNamesByEntity(List<String> showFieldNames, List<PropertyFilter> queryFilters) {
            Criterion[] criterions = buildCriterionByPropertyFilter(queryFilters);
            if (showFieldNames == null || showFieldNames.size()<=0){
                
                return  (T) findShowFieldsUniqueResult(showFieldNames, criterions);
             }
            Object o = findShowFieldsUniqueResult(showFieldNames, criterions);
           
            if(o!=null){
                T entity=null;
                try {
                    entity = entityClass.newInstance();
                   
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.error("Could not instantiate entityClass: " + entityClass.getName());
                    logger.error(String.format("getAppointedFieldNamesByEntity 创建实体对象:%s出错:%", entityClass.getName(),e.getMessage()));
                    e.printStackTrace();
                    throw new HibernateException("Could not instantiate entityClass: " + entityClass.getName());
                    
                }
                setObjectValuesToEntity(o , entity, showFieldNames);
            return entity;
            }else return null;
        }
	    
	   /**
	    * 根据查询条件查取指定属性的实体对象信息列表 注：此方法还未找到更好的解决方案，暂时这样用
	    *如果VO为空时使用要注意：
	    *如果VO为空，则结果集默认封装为当前实体类 这种方式一般返回结果只涉及到一个表的数据 
                      如果结果集包含外键，那么外键所对应的实体类必须有id属性且有setId方法 而当前实体类也必须有外键所对应的实体类属性且有set方法
                      如 SQL:SELSECT supplier_id ,SHIPPING_FEE  ,REMIT_SHIPPING_MONEY FROM t_Supplier_Store 其中“supplier_id” 是“SupplierStore”类的外键，外键“supplier_id” 它所对应的实体类为Supplier
        
	   
	    * @author: zhaozongzhan
	   
	    * @create: 2016年1月8日 下午6:05:45
	   
	    *
	   
	    * @param sql
	    * @param vo 
	    * 如果VO为空，则结果集默认结果集封装为当前实体类 这种方式一般返回结果结果只涉及到一个表的数据
	    * 如果vo不为空，则结果集封装为VO对象， 这种方式一般返回结果结果涉及到两个或更多个表的数据
	    * @param queryParameterValues
	    * @return
	    */
	public List<T> findBySQL(String sql,Class vo,final Object... queryParameterValues){
	    
	    
        Query  query= createCountSqlQuery(sql, queryParameterValues);
        if(vo==null)query.setResultTransformer(new SQLColumnToBean(entityClass));//表字段为supplier_id那么VO属性名称supplierId
        else//如果vo不为空，则结果集封装为VO对象， 这种方式一般返回结果涉及到两个或更多个表的数据
            query.setResultTransformer(new SQLColumnToBean(vo));//表字段为supplier_id那么VO属性名称supplierId
        List entitys=query.list();
        return entitys;
	    
	    
       
	}
	
	
   /**
    * 
   
    *
   
    * @author: zhaozongzhan
   
    * @create: 2016年1月12日 下午5:27:29
   
    *
   
    * @param values 数据库查询返回结果集
    * @param entitylist 封装为实体类结果集
    * @param showFieldNames  要显示的实体属性列表
    */
    
	private void setArrayValuesToEntity(List values ,List<T>  entitylist,List<String> showFieldNames){
	   
        if (values != null && values.size() > 0) {
          
            if (showFieldNames != null && showFieldNames.size() > 0) {
                int showFieldNamesSize=showFieldNames.size();
                List<T> myentitys = new ArrayList();
                for (int i = 0; i < values.size(); i++) {
                    T entity=null;
                    try {
                        entity = entityClass.newInstance();
                    }
                    catch (Exception e) {
                        // TODO Auto-generated catch block
                        logger.error("Could not instantiate entityClass: " + entityClass.getName());
                        logger.error(String.format("getAppointedFieldNamesByEntity 创建实体对象:%s出错:%", entityClass.getName(),e.getMessage()));
                        e.printStackTrace();
                        throw new HibernateException("Could not instantiate entityClass: " + entityClass.getName());
                        
                    }
                    //有时候查询只取一列属性，而HIBERNATE反回就是一个实质的对象而不是数组对象了，所以在这要加于判断处理
                    if(showFieldNamesSize>1){
                    Object[] fieldValueArry = (Object[]) values.get(i);

                    int fieldSize = fieldValueArry.length;
                   
                    for (int f = 0; f < fieldSize; f++) {
                        ClassUtils.setFieldValue(entity, showFieldNames.get(f), fieldValueArry[f]);
                    }
                    
                   
                    }else{
                        Object fieldValueObj = values.get(i);
                        ClassUtils.setFieldValue(entity, showFieldNames.get(0), fieldValueObj);
                    }
                    entitylist.add(entity);
                }
               
            }else {
                entitylist.addAll(values);
            }

        }
	    
	}
	
	/**
	 * 将HIBERNATE查询结果对象转换成实体对象
	
	 *
	
	 * @author: zhaozongzhan
	
	 * @create: 2016年1月15日 上午10:15:45
	
	 *
	
	 * @param value HIBERNATE查询结果返回对象
	 * @param entity 封装为实体对象
	 * @param showFieldNames 要显示的实体属性列表
	 */
	private void setObjectValuesToEntity(Object value ,T  entity,List<String> showFieldNames){
	       
        if (value != null && value.equals(entity)){
            
        return ;
        }
       
        if (showFieldNames != null && showFieldNames.size() > 0) {
           
            //有时候查询只取一列属性，而HIBERNATE反回就是一个实质的对象而不是数组对象了，所以在这要加于判断处理
            if (showFieldNames != null && showFieldNames.size() > 1) {
                Object[] fieldValueArry = (Object[]) value;
                int fieldSize = fieldValueArry.length;
                
                for (int f = 0; f < fieldSize; f++) {
                    ClassUtils.setFieldValue(entity, showFieldNames.get(f), fieldValueArry[f]);
                }
                
            }else{
                
                ClassUtils.setFieldValue(entity, showFieldNames.get(0), value);
            }
           
           
        }
        
    }
    
	
    /**
     * 
    
     *按Criteria分页查询，取指定属性.
    
     * @author: zhaozongzhan
    
     * @create: 2016年1月13日 上午10:46:36
    
     *
    
     * @param page 分页参数
     * @param showFieldNames 取实体指定属性列表
     * @param criterions 数量可变的Criterion.
     * @return  分页查询结果.附带结果列表及所有查询输入参数.
     */
    public Page<T> pageAppointedFieldNamesByEntity(final Page<T> page,final List<PropertyFilter> filters, List<String> showFieldNames) {
        Assert.notNull(page, "page不能为空");
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        Criteria c = setShowFields(showFieldNames,criterions);

        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }

        setPageParameterToCriteria(c, page);

        List result = c.list();
        if(result!=null&&result.size()>0){
            List<T> entitylist=new ArrayList<T>();
        setArrayValuesToEntity(result , entitylist, showFieldNames);
        page.setResult(entitylist);
        }
        
       
        return page;
    }
    
	/**
	 * 批量更或保存
	 * @param lsit
	 * @return
	 * @throws SQLException 
	 */
	@Transactional
	public void saveOrupdatBatch(List<Object> list) {
		  
		int batchSize = 100;
    	if (list == null || list.isEmpty()) return;
    	Session session = null;
    	Transaction tx = null;
	    try{
	    	session = getSessionFactory().openSession();
	    	tx = session.getTransaction();
	       	tx.begin();
	    	for (int i=0; i<list.size(); i++) {
	    		session.merge((list.get(i)));
	    		if (i % batchSize == 0) {
	    			session.flush();
	    			session.clear();
	    		}
	    	}
	    	tx.commit();
	    }catch(Exception e){
	 	  e.printStackTrace();
	 	  tx.rollback();
	    }finally{
	    	if(null!=tx){
	    		tx=null;
	    	}
	    	if(null!=session){
	    		session=null;
	    	}
	    }
	}
  	
}
