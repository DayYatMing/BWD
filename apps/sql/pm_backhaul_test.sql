select * from ciena_backhaul_config order by date_time desc;

select * from ciena_backhaul_raw order by date_time desc;
select * from ciena_backhaul_raw where facilityState IS NOT NULL and facilityNameNative like "OPTMON-%" and value=0 order by date_time desc;
select cls_name, ncid, shelf, slot, port, facilityNameNative from ciena_backhaul_raw where facilityState IS NOT NULL and facilityNameNative like "OPTMON-%";
select * from ciena_backhaul_status_config;

select raw.id, con.facilityNameNative, raw.facilityState, con.directionNative, con.network_id, con.attrs 
from ciena_backhaul_status_config con 
	join (select * from ciena_backhaul_raw raw order by id desc limit 12) raw 
    on raw.facilityNameNative = con.facilityNameNative;

select * from ciena_tokens;
#set SQL_SAFE_UPDATES = 0; 
#delete from ciena_backhaul_raw where value IS NULL and granularityNativeSeconds IS NULL;
select * from ciena_backhaul_raw where raised IS NOT NULL order by raised desc;

select count(*) from ciena_backhaul_config;
select * from ciena_backhaul_raw order by date_time DESC; #11616236
select count(*) from ciena_backhaul_raw_archive; #9674890

select * from ciena_backhaul_config where cls_name like "USPA%";

select * from ciena_backhaul_raw 
where facilityState IS NOT NULL and facilityNameNative like "OPTMON%"
group by facilityNameNative;


###############################################
select cls_name, ncid, shelf, slot, port, facilityNameNative, device_name, 
SUBTIME(CURRENT_TIMESTAMP(),"01:00:00") date_timestart,
SUBTIME(CURRENT_TIMESTAMP(),"00:15:00") date_timeend 
from ciena_backhaul_raw 
where facilityState IS NOT NULL and facilityNameNative like "OPTMON%"
group by facilityNameNative;

select cls_name, ncid, shelf, slot, port, facilityNameNative, parameterNative, device_name 
from ciena_backhaul_raw 
where facilityState IS NOT NULL 
and facilityNameNative like "OPTMON-%" 
and raised IS NULL
group by facilityNameNative;

