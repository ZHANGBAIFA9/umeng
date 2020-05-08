package com.umeng.spark.util;

import com.umeng.common.util.ResourceUtil;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Create By BF On 2020/3/3
 * 执行脚本工具类
 */
public class ExecSQLUtil {
    /**
     * 执行sql脚本
     */
    public static void execSQLScript(SparkSession sess , String sqlScript) throws Exception {
        String sqls = ResourceUtil.readResourceAsString(sqlScript);
        String[] arr = sqls.split(";");
        for(String sql : arr){
            if(!sql.trim().equals("")){
                sess.sql(sql).show(1000,false);
            }
        }
    }
    /**
     * 执行sqlsStr
     */
    public static void execSQLString(SparkSession sess, String sqlString){
        String[] arr = sqlString.split(";");
        for(String sql : arr){
            if(!sql.trim().equals("")){
                sess.sql(sql).show(1000,false);
            }
        }
    }

    /**
     * 执行sqlsStr
     */
    public static Dataset<Row> execSQLString2(SparkSession sess, String sqlString){
        String[] arr = sqlString.split(";");
        for(int i = 0 ; i < arr.length ; i++){
            if(!arr[i].trim().equals("")){
                if( i != arr.length - 1){
                    sess.sql(arr[i]).show();
                }else {
                    return sess.sql(arr[i]);
                }
            }
        }
        return null ;
    }
    /**
     * 注册函数
     */
    public static void execRegisterFuncs(SparkSession sess) throws Exception {
        ExecSQLUtil.execSQLScript(sess,"funcs.sql");
    }
}
