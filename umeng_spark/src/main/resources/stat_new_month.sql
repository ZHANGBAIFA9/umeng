use umeng_bigdata ;
create table if not exists stat_new_month(
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

insert into table stat_new_month
SELECT
  '202002' ,
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
  formatbymonth(day ,'yyyy/MM' , 0 , 'yyyy/MM') = '2020/02'
group by
  appid ,
  appversion ,
  appplatform,
  brand ,
  devicestyle,
  ostype