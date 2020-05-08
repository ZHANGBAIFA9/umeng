use umeng_bigdata ;
create table if not exists stat_new_day(
  day string ,
  appid string,
  appplatform string,
  brand string ,
  devicestyle string,
  ostype string ,
  appversion string ,
  cnt int
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
lines terminated by '\n';

insert into table stat_new_day
select
  '2020/02/16' ,
  t.appid ,
  t.appversion ,
  t.appplatform,
  t.brand ,
  t.devicestyle,
  t.ostype ,
  count(t.deviceid) cnt
from
(
select
      appid ,
      appplatform,
      brand ,
      devicestyle,
      ostype ,
      appversion ,
      deviceid ,
      min(createdatms) firsttime
    from
      appstartuplogs
    group BY
      appid ,
      appplatform,
      brand ,
      devicestyle,
      ostype ,
      appversion,
      deviceid
    with cube
)t
where
  t.appid is not null
  and t.deviceid is not null
  and formatbyday(t.firsttime , 0 ,'yyyy/MM/dd') ='2020/02/16'
group by
  t.appid ,
  t.appversion ,
  t.appplatform,
  t.brand ,
  t.devicestyle,
  t.ostype
order by
  t.appid ,
  t.appversion ,
  t.appplatform,
  t.brand ,
  t.devicestyle,
  t.ostype

