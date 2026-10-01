SELECT * FROM pm_db.tems_och order by id desc;
SELECT * FROM pm_db.tems_och where customer_sid = 'RYU-BX1373' order by id desc;
SELECT * FROM pm_db.tems_och where `PM Source` = 'AUSY:PORT-1-3' order by id desc;
SELECT * FROM pm_db.tems_och where customer_sid like '%ASGO%' order by id desc;

SELECT * FROM pm_db.tems_och where node_id like "%PTP%" order by id desc;
select * from pm_db.tems_och where customer_sid in ("OMA-BC0034") order by `Date/Time` desc limit 100;

SELECT customer_sid, now() FROM pm_db.tems_och WHERE `Date/Time` < now() GROUP BY customer_sid ORDER BY `Date/Time` DESC;

SELECT * from pm_db.tems_och where `PM Source`='USFB:ETTP-1-21-33' and `Date/Time` like '2025-05-27 %' order by `Date/Time` desc;
    #155618287	2025-05-27 14:45:00	IOA-BX0539	USFB:ETTP-1-21-33	46147a67-1425-33bf-a847-7c08224a7811										6				6	6		900	900
	#155617596	2025-05-27 14:30:00	IOA-BX0539	USFB:ETTP-1-21-33	46147a67-1425-33bf-a847-7c08224a7811										2				2	2		900	900
	#155643086	2025-05-27 14:15:00	IOA-BX0539	USFB:ETTP-1-21-33	46147a67-1425-33bf-a847-7c08224a7811						16	900	900	900	900	900	900	16	900	900		900	900
	#155616239	2025-05-27 14:00:00	IOA-BX0539	USFB:ETTP-1-21-33	46147a67-1425-33bf-a847-7c08224a7811										11				11	11		900	900
#SET SQL_SAFE_UPDATES = 0;
#update ignore pm_db.tems_och set `PM Source` = REPLACE(`PM Source`, 'PTP', 'OTUTTP') where customer_sid = "RLZ-BX0053";

SELECT * FROM Segment;
SELECT * FROM service_source;
SELECT * FROM network_configuration;
SELECT * FROM site_weight;



SELECT date_time, customer, serviceid, pmsource, vendor, nodeid, 
CASE WHEN pecin >=1 THEN "Degraded Service" 
	 ELSE "0"
     END AS pecin, 
CASE WHEN pecout >=1 THEN "Degraded Service" 
	 ELSE "0"
     END AS pecout, 
CASE WHEN sesin > 10 THEN "Service Down" 
	 WHEN sesin >= 1 THEN "Degraded Service" 
	 ELSE "0"
     END AS sesin, 
CASE WHEN sesout > 10 THEN "Service Down" 
	 WHEN sesin >= 1 THEN "Degraded Service" 
	 ELSE "0"
     END AS sesout, 
CASE WHEN esin >=1 THEN "Degraded Service" 
	 ELSE "0"
     END AS esin,
CASE WHEN esout >=1 THEN "Degraded Service" 
	 ELSE "0"
     END AS esout,
CASE WHEN esout >=0 THEN "0" 
	 ELSE "0"
     END AS count 
FROM (
SELECT tem1.date_time, tem1.customer, tem1.serviceid, tem1.pmsource, con.vendor, tem1.nodeid, tem1.pecin, tem1.pecout, tem1.sesin, tem1.sesout, tem1.esin, tem1.esout
FROM (
SELECT tem.date_time, cus.name AS customer, tem.serviceid, tem.pmsource, tem.nodeid, tem.pecin, tem.pecout, tem.sesin, tem.sesout, tem.esin, tem.esout
FROM (
SELECT `Date/Time` AS date_time, customer_sid AS serviceid, `PM Source` AS pmsource, node_id AS nodeid,
		COALESCE(`Physical Error Count - In`, 0) AS pecin, 
        COALESCE(`Physical Error Count - Out`, 0) AS pecout, 
		COALESCE(`Severely Errored Seconds - In`, 0) AS sesin, 
        COALESCE(`Severely Errored Seconds - Out`, 0) AS sesout,
        COALESCE(`Errored Seconds - In`, 0) AS esin, 
        COALESCE(`Errored Seconds - Out`, 0) AS esout
FROM pm_db.tems_och_ne 
WHERE `PM Source` LIKE "%:PORT-%" 
AND (`Physical Error Count - In` >= 1 
OR  `Physical Error Count - Out` >= 1
OR  `Severely Errored Seconds - In` >= 1
OR  `Severely Errored Seconds - Out` >= 1
OR  `Errored Seconds - In` >= 1
OR  `Errored Seconds - Out` >= 1)
AND Type IS NULL 
AND `Date/Time` BETWEEN SUBTIME(CURRENT_TIMESTAMP(),"00:30:00") AND SUBTIME(CURRENT_TIMESTAMP(),"00:15:00")
ORDER BY `Date/Time` DESC
) tem, pm_db.customer cus, pm_db.service ser
WHERE tem.serviceid = ser.serviceid
AND ser.customer_id = cus.id
) tem1, pm_db.configuration con
WHERE tem1.serviceid = con.serviceid
GROUP BY tem1.pmsource
) raw_values
;

