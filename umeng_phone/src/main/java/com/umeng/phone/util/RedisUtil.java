package com.umeng.phone.util;

import com.umeng.phone.domain.DeviceInfo;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By BF On 2020/2/13
 */
public class RedisUtil {
    private static Jedis redis = new Jedis("192.168.244.101",6379);

    /**
     * 按照谁被id，查询设备信息
     */
    public static DeviceInfo getDeviceInfo(String devid){
        Boolean have = redis.exists(devid);
        if(have){
            List<String> list = redis.hmget(devid, "appplatform", "brand", "deviceStyle", "osType");
            DeviceInfo dev = new DeviceInfo();
            dev.setDeviceId(devid);
            dev.setAppPlatform(list.get(0));
            dev.setBrand(list.get(1));
            dev.setDeviceStyle(list.get(2));
            dev.setOsType(list.get(3));
            return dev;

        }
        return null;
    }
    /**
     * 按照设备id，查询设备信息
     */
    public static void setDeviceInfo(String devid , String platform , String brand , String deviceStyle , String osType) {
        Map<String,String> map = new HashMap<String, String>() ;
        map.put("appplatform" , platform);
        map.put("brand" , brand);
        map.put("deviceStyle" , deviceStyle);
        map.put("osType" , osType);
        redis.hmset(devid , map) ;
    }
    /**
     * 获取appVersion信息
     */
    public static String getAppVersion(String devid,String appid,String newVersion){
        String appVersion = redis.hget(devid,appid);
        if(appVersion != null){
            return appVersion;
        }else {
            redis.hset(devid,appid,newVersion);
            return newVersion;
        }
    }

}
