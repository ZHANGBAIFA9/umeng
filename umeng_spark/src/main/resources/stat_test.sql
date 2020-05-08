use umeng_bigdata ;

select
distinct
  ym,
  day
from
  appstartuplogs
where
  ym = formatbyday(-1 , 'yyyyMM')
order by
  ym ,
  day
