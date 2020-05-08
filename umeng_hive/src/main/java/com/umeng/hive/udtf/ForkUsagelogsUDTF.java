package com.umeng.hive.udtf;

import com.umeng.common.domain.AppLogAggEntity;
import com.umeng.common.domain.AppUsageLog;

import java.util.List;

/**
 * 叉分事件日志
 */
public class ForkUsagelogsUDTF extends BaseForkUDTF<AppUsageLog>{
	public List<AppUsageLog> getLogs(AppLogAggEntity agg) {
		return agg.getUsageLogs();
	}
}
