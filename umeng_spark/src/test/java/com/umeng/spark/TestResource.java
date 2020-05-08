package com.umeng.spark;

import com.umeng.common.util.ResourceUtil;
import org.junit.Test;

/**
 * Create By BF On 2020/3/2
 */
public class TestResource {
    /**
     * funcs.sql
     */
    @Test
    public void test1() throws Exception {
        String str = ResourceUtil.readResourceAsString("funcs.sql");
        System.out.println(str);
    }
}
