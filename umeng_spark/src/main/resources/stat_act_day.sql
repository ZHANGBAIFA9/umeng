use uemng_bigdata ;
create table if not exists stat_act_day(
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

insert overwrite table stat_act_day
select
  formatbyday(-5, 'yyyyMMdd'),
  tt.appid ,
  tt.appplatform,
  tt.brand ,
  tt.devicestyle,
  tt.ostype ,
  tt.appversion ,
  count(tt.deviceid)
from
(
select
    t.appid ,
    t.appplatform,
    t.brand ,
    t.devicestyle,
    t.ostype ,
    t.appversion ,
    t.deviceid
from
(
select
      appid ,
      appplatform,
      brand ,
      devicestyle,
      ostype ,
      appversion ,
      deviceid
    from
      appstartuplogs
  WHERE
      concat(ym,day) = formatbyday(-5, 'yyyyMMdd')
  group by
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
  t.appid is  not null
  and t.deviceid is not null
)tt
group by
  tt.appid ,
  tt.appplatform,
  tt.brand ,
  tt.devicestyle,
  tt.ostype ,
  tt.appversion
order by
  tt.appid ,
  tt.appplatform,
  tt.brand ,
  tt.devicestyle,
  tt.ostype ,
  tt.appversion




