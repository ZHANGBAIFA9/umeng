package com.umeng.hive.test;

import com.umeng.hive.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Create By BF On 2020/2/29
 */
public class HiveTest {
    /**
     * locale:地域信息，国际化
     */
    @Test
    public void test() throws Exception {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z",Locale.US);
        System.out.println(df.format(now));

        String str = "28/Oct/2018:20:43:46 -0700" ;
        Date thatDate = df.parse(str);
        System.out.println(thatDate.getTime());
        //1540784626000
        Date newDate = new Date(1540784626000L);
        System.out.println(df.format(newDate));
    }

    @Test
    public void test2() throws Exception {
        String str = "28/Oct/2018:20:43:46 -0700" ;
        long ts = DateUtil.parseDateStrInUS(str);
        System.out.println(ts);
        String strDate = DateUtil.formatDate(ts);
        System.out.println(strDate);
        System.out.println(DateUtil.parseDateStr(strDate));
    }
    @Test
    public void test3() throws Exception {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println(cal.getTime());
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH) + 1);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
    }
    @Test
    public void test4() throws Exception {
        long ts = 1540784626000L;
        Date date = new Date(ts);
        System.out.println(date);
    }
    @Test
    public void test5() throws Exception {
        Date date = new Date();
        System.out.println(DateUtil.formatWeek(date,-1,"yyyy/MM/dd HH:mm:ss"));
        System.out.println(DateUtil.formatMonth(date,-1,"yyyy/MM/dd HH:mm:ss"));
    }
}
