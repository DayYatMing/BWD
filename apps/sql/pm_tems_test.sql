SELECT * FROM pm_db.tems_och WHERE `Date/Time` < now() GROUP BY `Date/Time` DESC;
SELECT * FROM pm_db.tems_och where `PM Source` = 'AUSY:PORT-1-4' order by `Date/Time` desc;

SELECT customer_sid, now() FROM pm_db.tems_och WHERE `Date/Time` < now() GROUP BY customer_sid ORDER BY `Date/Time` DESC;

SELECT * from pm_db.tems_och where `Date/Time` like '2025-09-20%' order by id desc; #OOS-BC0788 UBP-BC0458
SELECT * from pm_db.tems_och where customer_sid = "XUC-BC0443" order by id desc;
SELECT * from pm_db.tems_och order by id desc; 
#SET SQL_SAFE_UPDATES = 0;
#update ignore pm_db.tems_och set `PM Source` = REPLACE(`PM Source`, 'PTP', 'OTUTTP') where customer_sid = "RLZ-BX0053";

SELECT * FROM Segment;
SELECT * FROM service_source;
SELECT * FROM network_configuration;
SELECT * FROM site_weight;

SELECT * from pm_db.tems_och where `PM Source`='USFB:ETTP-1-21-33' and `Date/Time` like '2025-05-27 14%' order by `Date/Time` desc;

select avg(t1.`Link Fail Seconds - Out`) as outV, avg(t1.`Link Fail Seconds - In`) as inV FROM tems_och t1 WHERE t1.`customer_sid` = "UBP-BC0019" ORDER BY t1.`Date/Time` DESC LIMIT 100;

select tbl.dtm, tbl.val, 
269, 
tbl.cus from 
(select `Date/TimeDBQueryDBQuery` as dtm, `Link Fail Seconds - Out` as val, `customer_sid` as cus FROM tems_och tems WHERE tems.`customer_sid` = "UBP-BC0019" ORDER BY `Date/Time` DESC LIMIT 100) as tbl
order by tbl.dtm DESC;

SELECT date_time, serviceid, pmsource, nodeid, 
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
     END AS esout 
FROM (
SELECT `Date/Time` AS date_time, customer_sid AS serviceid, `PM Source` AS pmsource, node_id AS nodeid,
		COALESCE(`Physical Error Count - In`, 0) AS pecin, 
        COALESCE(`Physical Error Count - Out`, 0) AS pecout, 
		COALESCE(`Severely Errored Seconds - In`, 0) AS sesin, 
        COALESCE(`Severely Errored Seconds - Out`, 0) AS sesout,
        COALESCE(`Errored Seconds - In`, 0) AS esin, 
        COALESCE(`Errored Seconds - Out`, 0) AS esout
FROM pm_db.tems_och 
WHERE `PM Source` LIKE "%:PORT-%" 
AND (`Physical Error Count - In` >= 1 
OR  `Physical Error Count - Out` >= 1
OR  `Severely Errored Seconds - In` >= 1
OR  `Severely Errored Seconds - Out` >= 1
OR  `Errored Seconds - In` >= 1
OR  `Errored Seconds - Out` >= 1)
AND Type IS NULL 
AND `Date/Time` BETWEEN SUBTIME(CURRENT_TIMESTAMP(),"01:15:00") AND SUBTIME(CURRENT_TIMESTAMP(),"01:00:00")
ORDER BY `Date/Time` DESC
) raw_values
;





