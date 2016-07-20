/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SimpleHibernateDao.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package com.zhumeng.dream.orm.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.zhumeng.dream.util.reflection.ReflectionUtils;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释. 参考Spring2.5自带的Petlinc例子,
 * 取消了HibernateTemplate, 直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, PK extends Serializable> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    /**
     * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
     * SimpleHibernateDao<User, Long>
     */
    public SimpleHibernateDao() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数. 在构造函数中定义对象类型Class.
     * eg. SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User,
     * Long>(sessionFactory, User.class);
     */
    public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    /**
     * 取得sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * @deprecated
     * @see saveEntity(); 保存新增或修改的对象.
     */
    public void save(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
    }

    /**
     * 保存新增或修改的对象.
     */
    public void saveOrUpdate(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
    }

    /**
     * 保存新增对象.
     */
    public void saveEntity(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().save(entity);
    }

    /**
     * 更新对象
     */
    public void update(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().update(entity);
    }

    /**
     * 合并对象
     * 
     * @author :
     * @param entity :
     */
    public void merge(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().merge(entity);
    }

    /**
     * 删除对象.
     * 
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
    }

    /**
     * 按id获取对象.(get方式获取)
     */
    public T get(final PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 按id获取对象.(load方式获取)
     */
    public T load(final PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().load(entityClass, id);
    }

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(final Collection<PK> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(final PK[] ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    /**
     * 通过属性字段获取对象
     * 
     * @author : lizhong0815@163.com
     * @param propertyName : 属性名称
     * @param value : 属性值
     * @return : 实体对象
     */
    public T get(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName 不能为空");
        Assert.notNull(value, "value 不能为空");
        String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
        return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
    }

    /**
     * 获取全部对象.
     */
    public List<T> getAll() {
        return find();
    }

    /**
     * 获取全部对象, 支持按属性行序.
     */
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        }
        else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     */
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     */
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按HQL查询对象列表.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     * 
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUniqueBySql(final String sql, final List<Object> values) {
        return (X) createSqlQuery(sql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     * 
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     * 
     * @author:chengkunxf
     * @param sql
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecuteSql(final String sql, final Map<String, ?> values) {
        return createSqlQuery(sql, values).executeUpdate();
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public Query createSqlQuery(final String queryString, final List<Object> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createSQLQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i, values.get(i));
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @param values 命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @author:chengkunxf
     * @param queryString
     * @param values 命名参数,按名称绑定.
     * @return
     */
    public Query createSqlQuery(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createSQLQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     * 
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /**
     * wucong 按Criteria查询对象列表. 并支持排序
     * 
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(String orderByProperty, boolean isAsc, final Criterion... criterions) {
        Criteria c = createCriteria(criterions);
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        }
        else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    protected Criteria createAlias(Criteria criteria, String... aliasBeans) {
        if (aliasBeans != null && aliasBeans.length > 0) {
            for (int i = 0; i < aliasBeans.length; i++) {
                criteria.createAlias(aliasBeans[i], aliasBeans[i], CriteriaSpecification.LEFT_JOIN);
            }
        }
        return criteria;
    }

    public List<T> find(final String[] aliasBeans, final Criterion... criterions) {
        Criteria criteria = createCriteria(criterions);
        criteria = createAlias(criteria, aliasBeans);
        return criteria.list();
    }

    /**
     * 按Criteria查询唯一对象.
     * 
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
     * 
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
     * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize
     * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名.
     */
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * 
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
        if (newValue == null || newValue.equals(oldValue)) {
            return true;
        }
        Object object = findUniqueBy(propertyName, newValue);
        return (object == null);
    }

    /**
     * sql批量修改 表 单字段值 适用于批量修改字段值 例如修改审核状态
     * 
     * @author:chengkunxf
     * @param field 字段名称
     * @param value 字段值
     * @param ids 主键ids
     * @return
     */
    public int updateValueByField(String field, Object value, PK... ids) {

        Assert.notNull(entityClass, "entityClass不能为空");

        String tableName = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            tableName = table.name();
        }
        Assert.notNull(tableName, "表名不能为空");
        int i = 0;
        StringBuffer sql = new StringBuffer("update ");
        sql.append(tableName);
        sql.append(" set ").append(field);
        sql.append(" = ").append(":value");
        sql.append(" where id in( :id )");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", value);
        map.put("id", ids);
        i = batchExecuteSql(sql.toString(), map);
        return i;

    }

    /**
     * 使用逻辑 模式， 批量删除对象
     * 
     * @author: chengkunxf
     * @param ids 要删除对象的主键id数组
     * @return 是否删除成功
     */
    public boolean logicDelete(final PK... ids) {
        Assert.notNull(entityClass, "entityClass不能为空");
        Assert.notNull(ids, "删除对象列表不能为空");

        StringBuffer hql = new StringBuffer("update ");
        hql.append(entityClass.getName());

        int i = 0;
        if (ids.length == 0) {
            hql.append(" set status = -100 where id =:id");

            i = this.createQuery(hql.toString(), ids[0]).executeUpdate();
        }
        else {
            hql.append(" set status = -100 where id in(:id)");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ids);
            i = batchExecute(hql.toString(), map);
        }

        if (i >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 使用物理模式， 单个/批量删除对象，涉及级联删除
     * 
     * @author: lizhong
     * @param ids 要删除对象的主键id数组
     * @return 是否删除成功
     */
    public void delete(final PK... ids) {
        Assert.notEmpty(ids, "ids 不能为空");
        for (PK id : ids) {
            T entity = get(id);
            getSession().delete(entity);
        }
    }

    /**
     * 使用物理模式， 单个/批量删除对象，sql批量删除，不涉及级联删除
     * 
     * @author: lizhong
     * @param ids 要删除对象的主键id数组
     * @return 是否删除成功
     */
    public boolean deleteBySql(final PK... ids) {
        Assert.notNull(entityClass, "entityClass不能为空");
        Assert.notNull(ids, "删除对象列表不能为空");

        StringBuffer hql = new StringBuffer("delete ");
        hql.append(entityClass.getName());

        int i = 0;
        if (ids.length == 0) {
            hql.append(" where id =:id");

            i = this.createQuery(hql.toString(), ids[0]).executeUpdate();
        }
        else {
            hql.append(" where id in(:id)");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ids);
            i = batchExecute(hql.toString(), map);
        }

        if (i >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据查询条件Criterion查取指定属性的实体对象信息列表
     * 
     *
     * 
     * @author: zhaozongzhan
     * 
     * @create: 2016年1月5日 上午10:28:26
     * 
     *
     * 
     * @param showFieldNames//要取的实体对象属性列表
     * @param criterions 查询条件
     * @return 返回结果集如果showFieldNames为NULL或0则返回实体对象，否则返回数据组对象 这个方法留给调用者根据业务需要调用
     *         在com.hyzy.core.orm.hibernate.
     *         HibernateDao类提供了getAppointedFieldNamesByEntity方法统一处理为返回实体对象结果集
     */
    public List<T> findShowFields(List<String> showFieldNames, final Criterion... criterions) {
        return setShowFields(showFieldNames, criterions).list();

    }

    /**
     * 根据查询条件Criterion查取指定属性的唯一实体对象信息
     * 
     *
     * 
     * @author: zhaozongzhan
     * 
     * @create: 2016年1月14日 下午5:21:27
     * 
     *
     * @param showFieldNames//要取的实体对象属性列表
     * @param criterions 查询条件
     * @return 返回结果集如果showFieldNames为NULL或0则返回实体对象，否则返回数据组对象 这个方法留给调用者根据业务需要调用
     *         在com.hyzy.core.orm.hibernate.
     *         HibernateDao类提供了getAppointedFieldNamesByEntitys方法统一处理为返回实体对象结果集
     * 
     * @return
     */
    public Object findShowFieldsUniqueResult(List<String> showFieldNames, final Criterion... criterions) {
        Criteria c =null;
        if(showFieldNames!=null&&showFieldNames.size()>0)
            c=    setShowFields(showFieldNames, criterions);
        else
       c= createCriteria(criterions);
        return c.uniqueResult();

    }

    /**
     * 
     * 
     * 设置要取的实体对象属性列表
     * 
     * @author: zhaozongzhan
     * 
     * @create: 2016年1月13日 上午10:40:57
     * 
     *
     * 
     * @param showFieldNames 取实体表指定属性列表
     * @param criterions
     * @return
     */
    protected Criteria setShowFields(List<String> showFieldNames, final Criterion... criterions) {
        Criteria criteria = createCriteria(criterions);
        if (showFieldNames != null && showFieldNames.size() > 0) {
            ProjectionList proList = Projections.projectionList();// 设置投影集合
            for (String fieldName : showFieldNames) {

                proList.add(Projections.property(fieldName));
            }
            criteria.setProjection(proList);
        }

        return criteria;
    }

    /**
     * 更新指定属性的对象

     *

     * @author: zhaozongzhan

     * @create: 2016年1月28日 下午5:28:07

     *

     * @param valuesMap 模型值对
     * 
     * 使用如
     * Map<String,Object> clearModelMap=new  HashMap<String,Object>();
           clearModelMap.put("id", 27016L);//一定要有ID参数值，是指定更新哪个对象记录
           clearModelMap.put("supplierName","广西职业技术学院店111111111111111111ZZZqqxxx");
           bs.updateClearModel(clearModelMap);
     * @return
     */
    public boolean updateAppointedFieldNamesByEntity(Map<String,Object> updateValuesMap) {
        Assert.notNull(updateValuesMap, "valuesMap不能为空");
        StringBuffer hqlbuf = new StringBuffer();
        hqlbuf.append(" update ");
        hqlbuf.append(entityClass.getSimpleName());
        hqlbuf.append("  set ");
        List<Object> parVales = new ArrayList<Object>();
        
        for(Map.Entry<String, Object> entry:updateValuesMap.entrySet()){   
            String fieldName=entry.getKey();
            Object fieldValue=entry.getValue();
            if ("id".equals(fieldName) )
                    continue;     
            hqlbuf.append(fieldName + " =?,");
            parVales.add(fieldValue);
            
       }   
        parVales.add(updateValuesMap.get("id"));
        Object[] pars = parVales.toArray();
       
        String hql = hqlbuf.toString();
        hql = hql.substring(0, hql.lastIndexOf(",")) + " where id=?";
        logger.info(" hqlbuf:" + hql);
        int result = -1;
        if(parVales.size()>1&&updateValuesMap.get("id")!=null)
            result=this.createQuery(hql, pars).executeUpdate();
        if (result != 1)
            return false;
        else
            return true;
    }
    

    
    /**
     * 用于不经过 WEB 的SESSION关闭控制
    
            执行HQL进行批量修改/删除操作.
     * 
     * @author:chengkunxf
     * @param sql
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
    
     * @author: zhaozongzhan
    
     * @create: 2016年3月16日 上午11:17:00
    
     *
    
     * @param sql
     * @param values
     * @return
     */
    public int batchExecuteSqlBeginTransaction(final String sql, final Map<String, ?> values) {
        return createSqlQueryBeginTransaction(sql, values).executeUpdate();
    }

  
    /**
     * 用于不经过 WEB 的SESSION关闭控制
    
     *根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     * 
     * @author:chengkunxf
     * @param queryString
     * @param values 命名参数,按名称绑定.
     * @return
    
     * @author: zhaozongzhan
    
     * @create: 2016年3月16日 上午11:17:42
    
     *
    
     * @param queryString
     * @param values
     * @return
     */
    public Query createSqlQueryBeginTransaction(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Session s = HibernateUtil.currentSession();
        HibernateUtil.beginTransaction();
        Query query = s.createSQLQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }
    
    
    /**
     * 用于不经过 WEB 的SESSION关闭控制
     * sql批量修改 表 单字段值 适用于批量修改字段值 例如修改审核状态
     * 
     * @author:chengkunxf
     * @param field 字段名称
     * @param value 字段值
     * @param ids 主键ids
     * @return
    
     * @author: zhaozongzhan
    
     * @create: 2016年3月16日 上午11:14:51
    
     *
    
     * @param field
     * @param value
     * @param ids
     * @return
     */
    public int updateValueByFieldBeginTransaction( String field, Object value, Object ids) {

        Assert.notNull(entityClass, "entityClass不能为空");

        String tableName = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            tableName = table.name();
        }
        Assert.notNull(tableName, "表名不能为空");
        int i = 0;
        StringBuffer sql = new StringBuffer("update ");
        sql.append(tableName);
        sql.append(" set ").append(field);
        sql.append(" = ").append(":value");
        sql.append(" where id in( :id )");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", value);
        map.put("id", ids);
        i = batchExecuteSqlBeginTransaction(sql.toString(), map);
        return i;

    }

    
    

}