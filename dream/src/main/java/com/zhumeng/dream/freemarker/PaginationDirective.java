package com.zhumeng.dream.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhumeng.dream.freemarker.util.TemplateUtil;
import com.zhumeng.dream.orm.Page;

import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @filename      : PaginationDirective.java
 * @description   : 分页控件
 * @author        : chengkunxf
 * @create        : 2013-4-19 下午2:15:07
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-19 下午2:15:07
 */
public class PaginationDirective implements TemplateDirectiveModel{
	public static final String DIRECTIVE_NAME = "pagination";
	
	public void execute(Environment environment, Map map, TemplateModel atemplatemodel[], TemplateDirectiveBody templatedirectivebody) throws TemplateException
	{
		Page pager = (Page)TemplateUtil.parseToObject("pager", map);//page对象
		ArrayList arraylist = (ArrayList)TemplateUtil.parseToObject("urlList", map);
		Integer maxPageItemCount = TemplateUtil.parseToInteger("maxPageItemCount", map);//显示�?��数量
		HashMap hashmap = (HashMap)TemplateUtil.parseToObject("parameterMap", map);//参数对象
		Integer pageNo = null;
		Long totalPages = null;
		Object obj = null;
		String firstPageUrl = null;
		String lastPageUrl = null;
		String prePageUrl = null;
		String nextPageUrl = null;
		LinkedHashMap pageItem = new LinkedHashMap();
		Object obj1 = null;
		Object obj2 = null;
		if (pager != null)
		{
			String baseUrl = TemplateUtil.parseToString("baseUrl", map);
			pageNo = Integer.valueOf(pager.getPageNo());
			totalPages = pager.getTotalPages();
			Integer pageSize = Integer.valueOf(pager.getPageSize());
//			String s8 = iiiiilll.getSearchBy();
//			String s9 = iiiiilll.getKeyword();
			String orderBy = pager.getOrderBy();
			String order = pager.getOrder();
			if (maxPageItemCount == null || maxPageItemCount.intValue() <= 0)
				maxPageItemCount = Integer.valueOf(6);
			StringBuffer stringbuffer1 = new StringBuffer();
			if (pageSize != null)
				stringbuffer1.append((new StringBuilder("&pager.pageSize=")).append(pageSize).toString());
//			if (StringUtils.isNotEmpty(s8))
//				stringbuffer1.append((new StringBuilder("&pager.searchBy=")).append(s8).toString());
//			if (StringUtils.isNotEmpty(s9))
//				stringbuffer1.append((new StringBuilder("&pager.keyword=")).append(s9).toString());
			if (StringUtils.isNotEmpty(orderBy))
				stringbuffer1.append((new StringBuilder("&pager.orderBy=")).append(orderBy).toString());
			if (StringUtils.isNotEmpty(order))
				stringbuffer1.append((new StringBuilder("&pager.order=")).append(order).toString());
			if (hashmap != null)
			{
				for (Iterator iterator1 = hashmap.keySet().iterator(); iterator1.hasNext();)
				{
					String s12 = (String)iterator1.next();
					String s13 = (String)hashmap.get(s12);
					if (StringUtils.isNotEmpty(s12) && StringUtils.isNotEmpty(s13))
						stringbuffer1.append((new StringBuilder("&")).append(s12).append("=").append(s13).toString());
				}

			}
			if (StringUtils.contains(baseUrl, "?"))
				baseUrl = (new StringBuilder(String.valueOf(baseUrl))).append("&").toString();
			else
				baseUrl = (new StringBuilder(String.valueOf(baseUrl))).append("?").toString();
			String s = stringbuffer1.toString();
			firstPageUrl = (new StringBuilder(String.valueOf(baseUrl))).append("pager.pageNo=1").append(s).toString();
			lastPageUrl = (new StringBuilder(String.valueOf(baseUrl))).append("pager.pageNo=").append(totalPages).append(s).toString();
			if (pageNo.intValue() > 1)
				prePageUrl = (new StringBuilder(String.valueOf(baseUrl))).append("pager.pageNo=").append(pageNo.intValue() - 1).append(s).toString();
			if (pageNo.intValue() < totalPages.intValue())
				nextPageUrl = (new StringBuilder(String.valueOf(baseUrl))).append("pager.pageNo=").append(pageNo.intValue() + 1).append(s).toString();
			Integer integer9 = Integer.valueOf((pageNo.intValue() - 1) / maxPageItemCount.intValue() + 1);
			Integer integer3 = Integer.valueOf((integer9.intValue() - 1) * maxPageItemCount.intValue() + 1);
			Integer integer5 = Integer.valueOf(integer9.intValue() * maxPageItemCount.intValue());
			if (integer3.intValue() < 1)
				integer3 = Integer.valueOf(1);
			if (integer5.intValue() > totalPages.intValue())
				integer5 = Integer.parseInt(totalPages.toString());
			for (int j = integer3.intValue(); j <= integer5.intValue(); j++)
				pageItem.put(String.valueOf(j), (new StringBuilder(String.valueOf(baseUrl))).append("pager.pageNo=").append(j).append(s).toString());

		} else
		if (arraylist != null)
		{
			pageNo = TemplateUtil.parseToInteger("pageNo", map);
			totalPages = Long.valueOf(arraylist.size());
			if (maxPageItemCount == null || maxPageItemCount.intValue() <= 0)
				maxPageItemCount = Integer.valueOf(6);
			StringBuffer stringbuffer = new StringBuffer();
			if (hashmap != null)
			{
				for (Iterator iterator = hashmap.keySet().iterator(); iterator.hasNext();)
				{
					String s7 = (String)iterator.next();
					String s10 = (String)hashmap.get(s7);
					if (StringUtils.isNotEmpty(s7) && StringUtils.isNotEmpty(s10))
						stringbuffer.append((new StringBuilder("&")).append(s7).append("=").append(s10).toString());
				}

			}
			String s1 = stringbuffer.toString();
			firstPageUrl = (String)arraylist.get(0);
			lastPageUrl = (String)arraylist.get(totalPages.intValue() - 1);
			if (pageNo.intValue() > 1)
				prePageUrl = (String)arraylist.get(pageNo.intValue() - 2);
			if (pageNo.intValue() < totalPages.intValue())
				nextPageUrl = (String)arraylist.get(pageNo.intValue());
			Integer integer8 = Integer.valueOf((pageNo.intValue() - 1) / maxPageItemCount.intValue() + 1);
			Integer integer4 = Integer.valueOf((integer8.intValue() - 1) * maxPageItemCount.intValue() + 1);
			Integer integer6 = Integer.valueOf(integer8.intValue() * maxPageItemCount.intValue());
			if (integer4.intValue() < 1)
				integer4 = Integer.valueOf(1);
			if (integer6.intValue() > totalPages.intValue())
				integer6 = Integer.parseInt(totalPages.toString());
			for (int i = integer4.intValue(); i <= integer6.intValue(); i++)
				pageItem.put(String.valueOf(i), (String)arraylist.get(i - 1));

		} else
		{
			return;
		}
		if (templatedirectivebody != null)
		{
			TemplateModel templatemodel = environment.getVariable("pageNumber");
			TemplateModel templatemodel1 = environment.getVariable("pageCount");
			TemplateModel templatemodel2 = environment.getVariable("firstPageUrl");
			TemplateModel templatemodel3 = environment.getVariable("lastPageUrl");
			TemplateModel templatemodel4 = environment.getVariable("prePageUrl");
			TemplateModel templatemodel5 = environment.getVariable("nextPageUrl");
			TemplateModel templatemodel6 = environment.getVariable("pageItem");
			environment.setVariable("pageNo", new SimpleNumber(pageNo));
			environment.setVariable("totalPages", new SimpleNumber(totalPages));
			environment.setVariable("firstPageUrl", new SimpleScalar(firstPageUrl));
			environment.setVariable("lastPageUrl", new SimpleScalar(lastPageUrl));
			environment.setVariable("prePageUrl", new SimpleScalar(prePageUrl));
			environment.setVariable("nextPageUrl", new SimpleScalar(nextPageUrl));
			environment.setVariable("pageItem", new SimpleHash(pageItem));
			try {
				templatedirectivebody.render(environment.getOut());
			} catch (IOException e) {
				e.printStackTrace();
			}
			environment.setVariable("pageNo", templatemodel);
			environment.setVariable("totalPages", templatemodel1);
			environment.setVariable("firstPageUrl", templatemodel2);
			environment.setVariable("lastPageUrl", templatemodel3);
			environment.setVariable("prePageUrl", templatemodel4);
			environment.setVariable("nextPageUrl", templatemodel5);
			environment.setVariable("pageItem", templatemodel6);
		}
	}
}
