package com.zhumeng.dream.freemarker;

import java.util.List;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;

/**
 * @filename      : SubstringDirective.java
 * @description   : 字符串超长截取指令
 * @author        : chengkunxf
 * @create        : 2013-4-28 下午2:39:47
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-28 下午2:39:47
 */
public class SubstringDirective implements TemplateMethodModel{
	public static final String DIRECTIVE_NAME = "substring";
	
	
	public Object exec(List list)
	{
		if (list.size() == 2)
		{
			String s = list.get(0).toString();
			Integer integer = Integer.valueOf(list.get(1).toString());
			return new SimpleScalar(substring(s, integer.intValue(), null));
		}
		if (list.size() == 3)
		{
			String s1 = list.get(0).toString();
			Integer integer1 = Integer.valueOf(list.get(1).toString());
			String s2 = list.get(2).toString();
			return new SimpleScalar(substring(s1, integer1.intValue(), s2));
		} else
		{
			throw new RuntimeException("Wrong arguments");
		}
	}

	public static String substring(String s, int i, String s1)
	{
		if (s == null)
			return "";
		int j = 0;
		String s2 = "";
		char ac[] = s.toCharArray();
		for (int k = 0; k < ac.length && i > j; k++)
		{
			byte abyte0[] = String.valueOf(ac[k]).getBytes();
			j += abyte0.length;
			s2 = (new StringBuilder(String.valueOf(s2))).append(ac[k]).toString();
		}

		if (s1 != null && (i == j || i == j - 1))
			s2 = (new StringBuilder(String.valueOf(s2))).append(s1).toString();
		return s2;
	}
}
