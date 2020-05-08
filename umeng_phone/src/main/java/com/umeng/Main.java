package com.umeng;

import com.alibaba.fastjson.JSONObject;
import com.umeng.common.domain.AppLogAggEntity;
import com.umeng.phone.util.LogUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Create By BF On 2020/2/3
 * 入口点函数
 */
public class Main {
    public static void main(String[] args) throws Exception {
        for(;;){
            sendLog(LogUtil.genAppAgg());
            Thread.sleep(2000);
        }
    }

    private static void sendLog(AppLogAggEntity agg) throws Exception {
        String json = JSONObject.toJSONString(agg) ;
        String strURL = "HTTP://localhost:9090/1.html";
        URL url = new URL(strURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式
        conn.setRequestMethod("POST");
        //设置发送的内容类型
        conn.setRequestProperty("Content-Type", "application/json");
        //发送客户端时间，用来对齐
        conn.setRequestProperty("client_time" , new Date().getTime() + "");
        //允许输出到服务器
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        out.write(json.getBytes());
        out.flush();
        out.close();
        int code = conn.getResponseCode();
        if (code == 200) {
            System.out.println("发送ok!!");
        }
    }
}
