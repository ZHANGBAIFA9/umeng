use umeng_bigdata ;
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
lines terminated by '\n'
stored as text;

create table if not exists stat_act_week(
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
lines terminated by '\n'
stored as text;

create table if not exists stat_act_month(
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
lines terminated by '\n'
stored as text;
