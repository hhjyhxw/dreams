/**
 * Copyright (c) 2005-20010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: PropertyFilter.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package com.zhumeng.dream.orm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.zhumeng.dream.util.reflection.ConvertUtils;
import com.zhumeng.dream.util.servlet.ServletUtils;

/**
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * 
 * @author calvin
 */
public class PropertyFilter {

    /** 多个属性间OR关系的分隔符. */
    public static final String OR_SEPARATOR = "_OR_";
    /** 实体类联合 */
    public static final String ALIAS = "C";

    /** 需要转换的枚举类型Class集合 */
    private static List<Class<?>> enumObjects;

    /**
     * 读取属性文件，获取需要转换的枚举类型完整类名 通过反射获取枚举类型的Class信息。
     */
    static {
        Properties properties = new Properties();
        List<Class<?>> clazzList = new ArrayList<Class<?>>();
        try {
            properties.load(PropertyFilter.class.getClassLoader().getResourceAsStream("enumConvert.properties"));
            Enumeration<Object> en = properties.elements();
            while (en.hasMoreElements()) {
                String str = (String) en.nextElement();
                try {
                    clazzList.add(Class.forName(str));
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        enumObjects = clazzList;
    }

    /** 属性比较类型. */
    public enum MatchType {
        /** 等于 */
        EQ,
        /** like eg: %01% */
        LIKE,
        /** 精确匹配 */
        LIKEEXACT,
        /** like eg: 01% */
        LIKESTART,
        /** like eg: %01 */
        LIKEEND,
        /** 小于 */
        LT,
        /** 大于 */
        GT,
        /** 小于等于 */
        LE,
        /** 大于等于 */
        GE,
        /** 不等于 */
        NEQ,
        /** 为空 */
        ISN,
        /** 不为空 */
        ISNN,
        /** IN */
        IN;

    }

    /** 属性数据类型. */
    public enum PropertyType {
        S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), E(
                Enum.class), M(BigDecimal.class), C(Collection.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    private MatchType matchType = null;
    private Object matchValue = null;
    private Class<?> propertyClass = null;
    private String[] propertyNames = null;

    // add by wucong
    private Object[] matchValues = null;

    // private boolean alias = false;
    // private String[] aliasBean = null;

    public PropertyFilter() {
    }

    /**
     * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. eg.
     *            LIKES_NAME_OR_LOGIN_NAME
     * @param value 待比较的值.
     */
    public PropertyFilter(final String filterName, final String value) {
        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        init(filterName, value, propertyNameStr);
        if (propertyClass == Enum.class) {
            for (Class<?> clazz : enumObjects) {
                if (propertyNameStr.equalsIgnoreCase(clazz.getSimpleName())) {
                    Class<Enum> enumObjet = (Class<Enum>) clazz;

                    // add by wucong
                    String[] values = StringUtils.split(value, "_");
                    if (!StringUtils.contains(value, "_"))
                        this.matchValue = Enum.valueOf(enumObjet, value);
                    else
                        this.matchValue = Enum.valueOf(enumObjet, values[0]);// 默认取第一个
                    if (values != null) {
                        matchValues = new Object[values.length];
                        for (int i = 0; i < values.length; i++) {
                            matchValues[i] = Enum.valueOf(enumObjet, values[i]);
                        }
                        if (values.length == 0) {
                            matchValues = new Object[1];
                            matchValues[0] = this.matchValue;
                        }
                    }
                    break;
                }
            }
        }
        else {
            /*
             * if(!StringUtils.contains(value, "_")) this.matchValue =
             * ConvertUtils.convertStringToObject(value, propertyClass);
             */

            // add by wucong
            String[] values = null;
            //用于处理微信唯一号所包含的"_"字符
            if(filterName.lastIndexOf("unionId")>0||filterName.lastIndexOf("openId")>0||(!StringUtils.contains(value, "_")))//用于处理微信唯一号所包含的"_"字符
                values=new String[] {value};
        
            else 
                values=StringUtils.split(value, "_");
            if ((!StringUtils.contains(value, "_")||filterName.lastIndexOf("unionId")>0)||(!StringUtils.contains(value, "_")&&filterName.lastIndexOf("openId")>0))//用于处理微信唯一号所包含的"_"字符
                this.matchValue = ConvertUtils.convertStringToObject(value, propertyClass);
            else
                this.matchValue = ConvertUtils.convertStringToObject(values[0], propertyClass);// 默认取第一个
            if (values != null) {
                matchValues = new Object[values.length];
                for (int i = 0; i < values.length; i++) {
                    matchValues[i] = ConvertUtils.convertStringToObject(values[i], propertyClass);
                }
                if (values.length == 0) {
                    matchValues = new Object[1];
                    matchValues[0] = this.matchValue;
                }
            }
        }

    }

    // /**
    // * 有实体类联合.取别名
    // * @return
    // */
    // public boolean isAlias() {
    // return alias;
    // }

    /**
     * 从HttpRequest中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
     * 
     * @see #buildFromHttpRequest(HttpServletRequest, String)
     */
    public static List<PropertyFilter> buildFromHttpRequest(final HttpServletRequest request) {
        return buildFromHttpRequest(request, "filter");
    }

    /**
     * 从HttpRequest中获取联合查询对象别名,查询对象别名需要在实体类中定义, 默认Filter属性名前缀为filter.
     * 
     * @see #buildFromHttpRequest(HttpServletRequest, String)
     */
    public static String[] buildAliasEntityFromHttpRequest(final HttpServletRequest request) {
        return buildAliasEntityFromHttpRequest(request, "filter");
    }

    public static String[] buildAliasEntityFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
        // 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        List<String> filterParamKey = ServletUtils.getParametersKeyStartingWith(request, filterPrefix + "_");
        return buildAliasEntity(filterParamKey);
    }

    /**
     * 定义联查对象 例如：EQS_alais.name = val
     * 
     * @param filterName
     * @param value
     * @return
     */
    public static String[] buildAliasEntity(List<String> filterParamKey) {

        Assert.notNull(filterParamKey, "filterParamKey must not be null");
        String[] result = null;
        for (String filterName : filterParamKey) {
            String alais = StringUtils.substringBetween(filterName, "_", ".");

            if (StringUtils.isNotBlank(alais) && !ArrayUtils.contains(result, alais)) {
                result = ArrayUtils.add(result, alais);
            }
        }

        return result;
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表
     * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     * 
     * eg. filter_EQS_name filter_LIKES_name_OR_email
     */
    public static List<PropertyFilter> buildFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
        List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

        // 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + "_");

        // 分析参数Map,构造PropertyFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            String value = (String) entry.getValue();
            // 如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                PropertyFilter filter = new PropertyFilter(filterName, value.trim());
                filterList.add(filter);
            }
        }

        return filterList;
    }

    /**
     * 不通过http请求获取参数 直接传map对象 key值必须是这种值 例如：EQS_street 去掉前缀filter_即可
     * 
     * @author:chengkunxf
     * @param filterParamMap
     * @return
     */
    public static List<PropertyFilter> buildFromMap(final Map<String, Object> filterParamMap) {
        List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

        // 分析参数Map,构造PropertyFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            String value = (String) entry.getValue();
            // 如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                PropertyFilter filter = new PropertyFilter(filterName, value);
                filterList.add(filter);
            }
        }

