-- appusagelog
use umeng_bigdata ;

insert into appusagelogs partition(ym , day)
select
  t.appChannel ,
  t.appId ,
  t.appPlatform ,
  t.appVersion ,
  t.brand ,
  t.createdAtMs ,
  t.deviceId ,
  t.deviceStyle ,
  t.osType ,
  t.singleDownloadTraffic ,
  t.singleUploadTraffic ,
  t.singleUseDurationSecs ,
  t.tenantId ,
date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,
date_format(cast(t.createdatms as timestamp) , 'dd')
from
 (
    select forkusagelogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs where ym='202002' and day='16'
  )t;

