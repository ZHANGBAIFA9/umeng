package com.umeng.common.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By BF On 2020/3/2
 * 资源文件工具类
 */
public class ResourceUtil {
    /**
     * 以String 方式读取整个资源串,带有字符集
     */
    public static String readResourceAsString(String resource , String charset) throws Exception {
        InputStream input = readFileInputStream(resource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1 ;
        while((len = input.read(buf))  != -1){
            baos.write(buf , 0 , len);
        }
        return new String(baos.toByteArray() , charset) ;
//        String sql = new String(baos.toByteArray() , charset) ;
//        sql = sql.replaceAll("--.*\r\n","");
//        return sql;
    }
    /**
     * 以string方式读取整个资源串
     */
    public static String readResourceAsString(String resource) throws Exception {
        InputStream input = readFileInputStream(resource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1 ;
        while((len = input.read(buf)) != -1){
            baos.write(buf , 0 , len);
        }
        String sql = new String(baos.toByteArray(), Charset.defaultCharset());
        //注释处理，将注释内容替换成空串。
        sql = sql.replaceAll("--.*\r\n", "");
        return sql;
    }
    /**
     * 将资源文件读取出来，形成list
     */
    public static List<String> readResourceAsList(String resource) throws Exception {
        List<String> list = new ArrayList<String>();
        InputStream input = readFileInputStream(resource);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));

        String line = null ;
        while((line = br.readLine()) != null){
            if(!line.trim().equals("")){
                list.add(line);
            }
        }
        return list ;
    }
    /**
     * 判断文件位置，以“/”开始 直接获取流，否则通过当前线程获取
     */
    public static InputStream readFileInputStream(String file) throws Exception {
        if(file.startsWith("/")){
            return new FileInputStream(file);
        }else{
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        }
    }
}
