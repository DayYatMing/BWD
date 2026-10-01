SELECT * from pm_db.customer;
SELECT name FROM pm_db.customer where active=1 and name !='ONATI' and name !='MEGAPORT' order by name;

SELECT * FROM pm_db.service;
SELECT ser.serviceid FROM pm_db.service ser, customer cus 
WHERE ser.active=1 AND ser.serviceid not in 
			('DEC-BC0074','DEC-BC0075','DEC-BC0314','DEC-BC0317','DEC-BC0386','LWX-BX0022','WQP-BC0065','UBP-BC0458',
            'UBP-BC0464','RLZ-BX0048','MNT-BX0611','MNT-BX0608','RLZ-BC0644::DEC-BC0075','RLZ-BC0638::DEC-BC0074') 
	   AND cus.name in ('MICROSOFT', 'ASTCA') 
       AND ser.customer_id = cus.id  
	   AND CASE WHEN startdate IS NOT NULL THEN startdate<=SYSDATE() ELSE true end 
	   AND CASE WHEN enddate IS NOT NULL THEN enddate >= SYSDATE() ELSE true end group by serviceid;

SELECT * FROM pm_db.configuration;
SELECT sourcename FROM pm_db.configuration WHERE serviceid in ('MSO-BX0353', 'LWX-BX0023') group by sourcename order by sourcename;

SELECT * FROM network_configuration;
SELECT route FROM network_configuration GROUP BY route ORDER BY route;
SELECT segment FROM network_configuration where route in(SELECT route FROM network_configuration GROUP BY route) GROUP BY segment ORDER BY segment;

SELECT * FROM tems_och order by `Date/Time` desc;
SELECT * FROM tems_och 
WHERE customer_sid IN ('MSO-BX0353', 'LWX-BX0023')  
	AND `Date/Time` > "2024-02-19 00:15:25" AND `Date/Time` < "2024-02-19 03:15:25"  ;
    #AND  `Q (dB) Average` IS NOT NULL;
    
    SELECT * FROM ciena_qfactor_config;

