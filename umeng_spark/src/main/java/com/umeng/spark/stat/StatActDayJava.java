package com.umeng.spark.stat;

import com.umeng.spark.util.ExecSQLUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/**
 * Create By BF On 2020/3/4
 */
public class StatActDayJava {
    public static void main(String[] args) throws Exception {
        //创建sparkconf
        SparkConf conf = new SparkConf();
        conf.setAppName("StatActDayJava");
        conf.setMaster("local[4]");
        //获取sparksession
        SparkSession sess = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
        //执行注册函数
        ExecSQLUtil.execRegisterFuncs(sess);
        //执行sql脚本
        ExecSQLUtil.execSQLScript(sess,"stat_act_day.sql");
    }
}
