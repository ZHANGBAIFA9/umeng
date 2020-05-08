use uemng_bigdata ;
create table if not exists stat_act_month(
  month string ,
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

insert into table stat_act_month
select
  '${ym}' ,
  tt.appid ,
  tt.appplatform,
  tt.brand ,
  tt.devicestyle,
  tt.ostype ,
  tt.appversion ,
  count(tt.deviceid)
FROM
(
  select
    t.appid ,
    t.appplatform,
    t.brand ,
    t.devicestyle,
    t.ostype ,
    t.appversion ,
    t.deviceid
  FROM
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
      ym = '${ym}'
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
)tt
group BY
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