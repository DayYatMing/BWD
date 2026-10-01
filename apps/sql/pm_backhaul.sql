select * from ciena_backhaul_config order by date_time desc;

select * from ciena_backhaul_raw order by date_time desc limit 12;
select * from ciena_backhaul_raw where facilityState IS NOT NULL and facilityNameNative like "OPTMON-%";
select cls_name, ncid, shelf, slot, port, facilityNameNative from ciena_backhaul_raw where facilityState IS NOT NULL and facilityNameNative like "OPTMON-%";
select * from ciena_backhaul_status_config;

select * from ciena_tokens;

select * from ciena_backhaul_raw group by device_name;

select count(*) from ciena_backhaul_config;
select * from ciena_backhaul_raw order by date_time DESC; #11616236
select count(*) from ciena_backhaul_raw_archive; #9674890

select * from ciena_backhaul_config where cls_name like "USPA%";

select * from ciena_backhaul_raw;


select cls_name, ncid, shelf, slot, port, facilityNameNative, device_name, 
SUBTIME(CURRENT_TIMESTAMP(),"01:00:00") date_timestart,
SUBTIME(CURRENT_TIMESTAMP(),"00:15:00") date_timeend 
from ciena_backhaul_raw 
where facilityState IS NOT NULL and facilityNameNative like "OPTMON%"
group by device_name;

#ALTER TABLE ciena_backhaul_raw ADD facilityState varchar(255);	
#ALTER TABLE ciena_backhaul_raw ADD device_name varchar(255);	
#ALTER TABLE ciena_backhaul_raw ADD raised datetime;

-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USPA", "ab89ea0e-d6e5-3afb-8657-d22f9d71700b", "6", "1", "13", null, "OPTMON-6-1-13", "Nestucca", 0, "Hot standby", "USPA-C6500-2");
-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USFB", "42a864fd-a2de-3a47-a546-fb96ede1787b", "7", "1", "13", null, "OPTMON-7-1-13", "Nestucca", 0, "Hot standby", "USFB-C6500-4");

-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USPA", "ab89ea0e-d6e5-3afb-8657-d22f9d71700b", "6", "1", "15", null, "OPTMON-6-1-15", "Salmon", 0, "Working receiver", "USPA-C6500-2");
-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USFB", "42a864fd-a2de-3a47-a546-fb96ede1787b", "7", "1", "15", null, "OPTMON-7-1-15", "Salmon", 0, "Working receiver", "USFB-C6500-4");

-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USPA", "4aa64460-0755-38e3-9b9d-37d2d6c64130", "5", "5", "13", null, "OPTMON-5-5-13", "Nestucca", 0, "Hot standby", "USPA-C6500-3");
-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USFB", "70b6e294-7228-3abd-a398-75704e09e9b5", "5", "3", "13", null, "OPTMON-5-3-13", "Nestucca", 0, "Hot standby", "USFB-C6500-3");

-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USPA", "4aa64460-0755-38e3-9b9d-37d2d6c64130", "5", "5", "15", null, "OPTMON-5-5-15", "Salmon", 0, "Working receiver", "USPA-C6500-3");
-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USFB", "70b6e294-7228-3abd-a398-75704e09e9b5", "5", "3", "15", null, "OPTMON-5-3-15", "Salmon", 0, "Working receiver", "USFB-C6500-3");

-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USPA", "ab89ea0e-d6e5-3afb-8657-d22f9d71700b", "4", "1", "13", null, "OPTMON-4-1-13", "Nestucca", 0, "Hot standby", "USPA-C6500-2");
-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USFB", "8f46f3d8-554a-3e1f-a48c-6ad1aed40a4f", "3", "2", "13", null, "OPTMON-3-2-13", "Nestucca", 0, "Hot standby", "USFB-C6500-2");

-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USPA", "ab89ea0e-d6e5-3afb-8657-d22f9d71700b", "4", "1", "15", null, "OPTMON-4-1-15", "Salmon", 0, "Working receiver", "USPA-C6500-2");
-- insert into ciena_backhaul_raw (date_time, granularityNativeSeconds, cls_name, ncid, shelf, slot, port, directionNative, facilityNameNative, parameterNative, value, facilityState, device_name) 
-- values("2024-03-11 00:00:00", null, "USFB", "8f46f3d8-554a-3e1f-a48c-6ad1aed40a4f", "3", "2", "15", null, "OPTMON-3-2-15", "Salmon", 0, "Working receiver", "USFB-C6500-2");