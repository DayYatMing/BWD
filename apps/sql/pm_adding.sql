SELECT * FROM pm_db.customer;
SELECT * FROM pm_db.service;
SELECT * FROM pm_db.service_source;
SELECT * FROM pm_db.configuration;
select * from tems_och where customer_sid in ("MMF-BX1117", "MVY-BX0081") order by customer_sid DESC;


#########################################
######### Adding PM below ###############
#########################################
SELECT * FROM pm_db.customer WHERE shortname in ("URO", 'TMK', "JMV");

SELECT * FROM pm_db.service WHERE serviceid in ("DEC-BC0075", 'CAC-BC1196');
SELECT * FROM pm_db.service WHERE customer_id="54";
#note this table has one less column 'masked' on pre prd, only prd has extra column 'masked' 
#below SQL for PRE-PROD
INSERT INTO pm_db.service (serviceid, customer_id, startdate, enddate, bandwidth, active, visible_to_customer, masked)
VALUES("CAN-BC1193", "67", null, null, "100 GB", "1", "1", "0"); 

SELECT * FROM pm_db.service_source WHERE customer_sid  in ("NJZ-BC1451");
#UPDATE pm_db.service_source SET pm_source = "HIKA:ETTP-5-5-17" WHERE id IN (841);
INSERT INTO pm_db.service_source (customer, customer_sid, pm_source, visible_to_customer, route_end, segment_end, source, node_id, to_from, comm_limit, start_date, end_date, route, segment)
VALUES("CHARTER", "CAN-BC1193", "HIKA:PORT-3-6", "1", "A", "1A", "CIENA-WS5", "fb8cfb4c-0faf-3241-a3f3-e5c4fe91b4df", null, null, null, null, "HI-UF-001", "HI-UF-57"); 
INSERT INTO pm_db.service_source (customer, customer_sid, pm_source, visible_to_customer, route_end, segment_end, source, node_id, to_from, comm_limit, start_date, end_date, route, segment)
VALUES("CHARTER", "CAN-BC1193", "USFB:PORT-3-6", "1", "Z", "1Z", "CIENA-WS5", "a26c2a6e-211d-3e64-96b0-02d809d54207", null, null, null, null, "HI-UF-001", "HI-UF-57");

SELECT * FROM pm_db.configuration WHERE serviceid in ("NJZ-BC1451");
#UPDATE pm_db.configuration SET sourcename = "HIKA:ETTP-5-5-17" and label_name = "HIKA:C6500-2:ETTP-5-5-17" WHERE id IN (929);
INSERT INTO pm_db.configuration (serviceid, sourcename, nodeid, vendor, label_name, collection_duration, route_direction, segment_direction, nodedisplayname, disable_collection, visible_to_customer, frequency)
VALUES("CAN-BC1193", "HIKA:PORT-3-6", "fb8cfb4c-0faf-3241-a3f3-e5c4fe91b4df", "CWS5-3", "HIKA:CWS5-3:PORT-3-6", null, "A", "1A", null, 0, 1, "195.545"); 
INSERT INTO pm_db.configuration (serviceid, sourcename, nodeid, vendor, label_name, collection_duration, route_direction, segment_direction, nodedisplayname, disable_collection, visible_to_customer, frequency)
VALUES("CAN-BC1193", "USFB:PORT-3-6", "a26c2a6e-211d-3e64-96b0-02d809d54207", "CWS5-2", "USFB:CWS5-2:PORT-3-6", null, "Z", "1Z", null, 0, 1, "195.545");  

#Active alarm list
SELECT customer, customer_sid, substring(pm_source, 6), node_id 
FROM pm_db.service_source 
WHERE source LIKE 'CIENA%' AND `visible_to_customer`=1 AND `customer_sid` NOT IN 
	(SELECT SUBSTRING_INDEX(customer_sid, ':', -1) FROM service_source WHERE `customer_sid` LIKE '%::%' AND source LIKE 'CIENA%') ORDER BY customer, customer_sid LIMIT 1000;
#note this is to be exported to replace CienaCustomerPMs.csv for sending alarm

select shelf, slot, port, cls_name,date_timestart,date_timeend, fname, customer_sid,pm_source, ncid from
(
select
    SUBSTRING_INDEX(cnf.vendor, '-', -1) as shelf,
	SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, '-', 3), '-', -1) as slot,
	SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, '-', 4), '-', -1) as port,
    substring(s.pm_source,1,4) cls_name,
    SUBTIME(CURRENT_TIMESTAMP(),"01:00:00") date_timestart,
	SUBTIME(CURRENT_TIMESTAMP(),"00:15:00") date_timeend,
    SUBSTRING_INDEX(s.pm_source, ":", -1) as fname,
    s.pm_source,
	s.customer_sid,
	s.node_id as ncid
from
	service_source s, configuration cnf
where
	s.source = 'CIENA' and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1' #RLZ-BX0048
	#s.source = 'CIENA-WS5' and cnf.vendor LIKE 'CWS5%' and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1'
	#s.source = 'CIENA-WS' and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1' #RLZ-BC0028
) temp
group by
	shelf, slot, port, cls_name, ncid
order by
	1,2,3,4,5;


SELECT * , name as customer FROM 
(SELECT *  , substring(con.serviceid, 1, 3) AS shrtnm  FROM configuration con  ) AS  con 
JOIN customer cust ON  con.shrtnm = cust.shortname  
JOIN service ser ON con.serviceid = ser.serviceid  WHERE ser.active = 1 AND label_name IS NOT NULL GROUP BY label_name;

select customer, customer_sid, substring(pm_source, 6)  as pm_source, node_id 
FROM pm_db.service_source where source like 'CIENA%' and `visible_to_customer`=1 and `customer_sid` not in (select SUBSTRING_INDEX(customer_sid, ':', -1) 
from service_source where `customer_sid` like '%::%' and source like 'CIENA%')  order by customer, customer_sid asc;

select customer, customer_sid, substring(pm_source, 6)  as pm_source, node_id 
FROM pm_db.service_source 
where source like 'CIENA%' and `visible_to_customer`=1 
order by customer, customer_sid asc;


select SUBSTRING(label_name, 1, 12) as result from pm_db.configuration where label_name like '%:C6500%' group by result UNION #13
select SUBSTRING(label_name, 1, 13) as result from pm_db.configuration where label_name like '%:CWSAI%' group by result UNION #25
select SUBSTRING(label_name, 1, 11) as result from pm_db.configuration where label_name like '%:CWS5%' group by result; #18 #total 56 source

select * from pm_db.configuration where sourcename like '%:PORT%';
select * from pm_db.configuration where sourcename like '%:ETTP%';
select * from pm_db.configuration where sourcename like '%:OTUTTP%';
select * from pm_db.configuration where sourcename like '%:STTP%';

select shelf, slot, port, cls_name,datetime_start,datetime_end, fname, customer_sid, pm_source, ncid from
(
select
    SUBSTRING_INDEX(SUBSTRING_INDEX(facilityNameNative, '-', -3), '-', 1) as shelf,
	SUBSTRING_INDEX(SUBSTRING_INDEX(facilityNameNative, '-', -2), '-', 1) as slot,
	SUBSTRING_INDEX(facilityNameNative, '-', -1) as port,
    in_out as customer_sid,
    cls_name,
    SUBTIME(CURRENT_TIMESTAMP(),"01:15:00") datetime_start,
	SUBTIME(CURRENT_TIMESTAMP(),"01:00:00") datetime_end,
    facilityNameNative as fname,
	ncid,
    shelf as pm_source
from
	ciena_backhaul_config 

) temp

order by
	1,2,3,4,5;