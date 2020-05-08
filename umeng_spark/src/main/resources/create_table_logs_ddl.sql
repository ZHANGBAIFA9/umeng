-- 创建日志子表
use umeng_bigdata ;
create table if not exists appstartuplogs (
  appchannel string ,
  appid string ,
  appplatform string ,
  appversion string ,
  brand string ,
  carrier string ,
  country string ,
  createdatms bigint ,
  deviceid string ,
  devicestyle string ,
  ipaddress string ,
  network string ,
  ostype string ,
  province string ,
  screensize string ,
  tenantid string)
PARTITIONED BY (ym string, day string)
stored as parquet ;

create table if not exists appeventlogs (
  appchannel string ,
  appid string ,
  appplatform string ,
  appversion string ,
  brand string ,
  createdatms bigint ,
  deviceid string ,
  devicestyle string ,
  eventdurationsecs string ,
  eventid string ,
  ostype string ,
  paramkeyvaluemap string ,
  tenantid string)
PARTITIONED BY (ym string, day string)
stored as parquet ;

create table if not exists apperrorlogs (
  appchannel string ,
  appid string ,
  appplatform string ,
  appversion string ,
  brand string ,
  createdatms bigint ,
  deviceid string ,
  devicestyle string ,
  errorbrief string ,
  errordetail string ,
  ostype string ,
  tenantid string)
PARTITIONED BY (ym string, day string)
stored as parquet ;

create table if not exists appusagelogs (
  appchannel string ,
  appid string ,
  appplatform string ,
  appversion string ,
  brand string ,
  createdatms bigint ,
  deviceid string ,
  devicestyle string ,
  ostype string ,
  singledownloadtraffic string ,
  singleuploadtraffic string ,
  singleusedurationsecs string ,
  tenantid string)
PARTITIONED BY (ym string, day string)
stored as parquet ;

create table if not exists apppagelogs (
  appchannel string ,
  appid string ,
  appplatform string ,
  appversion string ,
  brand string ,
  createdatms bigint ,
  deviceid string ,
  devicestyle string ,
  nextpage string ,
  ostype string ,
  pageid string ,
  pageviewcntinsession string ,
  staydurationsecs string ,
  tenantid string ,
  visitindex string)
PARTITIONED BY (ym string, day string)
stored as parquet ;