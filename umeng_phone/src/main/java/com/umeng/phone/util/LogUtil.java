package com.umeng.phone.util;

import com.umeng.common.domain.*;
import com.umeng.phone.domain.DeviceInfo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Create By BF On 2020/2/9
 * 日志实体类
 */
public class LogUtil {
    /**
     * 生成指定的实例，属性通过字典进行组装
     * Appstartlog log = genEntity(AppStartupLog.class);
     */
    public static <T extends AppBaseLog> T genLogEntity(Class<T> clazz) throws Exception {
        //创建对象
        Object obj = clazz.newInstance() ;
        AppBaseLog log = (AppBaseLog) obj;
        //加载机型字典数据
        String[] deviceType = DictUtil.getRandDeviceType().split("#");
        log.setAppPlatform(deviceType[0]);
        log.setBrand(deviceType[1]);
        log.setDeviceStyle(deviceType[2]);
        //设置ostype数据
        log.setOsType(DictUtil.getRandOsType(deviceType[0]));

        //设置时间，日志的发生时间是最近三个月内
        Random r = new Random() ;
        long ms = 3 * 30 * 24 * 60 * 60 * 1000L ;
        float ratio = r.nextFloat() ;
        long offset = (long)(ms * ratio) ;
        log.setCreatedAtMs(new Date().getTime() - offset);

        //beanInfo信息
        BeanInfo bi = Introspector.getBeanInfo(clazz) ;
        PropertyDescriptor[] pps = bi.getPropertyDescriptors();
        for(PropertyDescriptor pp : pps){
            //获得属性名
            String pname = pp.getName();
            if(pname.equals(DictUtil.LOG_PROP_APPPLATFORM)
                    || pname.equals(	DictUtil.LOG_PROP_BRAND)
                    || pname.equals(DictUtil.LOG_PROP_DEVICESTYLE)
                    || pname.equals(DictUtil.LOG_PROP_OSTYPE )){
                continue;
            }

            //获取对象的setXxxx方法
            Method set = pp.getWriteMethod();
            Method get = pp.getReadMethod();
            if(set != null && get != null){
                Class[] paramsType = set.getParameterTypes();
                //参数有效性判断
                if(paramsType != null && paramsType.length == 1){
                    //字符串
                    if(paramsType[0]==String.class){
                        String value = DictUtil.getRandString(pname) ;
                        set.invoke(obj , value) ;
                    }
                    //整数
                    else if(paramsType[0] == int.class || paramsType[0] == Integer.class){
                        String value = DictUtil.getRandString(pname);
                        if(value != null){
                            set.invoke(obj, Integer.parseInt(value));
                        }
                    }
                }
            }
        }
        return (T)log ;
    }

    private static Random rand = new Random() ;

    /**
     * 生成日志列表
     */
    public static <T extends AppBaseLog> List<T> genLogs(Class<T> clazz , int n) throws Exception {
        List<T> logs =new ArrayList<T>() ;
        for(int i = 0  ;i < (rand.nextInt(n) + 1) ; i ++){
            logs.add(genLogEntity(clazz)) ;
        }
        return logs;
    }

    /**
     * 生成日志聚合体
     */
    public static AppLogAggEntity genAppAgg() throws Exception {
        AppLogAggEntity agg = new AppLogAggEntity() ;
        String devId = LogUtil.genDeviceId() ;
        //设置devId
        agg.setDeviceId(devId);

        //硬件信息
        DeviceInfo dev = RedisUtil.getDeviceInfo(devId) ;
        if(dev != null){
            DataUtil.copyProperty(dev , agg) ;
        }
        else{
            //硬件信息
            String[] devInfo = DictUtil.getRandDeviceType().split("#");
            agg.setAppPlatform(devInfo[0]);
            agg.setBrand(devInfo[1]);
            agg.setDeviceStyle(devInfo[2]);
            String osType = DictUtil.getRandOsType(devInfo[0]) ;
            agg.setOsType(osType);
            RedisUtil.setDeviceInfo(devId , devInfo[0], devInfo[1], devInfo[2], osType);
        }

        //app信息
        String appversion = DictUtil.getRandString("appversion") ;
        String appid = DictUtil.getRandString("appId") ;
        appversion = RedisUtil.getAppVersion(devId,appid, appversion) ;
        agg.setAppId(appid);
        agg.setAppVersion(appversion);

        //渠道
        agg.setAppChannel(DictUtil.getRandString("appChannel"));
        //租户
        agg.setTenantId(DictUtil.getRandString("tenantid"));

        agg.setStartupLogs(LogUtil.genLogs(AppStartupLog.class , 5));
        agg.setPageLogs(LogUtil.genLogs(AppPageLog.class , 5));
        agg.setEventLogs(LogUtil.genLogs(AppEventLog.class , 5));
        agg.setUsageLogs(LogUtil.genLogs(AppUsageLog.class , 5));
        agg.setErrorLogs(LogUtil.genLogs(AppErrorLog.class , 5));

        List<AppBaseLog> allLogs = new ArrayList<AppBaseLog>() ;
        allLogs.addAll(agg.getStartupLogs()) ;
        allLogs.addAll(agg.getEventLogs()) ;
        allLogs.addAll(agg.getErrorLogs()) ;
        allLogs.addAll(agg.getPageLogs()) ;
        allLogs.addAll(agg.getUsageLogs()) ;

        for(AppBaseLog log : allLogs){
            DataUtil.copyProperty(agg , log);
        }

        return agg ;
    }

    /**
     * 生成设备号
     * dev000-dev999
     */
    public static String genDeviceId(){
        DecimalFormat df = new DecimalFormat("000") ;
        Random r = new Random() ;
        return "dev" + df.format(r.nextInt(1000)) ;
    }

    /**
     * 生成日志类对应的日志表sql语句
     */
    public static <T extends  AppBaseLog> String genDDL(Class<T> clazz) throws Exception {
        String classname = clazz.getSimpleName().toLowerCase() ;
        StringBuffer buffer = new StringBuffer() ;
        buffer.append("create table if not exists ") ;
        buffer.append(classname + "s (\r\n") ;
        BeanInfo bi = Introspector.getBeanInfo(clazz) ;
        PropertyDescriptor[] pps = bi.getPropertyDescriptors() ;
        for(int i =0 ; i < pps.length ; i ++ ){
            String name = pps[i].getName();
            Method get = pps[i].getReadMethod() ;
            Method set = pps[i].getWriteMethod();
            if(get != null && set != null){
                if(name.equalsIgnoreCase("createdAtMs")){
                    buffer.append("  createdatms bigint") ;
                }
                else{
                    buffer.append("  " + name.toLowerCase() + " string") ;
                }
                //最后一个
                if(i != (pps.length - 1)){
                    buffer.append(" ,\r\n");
                }
            }
        }
        buffer.append(")\r\n");
        buffer.append("PARTITIONED BY (ym string, day string) \r\n");
        buffer.append("stored as parquet ;\r\n");
        return buffer.toString() ;
    }

}
