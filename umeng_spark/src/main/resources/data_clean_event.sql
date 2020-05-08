-- appeventlog
use umeng_bigdata ;
insert into appeventlogs partition(ym , day)
select
  t.appChannel ,
  t.appId ,
  t.appPlatform ,
  t.appVersion ,
  t.brand ,
  t.createdAtMs ,
  t.deviceId ,
  t.deviceStyle ,
  t.eventDurationSecs ,
  t.eventId ,
  t.osType ,
  t.tenantId ,
date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,
date_format(cast(t.createdatms as timestamp) , 'dd')
from
 (
    select forkeventlogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs where ym='202002' and day='16'
  )t;

