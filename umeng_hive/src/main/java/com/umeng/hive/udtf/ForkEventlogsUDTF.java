package com.umeng.hive.udtf;

import com.umeng.common.domain.AppEventLog;
import com.umeng.common.domain.AppLogAggEntity;

import java.util.List;

/**
 * 叉分事件日志
 */
public class ForkEventlogsUDTF extends BaseForkUDTF<AppEventLog>{
	public List<AppEventLog> getLogs(AppLogAggEntity agg) {
		return agg.getEventLogs();
	}
}
