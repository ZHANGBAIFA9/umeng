package com.umeng.spark.stat;

import com.umeng.spark.util.ExecSQLUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/**
 * Create By BF On 2020/3/3
 */
public class StatWeekNewJava {

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setAppName("StatWeekNewJava");
        conf.setMaster("local[4]");

        SparkSession sess = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
        ExecSQLUtil.execRegisterFuncs(sess);

        ExecSQLUtil.execSQLScript(sess,"stat_test.sql");

    }
}
