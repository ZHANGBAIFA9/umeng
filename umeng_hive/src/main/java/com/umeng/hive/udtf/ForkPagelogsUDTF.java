package com.umeng.hive.udtf;

import com.umeng.common.domain.AppLogAggEntity;
import com.umeng.common.domain.AppPageLog;

import java.util.List;

/**
 * 叉分启动日志
 */
public class ForkPagelogsUDTF extends BaseForkUDTF<AppPageLog>{
	public List<AppPageLog> getLogs(AppLogAggEntity agg) {
		return agg.getPageLogs();
	}
}
