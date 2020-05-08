package com.umeng.phone.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Create By BF On 2020/2/6
 */
public class DictUtil {
    //字典
    private static Map<String,List<String>> dict = new HashMap<String, List<String>>();
    //ostypeMap
    private static Map<String,List<String>> osTypes = new HashMap<String, List<String>>();
    //随机数
    private static Random rand = new Random();
    //操作系统列表
    public static final String DICT_DEVICELIST = "devicelist";

    /**
     * 特殊属性之间有约束关系
     */
    public static final String LOG_PROP_APPPLATFORM = "appPlatform" ;
    public static final String LOG_PROP_BRAND = "brand" ;
    public static final String LOG_PROP_DEVICESTYLE = "deviceStyle" ;
    public static final String LOG_PROP_OSTYPE = "osType" ;
    //初始化字典数据
    static{
        try {
            //加载字典文件，通过classLoader加载
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream in = loader.getResourceAsStream("dict.dat");
            //通过转换流转换成缓冲型，以便读行
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            List<String> dictItemValues = null;
            while ((line = br.readLine()) != null){
                //不是空行
                if(!line.trim().equals("")){
                    //新的字典项
                    if(line.startsWith("[")){
                        String dictItemName = line.substring(1,line.length() - 1);
                        dictItemValues = new ArrayList<String>();
                        dict.put(dictItemName,dictItemValues);
                    }else {
                        dictItemValues.add(line);
                    }
                }
            }
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 随机抽取字典项的值
     * 字典名称均为小写，转换处理
     */
    public static String getRandString(String itemName){
        List<String> list = dict.get(itemName.toLowerCase());
        if(list != null && !list.isEmpty()){
            return list.get(rand.nextInt(list.size()));
        }
        return null;
    }
    /**
     * 获取设备型号列表
     */
    public static String getRandDeviceType(){
        return getRandString(DICT_DEVICELIST);
    }
    /**
     * 随机提取一个ostype（平台类型，平台版本）
     */
    public static String getRandOsType(String platform){
        List<String> list = dict.get("ostype");
        for(String str : list){
            if(str.startsWith(platform)){
                String[] arr = str.split("=")[1].split(",");
                return arr[rand.nextInt(arr.length)];
            }
        }
        return null;
    }
}
