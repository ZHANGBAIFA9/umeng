package com.umeng.hive.udtf;

import com.umeng.common.domain.AppErrorLog;
import com.umeng.common.domain.AppLogAggEntity;

import java.util.List;

/**
 * Create By BF On 2020/2/16
 */
public class ForkErrorlogsUDTF extends BaseForkUDTF<AppErrorLog> {
    public List<AppErrorLog> getLogs(AppLogAggEntity agg) {
        return agg.getErrorLogs();
    }
}
