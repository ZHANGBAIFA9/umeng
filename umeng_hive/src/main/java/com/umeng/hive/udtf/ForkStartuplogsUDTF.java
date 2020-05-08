package com.umeng.hive.udtf;

import com.umeng.common.domain.AppLogAggEntity;
import com.umeng.common.domain.AppStartupLog;
import com.umeng.common.util.GeoUtil;

import java.util.List;

/**
 * 叉分启动日志
 */
public class ForkStartuplogsUDTF extends BaseForkUDTF<AppStartupLog>{
	public List<AppStartupLog> getLogs(AppLogAggEntity agg) {
		return agg.getStartupLogs();
	}

	/**
	 *重写该方法，设置地理信息
	 */
	protected void extraProcess(AppLogAggEntity agg,String clientip) {
		List<AppStartupLog> logs = getLogs(agg);
		for(AppStartupLog log : logs){
			log.setIpAddress(clientip);
			String[] arr = GeoUtil.getGeoInfo(clientip);
			//设置国家，省份
			log.setCountry(arr[0]);
			log.setProvince(arr[1]);
		}
	}
}