        return filterList;
    }

    /**
     * 获取比较值的类型.
     */
    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    /**
     * 获取比较方式.
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * 获取比较值.
     */
    public Object getMatchValue() {
        return matchValue;
    }

    /**
     * 获取比较属性名称列表.
     */
    public String[] getPropertyNames() {
        return propertyNames;
    }

    /**
     * 获取唯一的比较属性名称.
     */
    public String getPropertyName() {
       // Assert.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
        if(propertyNames==null)
            return "";
        else
        return propertyNames[0];
    }

    /**
     * 是否比较多个属性.
     */
    public boolean hasMultiProperties() {
        if(propertyNames==null)
            return false;
        else
        return (propertyNames.length > 1);
    }

    /**
     * add by wucong 是否有多个值
     */
    public boolean hasMultiMatchValues() {
        return (matchValues.length > 1);
    }

    public Object[] getMatchValues() {
        return matchValues;
    }

    public void setMatchValues(Object[] matchValues) {
        this.matchValues = matchValues;
    }

    private void init(final String filterName, final Object value, String propertyNameStr) {
        String firstPart = StringUtils.substringBefore(filterName, "_");

        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);

        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

        try {
            matchType = Enum.valueOf(MatchType.class, matchTypeCode);
        }
        catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }

        try {
            propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
        }
        catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }

        // String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");

        propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);

    }

    /**
     * 初始化PropertyFilter
     * 
     * @param filterName
     * @param value
     */
    public PropertyFilter(final String filterName, final Object value) {
        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        init(filterName, value, propertyNameStr);
        
        matchValues = new Object[1];
        matchValues[0] = value;
        this.matchValue = value;

    }

    /**
     * 
     * 
     * 组建查询条件
     * 
     * @author: zhaozongzhan
     * 
     * @create: 2016年1月5日 上午11:30:37
     * 
     *
     * 
     * @param filterParamMap
     * @return
     */
    public static List<PropertyFilter> buildFilterFromMap(final Map<String, Object> filterParamMap) {
        List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

        // 分析参数Map,构造PropertyFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            String value = null;
            Object valueo = null;
            if (entry.getValue() instanceof java.lang.String)
                value = (String) entry.getValue();
            else
                valueo = entry.getValue();

            // 如果value值为空,则忽略此filter.
            PropertyFilter filter = null;
            if (value != null && StringUtils.isNotBlank(value))
                filter = new PropertyFilter(filterName, value);
            else if (valueo != null && !"".equals(valueo))
                filter = new PropertyFilter(filterName, valueo);
            filterList.add(filter);

        }

        return filterList;
    }

}
