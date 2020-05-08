-- appstartuplog
use umeng_bigdata ;

insert into appstartuplogs partition(ym , day)
select
  t.appChannel ,
  t.appId ,
  t.appPlatform ,
  t.appVersion ,
  t.brand ,
  t.carrier ,
  t.country ,
  t.createdAtMs ,
  t.deviceId ,
  t.deviceStyle ,
  t.ipAddress ,
  t.network ,
  t.osType ,
  t.province ,
  t.screenSize ,
  t.tenantId ,
  formatbyday(t.createdatms , 0 , 'yyyyMM') ,
  formatbyday(t.createdatms , 0 , 'dd')
from
 (
    select forkstartuplogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs where ym='202002' and day='16'
  )t;
