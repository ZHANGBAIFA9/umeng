package com.umeng.phone.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Create By BF On 2020/2/9
 */
public class DataUtil {
    /**
     * 属性复制，将src对象中的属性复制到dest中
     * 使用内省复制
     */
    public static void copyProperty(Object src,Object dest) throws Exception {
        //获得源和目标的bean信息
        BeanInfo srcBi = Introspector.getBeanInfo(src.getClass());
        BeanInfo destBi = Introspector.getBeanInfo(dest.getClass());
         //存放目标类的属性，set方法的映射
        Map<String,Method> destMethods = new HashMap<String, Method>();
        //准备dest的方法字典
        PropertyDescriptor[] destpps = destBi.getPropertyDescriptors();
        for(PropertyDescriptor destpp:destpps){
            String destName = destpp.getName();
            Method set = destpp.getWriteMethod();
            if(set != null){
                destMethods.put(destName,set);
            }
        }
        //得到src对象的所有属性
        PropertyDescriptor[] srcpps = srcBi.getPropertyDescriptors();
        for (PropertyDescriptor srcpp:srcpps){
            //属性名称
            String srcName = srcpp.getName();
            //get方法
            Method srcGet = srcpp.getReadMethod();
            //get方法的返回参数类型
            Class srcGetReturnType = srcGet.getReturnType();
            //通过srcName获取目标set方法
            Method destSet = destMethods.get(srcName);
            if(srcGet != null && destSet != null){
                Class[] setParameterTypes = destSet.getParameterTypes();
                if(setParameterTypes != null && setParameterTypes.length == 1 && setParameterTypes[0] == srcGetReturnType){
                    Object value = srcGet.invoke(src);
                    destSet.invoke(dest , value);
                }
            }
        }
    }
}
