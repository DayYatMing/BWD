select id, updatedby, dateupdated from nrmsv2.networkstate order by id desc;

select * from nrmsv2.networkstate order by id desc limit 2;
select * from nrmsv2.networkstate where id = 297;

select * from pm_db.ciena_backhaul_status_config;

SELECT * FROM ciena_backhaul_raw raw WHERE facilityNameNative like '%OPTMON-%' ORDER BY id DESC limit 12;
SELECT raw.id, con.facilityNameNative, raw.facilityState, con.directionNative, con.network_id, con.attrs FROM ciena_backhaul_status_config con JOIN (SELECT * FROM ciena_backhaul_raw raw WHERE facilityNameNative like '%OPTMON-%' ORDER BY id DESC limit 12) raw ON raw.facilityNameNative = con.facilityNameNative;