SELECT * FROM(
Select * FROM (
SELECT * FROM(
SELECT  name as Customer , serviceid as Serviceid ,   
max(slfs) OVER (PARTITION BY Customer , serviceid) AS `Sum Link Fail Seconds`  , 
max(sses) OVER (PARTITION BY Customer , serviceid) AS `Sum Severely Errored Seconds` ,
max(ses) OVER  (PARTITION BY Customer , serviceid) AS `Sum Errored Seconds` ,
max(spec) OVER (PARTITION BY Customer , serviceid) AS `Sum Physical Error Count` 
FROM (
SELECT name , serviceid ,  slin + slout   AS  slfs ,  ssesi + sseso AS sses , sesi + seso AS ses, speci + speco AS spec   FROM 
(
SELECT name , serviceid 
 , `Date/Time` , COALESCE (`Link Fail Seconds - In`,0) AS slin ,  COALESCE(`Link Fail Seconds - OUT`,0)  AS slout 
 , COALESCE (`Severely Errored Seconds - In`,0) AS ssesi ,  COALESCE(`Severely Errored Seconds - Out`,0)  AS sseso 
 , COALESCE (`Errored Seconds - In`,0) AS sesi ,  COALESCE(`Errored Seconds - Out`,0)  AS seso 
 , COALESCE (`Physical Error Count - In`,0) AS speci ,  COALESCE(`Physical Error Count - Out`,0)  AS speco
FROM (
	SELECT * FROM ( 
		SELECT c.name, ser.serviceid , ser.customer_id FROM pm_db.customer c INNER JOIN pm_db.service ser ON c.id =  ser.customer_id where c.active=1 and ser.active=1  and c.NAME In ( SELECT name FROM pm_db.customer where active=1 order by name )
		AND ser.serviceid not in ('DEC-BC0074','DEC-BC0075','DEC-BC0314','DEC-BC0317','LWX-BX0022','WQP-BC0065','UBP-BC0458','UBP-BC0464','MNT-BX0611','MNT-BX0608','RLZ-BC0644::DEC-BC0075','RLZ-BC0638::DEC-BC0074','LWX-BX0023','MNT-BX0611','MNT-BX0608','OMA-BX0039','MSO-BX0350','MSO-BX0353','BOP-BX0275','BOP-BX0278','BOP-BX0281','BOP-BX0284','DEC-BC0920','DEC-BC0893','DEC-BC0924','DEC-BC0896','ONC-BX0794','ONC-BX0899','LGS-BX0455','LGS-BX0824','IQX-BX0398','RLZ-BX0056','GLE-BC1072','ZAQ-BX0476-TEMP','RLZ-BX0054','CAN-BC1089','CAN-BC1091','CAN-BC1093','CAN-BC1095','CAN-BC1097','CAN-BC1099','CAN-BC1103','CAN-BC1105','CAN-BC1107','CAN-BC1166','CAN-BC1169','CAN-BC1172','CAN-BC1175','CAN-BC1178','CAN-BC1181','CAN-BC1184','CAN-BC1187','CAN-BC1190','CAN-BC1193','DEC-BC1119','DEC-BC1123_TEST','DEC-BC1127_TEST','DEC-BC1129_TEST','DEC-BC1131_TEST','DEC-BC1133_TEST','DEC-BC1137_TEST','DEC-BC1139_TEST','DEC-BC1141_TEST','DEC-BC1145','TJO-BC0061','UBP-BC0018_TEST','UBP-BC0025_TEST','UBP-BC0026_TEST','UBP-BC0027_TEST','DEC-BC0314_PROV','DEC-BC0317_PROV','VGZ-BC1158_PROV','DEC-BC0896_PROV','DEC-BC0920_PROV','DEC-BC0924_PROV','URO-BC1109_TEST','URO-BC1149_PROV','URO-BC1155_PROV','VGZ-BC1158_PROV','DEC-BC1145_PROV','URO-BC1163_PROV','CAN-BC1089_TEST','CAN-BC1091_TEST','CAN-BC1093_TEST','CAN-BC1095_TEST','CAN-BC1097_TEST','CAN-BC1099_TEST','CAN-BC1103_TEST','CAN-BC1105_TEST','CAN-BC1107_TEST','DEC-BC1119_PROV','DEC-BC1123_PROV','DEC-BC1127_PROV','DEC-BC1129_PROV','DEC-BC1131_PROV','DEC-BC1133_PROV','DEC-BC1137_PROV','DEC-BC1139_PROV','DEC-BC1141_PROV','DEC-BC1143_PROV','CAN-BC1101_TEST','DEC-BC1131_PROV','CAN-BC1166_TEST','CAN-BC1169_TEST','CAN-BC1172_TEST','CAN-BC1175_TEST','CAN-BC1178_TEST','CAN-BC1181_TEST','CAN-BC1184_TEST','CAN-BC1187_TEST','CAN-BC1190_TEST','CAN-BC1193_TEST','CAC-BC1196_TEST','RLZ-BX0047')
		and ser.serviceid In ( SELECT ser.serviceid FROM pm_db.service ser, customer cus where ser.active=1  AND  cus.name in ( SELECT name FROM pm_db.customer where active=1 order by name ) AND ser.customer_id = cus.id AND CASE WHEN startdate IS NOT NULL THEN startdate<=SYSDATE() ELSE true end AND CASE WHEN enddate IS NOT NULL THEN enddate >= SYSDATE() ELSE true end group by serviceid )
        )s
	LEFT JOIN
		(SELECT * FROM tems_och
 		WHERE `Date/Time` BETWEEN FROM_UNIXTIME(1746041422) AND FROM_UNIXTIME(1746052222) AND `Type` IS NULL) tems
		ON s.serviceid=tems.customer_sid
	) tms where `Date/Time` IS NOT NULL GROUP BY node_id,`PM Source`,serviceid ,`Date/Time` 
) abc order by name , serviceid) def
) dta GROUP BY Customer, Serviceid ) sdata 
LEFT JOIN 
(
			SELECT customer_sid  , route , segment FROM service_source where visible_to_customer =1 
) srsrc ON 
	  srsrc.customer_sid = sdata.Serviceid 
	  where route in (SELECT route FROM network_configuration GROUP BY route ORDER BY route) and  segment in (SELECT segment FROM network_configuration  GROUP BY segment ORDER BY segment))adta GROUP BY Serviceid;