-- apperrorlog
use umeng_bigdata ;
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
    select forkerrorlogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs where ym='202002' and day='16'
  )t;

