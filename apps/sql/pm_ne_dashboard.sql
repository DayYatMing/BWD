SELECT * FROM pm_db.customer;
SELECT * FROM pm_db.service;
SELECT * FROM pm_db.service_source;
SELECT * FROM pm_db.configuration;

select * from pm_db.service_source where customer = 'GOOGLE' and customer_sid = 'GLE-BC0680' and pm_source = 'AUSY:PORT-1-3';
select * from pm_db.service_source where visible_to_customer = 1 and source like 'CIENA%';
select * from pm_db.configuration where serviceid = 'VGZ-BC0080';
select * from pm_db.configuration where serviceid = 'GLE-BC0680' and sourcename = 'AUSY:PORT-1-3';

SELECT * FROM pm_db.customer where shortname like '%OMA%';
SELECT * FROM pm_db.service where serviceid in ('IQX-BC1053', 'OMA-BC0034');
select * from pm_db.service_source where customer_sid in ('IQX-BC1053', 'OMA-BC0034');
select * from pm_db.configuration where serviceid in ('IQX-BC1053', 'OMA-BC0034');

SELECT label_name FROM pm_db.configuration WHERE serviceid="GLE-BC0680" and `disable_collection`='0' group by sourcename order by sourcename;

select * from ciena_ne_config;
SELECT * FROM pm_db.tems_och order by id desc;
SELECT * FROM pm_db.tems_och_ne order by id desc;
SELECT * FROM pm_db.tems_och_ne_ptp order by id desc;
SELECT * FROM pm_db.tems_och_ne where `PM Source` like 'ASTA:ETTP-1-1-17' order by `Date/Time` desc;
SELECT * FROM pm_db.tems_och_ne where customer_sid in ('ETA-BC0668') order by id desc;

select temp.ne_sites, temp.ne_types, temp.ne_instances, cls_name, vendor, fname, customer_sid, pm_source, ncid from
(
select
	cnc.ne_sites, 
    cnc.ne_types,
	CONCAT(SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, ":", -1), "-", -2), "-", cnc.instances_name)  as ne_instances,
    substring(s.pm_source,1,4) as cls_name,
    cnf.vendor,
    CONCAT(substring(s.pm_source,1,4), "-", cnf.vendor) as sname,
    SUBSTRING_INDEX(s.pm_source, ":", -1) as fname,
    s.pm_source,
	s.customer_sid,
	s.node_id as ncid
from
	service_source s, configuration cnf, ciena_ne_config cnc
where
	#s.source = 'CIENA-WS' #for WSAI
    s.source = 'CIENA-WS5' #for WS5
    and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1' 
) temp, ciena_ne_config cnconfig
where temp.sname = cnconfig.sites_name and temp.ne_sites = cnconfig.ne_sites
group by
	cls_name, customer_sid, ncid;

    
     select shelf, slot, port, cls_name,date_timestart,date_timeend, fname, customer_sid,pm_source, ncid from
(
select
    SUBSTRING_INDEX(cnf.vendor, '-', -1) as shelf,
	SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, '-', 3), '-', -1) as slot,
	SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, '-', 4), '-', -1) as port,
    substring(s.pm_source,1,4) cls_name,
    SUBTIME(CURRENT_TIMESTAMP(),"00:15:00") date_timestart,
	CURRENT_TIMESTAMP() date_timeend,
    SUBSTRING_INDEX(s.pm_source, ":", -1) as fname,
    s.pm_source,
	s.customer_sid,
	s.node_id as ncid
from
	service_source s, configuration cnf
where
	s.source = 'CIENA' and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1'
) temp
group by
	shelf, slot, port, cls_name, ncid
order by
	1,2,3,4,5;
    
    
