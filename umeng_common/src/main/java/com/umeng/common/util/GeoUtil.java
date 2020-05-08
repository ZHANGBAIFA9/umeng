package com.umeng.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Create By BF On 2020/2/29
 * 地理位置工具类
 */
public class GeoUtil {
    //定义Map缓存
    private static Map<String ,String[]> cache = new HashMap<String, String[]>();

    private static Reader reader;
    static {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
            reader = new Reader(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *通过IP得到省份和城市名称
     */
    public static String[] getGeoInfo(String ip){
        if(cache.containsKey(ip)){
            return cache.get(ip);
        }
        //定义数组
        String[] arr = {"",""};
        try {
            InetAddress addr  = InetAddress.getByName(ip);
            JsonNode node = reader.get(addr);
            //国家
            String country = node.get("country").get("names").get("zh-CN").textValue();
            arr[0] = country ;
            //省份，数组
            String province = node.get("subdivisions").get(0).get("names").get("zh-CN").textValue();
            arr[1] = province ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取的信息存放到缓存中
        cache.put(ip,arr);
        return arr;
    }
}
