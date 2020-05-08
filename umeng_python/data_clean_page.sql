-- apppagelog
use big12_umeng ;
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
    select forkpagelogs(servertimestr ,clienttimems ,clientip ,json) from raw_logs limit 10
  )t;

