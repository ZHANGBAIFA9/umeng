-- apppagelog
use umeng_bigdata ;
insert into apppagelogs partition(ym , day)
select
  t.appChannel ,
  t.appId ,
  t.appPlatform ,
  t.appVersion ,
  t.brand ,
  t.createdAtMs ,
  t.deviceId ,
  t.deviceStyle ,
  t.nextPage ,
  t.osType ,
  t.pageId ,
  t.pageViewCntInSession ,
  t.stayDurationSecs ,
  t.tenantId ,
  t.visitIndex ,
date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,
date_format(cast(t.createdatms as timestamp) , 'dd')
from
 (
    select forkpagelogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs where ym='202002' and day='16'
  )t;

