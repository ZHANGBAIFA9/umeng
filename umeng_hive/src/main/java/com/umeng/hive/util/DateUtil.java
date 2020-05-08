package com.umeng.hive.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Create By BF On 2020/2/29
 * 日期工具类
 */
public class DateUtil {
    private static final String PATTERN_US_DATE = "dd/MMM/yyyy:HH:mm:ss Z";

    /**
     * 重载解析时间串，转换成时间戳（默认使用时间格式）
     */
    public static long parseDateStr(String dateStr) throws ParseException {
        return parseDateStr(dateStr ,"yyyy/MM/dd HH:mm:ss" ,Locale.CHINA);
    }
    /**
     * 重载解析时间串，转换成时间戳（默认使用时间格式）
     */
    public static long parseDateStrInUS(String dateStr) throws ParseException {
        return parseDateStr(dateStr ,PATTERN_US_DATE ,Locale.US);
    }
    /**
     * 解析时间串，转换成时间戳
     */
    public static long parseDateStr(String dateStr, String fmt, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt,locale);
        return sdf.parse(dateStr).getTime();
    }
    /**
     * 使用默认格式，格式化时间,默认格式("yyyy/MM/dd HH:mm:ss")
     */
    public static String formatDate(Date date){
        return formatDate(date , "yyyy/MM/dd HH:mm:ss");
    }
    /**
     * 使用指定格式，格式化时间
     */
    public static String formatDate(Date date, String fmt){
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }
    /**
     * 使用指定格式，格式化时间
     */
    public static String formatDate(long timestamp,String fmt){
        return formatDate(new Date(timestamp),fmt);
    }
    /**
     * 使用默认格式，格式化时间
     */
    public static String formatDate(long timestamp){
        return formatDate(new Date(timestamp));
    }

    /**
     * 以day为参照，查询指定按照day进行偏移日期时间格式
     */
    public static String formatDay(Date data,int offset , String fmt){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        //在当前day的成分上进行累加累减
        cal.add(Calendar.DAY_OF_MONTH , offset);
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(cal.getTime());
    }
    public static String formatDay(long ts, int offset ,String fmt){
        return formatDay(new Date(ts) , offset , fmt);
    }
    /**
     * 以date为参照，查询指定按照week进行偏移日期时间格式
     */
    public static String formatWeek(Date data,int offset , String fmt){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        //取出当前时间位于本周的第几天
        int n = cal.get(Calendar.DAY_OF_WEEK);
        //在当前day的成分上进行累加累减,至当前周的第一天
        cal.add(Calendar.DAY_OF_MONTH ,-( n - 1) );
        cal.add(Calendar.DAY_OF_MONTH , offset * 7);
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(cal.getTime());
    }
    public static String formatWeek(long ts, int offset ,String fmt){
        return formatWeek(new Date(ts) , offset , fmt);
    }
    /**
     * 以date为参照，查询指定按照month进行偏移日期时间格式
     */
    public static String formatMonth(Date data,int offset , String fmt){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH , offset);
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(cal.getTime());
    }
    public static String formatMonth(long ts, int offset ,String fmt){
        return formatMonth(new Date(ts) , offset , fmt);
    }
}
