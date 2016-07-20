package com.zhumeng.dream.freemarker;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
/**   
 * 
 * @filename ：Struts2ExtendedFreemarkerManager.java   
 * @description :  freemarker 管理类
 * @version ：   V 1.0
 * @author : 
 * @create at: 2011-8-9 下午06:08:12  
 * @Copyright:  Corporation 2014    
 * 
 * Modification History:
 * 	Date			Author			Version			Description
 *--------------------------------------------------------------
 *2011-8-9 下午06:08:12
 */
public class Struts2ExtendedFreemarkerManager extends FreemarkerManager 
{  
	
	private Map<String, Object> freemarkerVariables;

	 @Override  
    protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {  
        Configuration configuration = super.createConfiguration(servletContext);  
        
//        if(freemarkerVariables != null){
//        	Set<String> keys = freemarkerVariables.keySet();
//        	for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
//        		String key = iterator.next();
//				Object obj = freemarkerVariables.get(key);
//				
//				configuration.setSharedVariable(key, obj);
//			}
//        }
        //宏
  		//configuration.setSharedVariable(GoodsCheckboxDirective.CHECKBOX, new GoodsCheckboxDirective()); 
        return configuration;  
    }

	public Map<String, Object> getFreemarkerVariables() {
		return freemarkerVariables;
	}

	public void setFreemarkerVariables(Map<String, Object> freemarkerVariables) {
		this.freemarkerVariables = freemarkerVariables;
	}  

	 
}
