
package com.zhumeng.dream.util.others;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

 

/**

 *动态 获取 设置 实体类属性值

 *

 * @version 1.0

 *

 * @author zhaozongzhan

 *

 * @Create 2016年1月8日 上午10:01:22

 *

 * @History zhaozongzhan 2016年1月8日 创建 <br>

 *

 */

public class ClassUtils {


    public ClassUtils(){
        
    }
    
    public static Object getValueByObjectFiled(Object obj, String s)
    {
        try {
            String s1 = (new StringBuilder("get")).append(StringUtils.capitalize(s)).toString();
            Method method = obj.getClass().getMethod(s1, new Class[0]);
            return method.invoke(obj, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    
    public static Object getFieldValue(Object obj, String s)
    {
        Field field = getFileld(obj, s);
        if (field == null)
            throw new IllegalArgumentException((new StringBuilder("Could not find field ")).append(s).toString());
        Object obj1 = null;
        try
        {
            obj1 = field.get(obj);
        }
        catch (IllegalAccessException illegalaccessexception) { }
        return obj1;
    }

    public static void setFieldValue(Object obj, String s, Object obj1)
    {
        Field field = getFileld(obj, s);
       
        if (field == null)
            throw new IllegalArgumentException((new StringBuilder("Could not find field ")).append(s).toString());
        try
        {
            field.set(obj, obj1);
        }
        catch (IllegalAccessException illegalaccessexception) { }
    }

    private static Field getFileld(Object obj, String s)
    {
        try {
            Class class1 = obj.getClass();
            Field field;
            field = class1.getDeclaredField(s);
            field.setAccessible(true);
            class1 = class1.getSuperclass();
            return field;
            
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
