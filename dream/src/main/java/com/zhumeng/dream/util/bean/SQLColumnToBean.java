package com.zhumeng.dream.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.JoinColumn;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

import com.zhumeng.dream.util.others.ClassUtils;



/**
 * 
 * sql查询结果集转换成VO工具类
 * 
 *
 * 
 * @version 1.0
 * 
 *
 * 
 * @author zhaozongzhan
 * 
 *
 * 
 * @Create 2016年1月7日 上午11:02:40
 * 
 *
 * 
 * @History zhaozongzhan 2016年1月7日 创建 <br>
 * 
 *
 */

public class SQLColumnToBean implements ResultTransformer {
    private static final Log logger = LogFactory.getLog(SQLColumnToBean.class);
    private static final long serialVersionUID = 1L;
    private final Class<?> resultClass;
    private Setter[] setters;
    private PropertyAccessor propertyAccessor;
    private Map<String, String> joinColumnMap;

    public SQLColumnToBean(Class<?> resultClass) {
        if (resultClass == null)
            throw new IllegalArgumentException("resultClass cannot be null");
        this.resultClass = resultClass;
        propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] {
                PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
                PropertyAccessorFactory.getPropertyAccessor("field") });
    }

    // 结果转换时，HIBERNATE调用此方法
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object result = null;

        try {
            if (setters == null) {// 首先初始化，取得目标POJO类的所有SETTER方法
                setters = new Setter[aliases.length];
                // 注意，如果在创建SQL的时候用了query.addScalar(columnAlias)
                // 的话，这里仅仅会出现addScalar指定的列。
                for (int i = 0; i < aliases.length; i++) {
                    String alias = aliases[i];
                    if (alias != null && !"ROWNUM_".equals(alias)) {
                        // 我的逻辑主要是在getSetterByColumnName方法里面，其它都是HIBERNATE的另一个类中COPY的
                        // 这里填充所需要的SETTER方法
                        setters[i] = getSetterByColumnName(alias);

                    }
                }
            }
            result = resultClass.newInstance();

            // 这里使用SETTER方法填充POJO对象
            for (int i = 0; i < aliases.length; i++) {
                if (setters[i] != null) {
                    Method m = null;
                    if (joinColumnMap != null && joinColumnMap.size() > 0)
                        for (String key : joinColumnMap.keySet()) {
                            String joinColumnName = joinColumnMap.get(key).toLowerCase();
                            if (joinColumnName.equals(aliases[i].toLowerCase())) {
                                m = setters[i].getMethod();
                                break;
                            }

                        }

                    if (m != null) {
                        Class[] entitys = m.getParameterTypes();

                        if (entitys != null && entitys.length > 0) {
                            Class entitycla=entitys[0];
                            Field[] fields = entitycla.getDeclaredFields();
                            Class dataType = null;
                            for (Field f : fields)
                                if ("id".equals(f.getName())) {
                                    dataType = f.getType();
                                }
                            Object entity = entitycla.newInstance();
                            BigDecimal idl=(BigDecimal)tuple[i];
                            if ((dataType==Long.class && tuple[i] instanceof Long)||(dataType==BigDecimal.class && tuple[i] instanceof BigDecimal )||(dataType==Integer.class && tuple[i] instanceof Integer ) ) {
                                ClassUtils.setFieldValue(entity, "id", tuple[i]);
                            }else if (dataType==Long.class){
                                
                               ClassUtils.setFieldValue(entity, "id",  idl.longValue());
                            }else if (dataType==Integer.class ){
                               ClassUtils.setFieldValue(entity, "id",  idl.intValue());
                            }
                            
                           System.out.println("对象设值:"+setters[i].getMethodName()+" aliases:"+aliases[i]+" tuple["+i+"]:"+tuple[i]);
                            setters[i].set(result, entity, null);
                        }
                    }
                    else{
                        System.out.println(setters[i].getMethod().getName()+"一般设值11:"+setters[i].getMethodName()+" aliases:"+aliases[i]+" tuple["+i+"]:"+tuple[i]);
                        
                        setters[i].set(result, tuple[i], null);
                       
                        System.out.println("一般设值22:"+setters[i].getMethodName()+" aliases:"+aliases[i]+" tuple["+i+"]:"+tuple[i]);
                        
                    }
                }
            }
        }
        catch (InstantiationException e) {
            if (logger.isInfoEnabled()) {
                logger.error("Could not instantiate resultclass: " + resultClass.getName());
                logger.error(e.getMessage());
            }
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        }
        catch (IllegalAccessException e) {
            if (logger.isInfoEnabled()) {
                logger.error("Could not instantiate resultclass: " + resultClass.getName());
                logger.error(e.getMessage());
            }
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        }
        catch (HibernateException e) {
            if (logger.isInfoEnabled()) {
                logger.error(e.getMessage());
            }
            e.printStackTrace();
        }

        return result;
    }

    // 根据数据库字段名在POJO查找JAVA属性名，参数就是数据库字段名，如：USER_ID
    private Setter getSetterByColumnName(String alias) {
        // 取得POJO所有属性名
        Field[] fields = resultClass.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            throw new RuntimeException("实体" + resultClass.getName() + "不含任何属性");
        }
        // 把字段名中所有的下杠去除
        String proName = alias.replaceAll("_", "").toLowerCase();
        for (Field field : fields) {
            if (field.getName().toLowerCase().equals(proName)) {// 去除下杠的字段名如果和属性名对得上，就取这个SETTER方法
               
                return propertyAccessor.getSetter(resultClass, field.getName());
            }
            else {
                if (joinColumnMap == null)
                    joinColumnMap = getManyToOneJoinColumns(resultClass);
                if (joinColumnMap != null && joinColumnMap.size() > 0)
                    for (String key : joinColumnMap.keySet()) {
                        String joinColumnName = joinColumnMap.get(key).toLowerCase();
                        if (joinColumnName.equals(alias.toLowerCase())) {
                            return propertyAccessor.getSetter(resultClass, key.substring(3).toLowerCase());
                        }

                    }
            }
        }
        throw new RuntimeException("找不到数据库字段 ：" + alias
                + " 对应的POJO属性或其getter方法，比如数据库字段为USER_ID或USERID，那么JAVA属性应为userId");
    }

    @SuppressWarnings("rawtypes")
    public List transformList(List collection) {
        return collection;
    }

    /**
     * 获取字段
     * 
     * @param classtype
     */
    @SuppressWarnings(value = { "rawtypes" })
    public static Map<String, String> getManyToOneJoinColumns(Class classtype) {

        Method[] methods = classtype.getDeclaredMethods();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < methods.length; i++) {
            java.lang.annotation.Annotation[] annotations = methods[i].getAnnotations();
            annotations = methods[i].getAnnotations();
            for (int j = 0; j < annotations.length; j++) {

                if (annotations[j] instanceof JoinColumn) {
                    JoinColumn joinColumn = (JoinColumn) annotations[j];
                    System.out.print("====" + methods[i].getName() + "、" + methods[i].getReturnType() + "、");
                    System.out.println(joinColumn.name());
                    map.put(methods[i].getName(), joinColumn.name());
                }
            }
        }
        return map;
    }
    
    public static void invokeSetMethod(Class owner, String methodName, Object value, Class valueDataType)
            throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Class ownerClass = owner;
        Method method = null;
        method = ownerClass.getMethod(methodName, valueDataType);
        method.invoke(owner, value);
    }

}
