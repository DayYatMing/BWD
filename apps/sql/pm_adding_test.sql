SELECT * FROM pm_db.customer;
SELECT * FROM pm_db.service;
SELECT * FROM pm_db.service_source;
SELECT * FROM pm_db.configuration;
select * from tems_och where customer_sid in ("MMF-BX1117", "MVY-BX0081") order by customer_sid DESC;

#########################################
######### Adding PM below ###############
#########################################
SELECT * FROM pm_db.customer WHERE shortname in ("VGZ");

SELECT * FROM pm_db.service_source WHERE customer_sid in ("UBP-BC0027");
#UPDATE pm_db.service_source SET pm_source = "HIKA:ETTP-5-5-17" WHERE id IN (841);
INSERT INTO pm_db.service_source (customer, customer_sid, pm_source, visible_to_customer, route_end, segment_end, source, node_id, to_from, comm_limit, start_date, end_date, route, segment)
VALUES("CHARTER", "CAN-BC1091", "HIKA:PORT-2-4", "1", "A", "1A", "CIENA-WS", "d53ea14e-babf-34ca-856c-f96e3574e920", null, null, null, null, "HI-UF-001", "HI-UF-27"); 
INSERT INTO pm_db.service_source (customer, customer_sid, pm_source, visible_to_customer, route_end, segment_end, source, node_id, to_from, comm_limit, start_date, end_date, route, segment)
VALUES("CHARTER", "CAN-BC1091", "USFB:PORT-2-4", "1", "Z", "1Z", "CIENA-WS", "b1b43e65-5fab-3e5b-8ab1-ef84cf49a787", null, null, null, null, "HI-UF-001", "HI-UF-27");

SELECT * FROM pm_db.service WHERE serviceid in ("URO-BC1278", 'TMK-BC1269' , 'JMV-BC1222');
SELECT * FROM pm_db.service WHERE customer_id="2";
Update pm_db.service set serviceid = "DEC-BC0311_TEST" where serviceid like "AAA-BX0557_%";
#note this table has one less column 'masked' on pre prd, only prd has extra column 'masked' 
#below SQL for PRE-PROD
INSERT INTO pm_db.service (serviceid, customer_id, startdate, enddate, bandwidth, active, visible_to_customer)
VALUES("CAN-BC1089", "67", null, null, "100 GB", "1", "1"); 

SELECT * FROM pm_db.configuration WHERE serviceid in ("UBP-BC0027");
#UPDATE pm_db.configuration SET sourcename = "HIKA:ETTP-5-5-17" and label_name = "HIKA:C6500-2:ETTP-5-5-17" WHERE id IN (929);
INSERT INTO pm_db.configuration (serviceid, sourcename, nodeid, vendor, label_name, collection_duration, route_direction, segment_direction, nodedisplayname, disable_collection, visible_to_customer, frequency)
VALUES("CAN-BC1089", "HIKA:PORT-2-3", "d53ea14e-babf-34ca-856c-f96e3574e920", "CWSAI-9", "HIKA-CWSAI-9:PORT-2-3", null, "A", "1A", null, 0, 1, "195.7900"); 
INSERT INTO pm_db.configuration (serviceid, sourcename, nodeid, vendor, label_name, collection_duration, route_direction, segment_direction, nodedisplayname, disable_collection, visible_to_customer, frequency)
VALUES("CAN-BC1089", "USFB:PORT-2-3", "b1b43e65-5fab-3e5b-8ab1-ef84cf49a787", "CWSAI-5", "USFB-CWSAI-5:PORT-2-3", null, "Z", "1Z", null, 0, 1, "195.7900");  

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
	SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, '-', 2), '-', -1) as slot,
	SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, '-', 4), '-', -1) as port,
    substring(s.pm_source,1,4) cls_name,
    STR_TO_DATE('2025-09-20 00:00:00', '%Y-%m-%d %H:%i:%s') date_timestart,
	STR_TO_DATE('2025-09-20 23:59:59', '%Y-%m-%d %H:%i:%s') date_timeend,
    SUBSTRING_INDEX(s.pm_source, ":", -1) as fname,
    s.pm_source,
	s.customer_sid,
	s.node_id as ncid
from
	service_source s, configuration cnf
where
	s.source = 'CIENA-WS' and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and cnf.disable_collection='0' and cnf.serviceid = "XUC-BC0443"
) temp
group by
	shelf, slot, port, cls_name, customer_sid, ncid
order by
	1,2,3,4,5; 
