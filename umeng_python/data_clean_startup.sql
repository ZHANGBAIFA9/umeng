-- appstartuplog
use big12_umeng ;

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
date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,
date_format(cast(t.createdatms as timestamp) , 'dd')
from
 (
    select forkstartuplogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs limit 10
  )t;
