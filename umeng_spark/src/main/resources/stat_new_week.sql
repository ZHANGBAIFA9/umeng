use umeng_bigdata ;
create table if not exists stat_new_week(
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

insert into table stat_new_week
SELECT
'2020/02/16' ,
  appid ,
  appversion ,
  appplatform,
  brand ,
  devicestyle,
  ostype ,
  sum(cnt) cnt
FROM
  stat_new_day
WHERE
  formatbyweek(day ,'yyyy/MM/dd' , 0 , 'yyyy/MM/dd') = formatbyweek('2020/02/16' ,'yyyy/MM/dd' , 0 , 'yyyy/MM/dd')
group by
  appid ,
  appversion ,
  appplatform,
  brand ,
  devicestyle,
  ostype