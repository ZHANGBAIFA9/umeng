package com.umeng.spark.stat;

import com.umeng.common.util.ResourceUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.util.List;

/**
 * Create By BF On 2020/3/2
 */
public class StatDayNewJava {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setAppName("StatDayNewJava");
        conf.setMaster("local[4]") ;
        SparkSession sess = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();

        List<String> list = ResourceUtil.readResourceAsList("funcs.sql");
        for(String sql : list){
            System.out.println(sql);
            sql = sql.substring(0,sql.indexOf(";"));
            sess.sql(sql).show(100,false);
        }
        sess.sql("select formatbyday(0)").show();
    }
}
