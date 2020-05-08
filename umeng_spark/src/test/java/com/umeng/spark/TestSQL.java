package com.umeng.spark;

import com.umeng.common.domain.*;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Create By BF On 2020/3/1
 * sql代码生成
 */
public class TestSQL {
    @Test
    public void genSQL() throws IntrospectionException {
        Class[] classes = {
                AppStartupLog.class ,
                AppEventLog.class ,
                AppErrorLog.class ,
                AppPageLog.class ,
                AppUsageLog.class
        };
        for(Class clz : classes){
            System.out.println(doGenSQL(clz));
        }
    }

    public static String doGenSQL(Class clazz) throws IntrospectionException {
        String RN = "\r\n";
        String simpleClassName = clazz.getSimpleName().toLowerCase();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s" , RN));
        builder.append(String.format("%s" , RN));
        builder.append(String.format("-- %s %s",simpleClassName,RN));
        builder.append(String.format("insert into %ss partition(ym , day) %s" , simpleClassName , RN));
        builder.append(String.format("select%s" , RN));
        BeanInfo bi = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] pps = bi.getPropertyDescriptors();
        for(PropertyDescriptor pp : pps){
            String name = pp.getName();
            Class type = pp.getPropertyType();
            if(type != String.class
                    && type != int.class
                    && type != Integer.class
                    && type != Long.class
                    && type != long.class){
                continue;
            }
            Method get = pp.getReadMethod();
            Method set = pp.getWriteMethod();
            if(get != null && set != null){
                builder.append(String.format("t.%s ,%s" , name , RN));
            }
        }
        builder.append(String.format("date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,%s" , RN));
        builder.append(String.format("date_format(cast(t.createdatms as timestamp) , 'dd')%s" , RN));
        builder.append(String.format("from%s" , RN));
        builder.append(String.format(" (%s" , RN));
        builder.append(String.format("    select fork%ss(servertimestr ,clienttimems ,clientip ,json) from raw_logs limit 10%s" , simpleClassName.substring(3) , RN));
        builder.append(String.format("  )t;%s" , RN));
        return builder.toString();

    }
}
