
package com.zhumeng.dream.freemarker.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.utility.DeepUnwrap;

/**
 * @filename      : TemplateUtil.java
 * @description   : 类型转换类
 * @author        : zdh
 * @create        : 2013-4-10 下午2:20:45
 * @copyright     :  Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-10 下午2:20:45
 */
public class TemplateUtil {

	/**
	 * String类型转换
	 * @author:chengkunxf
	 * @param s
	 * @param map
	 * @return
	 * @throws TemplateException
	 */
	@SuppressWarnings("unchecked")
	public static String parseToString(String s, Map map) throws TemplateException
	{
		TemplateModel templatemodel = (TemplateModel)map.get(s);
		if (templatemodel == null)
			return null;
		if (templatemodel instanceof TemplateScalarModel)
			return ((TemplateScalarModel)templatemodel).getAsString();
		if (templatemodel instanceof TemplateNumberModel)
			return ((TemplateNumberModel)templatemodel).getAsNumber().toString();
		else
			throw new TemplateModelException((new StringBuilder("The \"")).append(s).append("\" parameter ").append("must be a string.").toString());
	}
	
	/**
	 * integer类型转换
	 * @author:chengkunxf
	 * @param s
	 * @param map
	 * @return
	 * @throws TemplateException
	 */
	@SuppressWarnings("unchecked")
	public static Integer parseToInteger(String s, Map map)throws TemplateException
	{
		TemplateModel templatemodel = (TemplateModel)map.get(s);
		if (templatemodel == null)
			return null;
		if (templatemodel instanceof TemplateScalarModel)
		{
			String s1 = ((TemplateScalarModel)templatemodel).getAsString();
			if (StringUtils.isEmpty(s1))
				return null;
			else
				return Integer.valueOf(Integer.parseInt(s1));
		}
		if (templatemodel instanceof TemplateNumberModel)
			return Integer.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().intValue());
		else
			throw new TemplateModelException((new StringBuilder("The \"")).append(s).append("\" parameter ").append("must be a integer.").toString());
	}
	
	/**
	 * Long类型转换
	 * @author:chengkunxf
	 * @param s
	 * @param map
	 * @return
	 * @throws TemplateException
	 */
	@SuppressWarnings("unchecked")
	public static Long parseToLong(String s, Map map)throws TemplateException
	{
		TemplateModel templatemodel = (TemplateModel)map.get(s);
		if (templatemodel == null)
			return null;
		if (templatemodel instanceof TemplateScalarModel)
		{
			String s1 = ((TemplateScalarModel)templatemodel).getAsString();
			if (StringUtils.isEmpty(s1))
				return null;
			else
				return Long.valueOf(Long.parseLong(s1));
		}
		if (templatemodel instanceof TemplateNumberModel)
			return Long.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().intValue());
		else
			throw new TemplateModelException((new StringBuilder("The \"")).append(s).append("\" parameter ").append("must be a integer.").toString());
	}
	
	/**
	 * Boolean类型转换
	 * @author:chengkunxf
	 * @param s
	 * @param map
	 * @return
	 * @throws TemplateException
	 */
	@SuppressWarnings("unchecked")
	public static Boolean parseToBoolean(String s, Map map)throws TemplateException
	{
		TemplateModel templatemodel = (TemplateModel)map.get(s);
		if (templatemodel == null)
			return null;
		if (templatemodel instanceof TemplateScalarModel)
		{
			String s1 = ((TemplateScalarModel)templatemodel).getAsString();
			if (StringUtils.isEmpty(s1))
				return null;
			else
				return Boolean.valueOf(s1);
		}
		if (templatemodel instanceof TemplateBooleanModel)
			return Boolean.valueOf(((TemplateBooleanModel)templatemodel).getAsBoolean());
		else
			throw new TemplateModelException((new StringBuilder("The \"")).append(s).append("\" parameter ").append("must be a boolean.").toString());
	}
	
	
	public static Object parseToObject(String s, Map map)throws TemplateException
	{
		TemplateModel templatemodel = (TemplateModel)map.get(s);
		
		if (templatemodel == null)
			return null;
		else{
			return DeepUnwrap.unwrap(templatemodel);
		}
		
	}
}
