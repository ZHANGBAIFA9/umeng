-- apperrorlog
use big12_umeng ;
insert into apperrorlogs partition(ym , day)
select
  t.appChannel ,
  t.appId ,
  t.appPlatform ,
  t.appVersion ,
  t.brand ,
  t.createdAtMs ,
  t.deviceId ,
  t.deviceStyle ,
  t.errorBrief ,
  t.errorDetail ,
  t.osType ,
  t.tenantId ,
date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,
date_format(cast(t.createdatms as timestamp) , 'dd')
from
 (
    select forkerrorlogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs limit 10
  )t;


