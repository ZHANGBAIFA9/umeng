package com.umeng;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.umeng.common.util.GeoUtil;
import com.umeng.common.util.ResourceUtil;
import org.junit.Test;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * Create By BF On 2020/2/29
 */
public class GeoTest {
    @Test
    public void test1() throws Exception {
        InputStream in = ClassLoader.getSystemResourceAsStream("GeoLite2-City.mmdb");
        Reader reader = new Reader(in);
        //百度：119.75.213.61
        InetAddress address = InetAddress.getByName("104.16.38.47");
        JsonNode node = reader.get(address);
        //打印node的json信息，在.json文件中查看解析
        //System.out.println(node);
        String country = node.get("country").get("names").get("zh-CN").textValue();
        //省份
        String area = node.get("subdivisions").get(0).get("names").get("zh-CN").textValue();
        //城市
        String city = node.get("city").get("names").get("zh-CN").textValue();
    }
    @Test
    public void test2() throws Exception {
        String[] arr = GeoUtil.getGeoInfo("119.75.213.61");
        System.out.println();
    }
}
