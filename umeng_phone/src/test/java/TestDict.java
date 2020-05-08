import com.umeng.common.domain.*;
import com.umeng.phone.util.DictUtil;
import com.umeng.phone.util.LogUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Create By BF On 2020/2/9
 */
public class TestDict {
    @Test
    public void testRand(){
        System.out.println(DictUtil.getRandString("devicelist"));
    }
    @Test
    public void testGenAppLog() throws Exception {
        AppStartupLog log = LogUtil.genLogEntity(AppStartupLog.class) ;
        System.out.println();
    }

    @Test
    public void testAppAggLog() throws Exception {
        AppLogAggEntity agg = LogUtil.genAppAgg() ;
        System.out.println();
    }

    @Test
    public void testGenDeviceId() throws Exception {
        System.out.println(LogUtil.genDeviceId());
        System.out.println(LogUtil.genDeviceId());
        System.out.println(LogUtil.genDeviceId());
        System.out.println(LogUtil.genDeviceId());
        System.out.println(LogUtil.genDeviceId());
        System.out.println(LogUtil.genDeviceId());
    }

    @Test
    public void testDateFormat(){
        for(int i = 0 ; i < 10000 ; i ++){
            randDate() ;
        }
    }
    public static void randDate(){
        Random r = new Random();
        long ms = 3 * 30 * 24 * 60 * 60 * 1000L;
        float ratio = r.nextFloat();
        long offset = (long) (ms * ratio);
        long longMs = new Date().getTime() - offset ;
        Date newDate = new Date(longMs) ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mmss") ;
        System.out.println(sdf.format(newDate)) ;
    }

    /**
     * 通过redis保证硬件设备和app相一致
     * redis 配置文件redis.conf中配置protected-mode no 未设置
     */
    @Test
    public void testRedis() throws Exception {
        AppLogAggEntity agg = LogUtil.genAppAgg();
        System.out.println();
    }

    @Test
    public void testGenDDL() throws  Exception{
        System.out.println(LogUtil.genDDL(AppStartupLog.class));
        System.out.println(LogUtil.genDDL(AppEventLog.class));
        System.out.println(LogUtil.genDDL(AppErrorLog.class));
        System.out.println(LogUtil.genDDL(AppUsageLog.class));
        System.out.println(LogUtil.genDDL(AppPageLog.class));
    }

}
