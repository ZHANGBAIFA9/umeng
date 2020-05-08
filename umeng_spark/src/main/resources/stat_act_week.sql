use uemng_bigdata ;
select
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
      formatbyweek(concat(ym,day) , 'yyyyMMdd' , 0 , 'yyyyMMdd') = formatbyweek(-1 , 'yyyyMMdd')
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