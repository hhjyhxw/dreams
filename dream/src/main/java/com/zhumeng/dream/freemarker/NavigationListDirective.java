package com.zhumeng.dream.freemarker;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.zhumeng.dream.entity.Navigation;
import com.zhumeng.dream.freemarker.util.TemplateUtil;

import freemarker.core.Environment;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @filename      : NavigationListDirective.java
 * @description   : 到航模板指令
 * @create        : 2013-4-22 上午11:35:24
 * @copyright     : dream Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-22 上午11:35:24
 */
public class NavigationListDirective implements TemplateDirectiveModel{

   public static final String DIRECTIVE_NAME = "navigation_list";

	/**
	 * 导航指令位置参数
	 */
	private static final String PARAM_NAME_POSITION = "position";
		
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map map, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		// ---------------------------------------------------------------------
		// 处理参数

//		String positionParm = "top";
//		boolean positionParmSet = false;
//		List<Navigation> navigationList = null;

//		Iterator paramIter = params.entrySet().iterator();
//		while (paramIter.hasNext()) {
//			Map.Entry ent = (Map.Entry) paramIter.next();
//
//			String paramName = (String) ent.getKey();
//			TemplateModel paramValue = (TemplateModel) ent.getValue();
//
//			if (paramName.equals(PARAM_NAME_POSITION)) {
//				if (!(paramValue instanceof SimpleScalar)) {
//					throw new TemplateModelException("The NavigationListDirective \"" + PARAM_NAME_POSITION
//							+ "\" parameter " + "must be a string.");
//				}
//				positionParm = ((SimpleScalar) paramValue).getAsString();
//				
//				positionParmSet = true;
//			} else {
//				throw new TemplateModelException("NavigationListDirective Unsupported parameter: "
//						+ paramName);
//			}
//		}
//		if (!positionParmSet) {
//			throw new TemplateModelException("The NavigationListDirective required \""
//					+ PARAM_NAME_POSITION + "\" paramter" + "is missing.");
//		}
//
//		if (loopVars.length > 1) {
//			throw new TemplateModelException(
//					"NavigationListDirective At most one loop variable is allowed.");
//		}
		List<Navigation> navigationList =  null;
		String positionParm = TemplateUtil.parseToString("position", map);
//		
//		//顶部导航
//		if(StringUtils.equals("top", positionParm))
//			navigationList = BaseManagerBean.navigationService.getTopNavigationList();
//		//中部导航
//		else if(StringUtils.equals("middle", positionParm))
//			navigationList = BaseManagerBean.navigationService.getMiddleNavigationList();
//		//底部导航
//		else if(StringUtils.equals("bottom", positionParm))
//			navigationList = BaseManagerBean.navigationService.getBottomNavigationList();

		// ---------------------------------------------------------------------
		// 真正�?��处理输出内容
		if (body != null) {
			// 设置循环变量
			if (loopVars.length > 0) {
				//包装成freemarker的序列数据模�?
				loopVars[0] = new SimpleSequence(navigationList);
			}
			// 执行标签内容(<@navigation_list position="top"; navigationList>). 
			body.render(env.getOut());
		}
	}

}
