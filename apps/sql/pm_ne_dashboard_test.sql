SELECT * FROM pm_db.customer;
SELECT * FROM pm_db.service;
SELECT * FROM pm_db.service_source;
SELECT * FROM pm_db.configuration;

select * from pm_db.service_source where visible_to_customer = '1';
select * from pm_db.configuration where disable_collection = '0' and vendor like "C%";
select * from pm_db.configuration where serviceid = 'DEC-BC0169' and sourcename = 'AUSY:PORT-1-3';

select label_name from pm_db.configuration where serviceid = 'GLE-BC0680' and sourcename = 'AUSY:PORT-1-3';

SELECT label_name FROM pm_db.configuration WHERE serviceid="GLE-BC0680" and `disable_collection`='0' group by sourcename order by sourcename;



select * from ciena_ne_config;
SELECT * FROM pm_db.tems_och_ne order by id desc;
SELECT * FROM pm_db.tems_och_ne_ptp order by id desc;
SELECT * FROM pm_db.tems_och_ne where `Date/Time` = "2025-04-14 21:45:00" order by `Date/Time` desc;


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
	s.source = 'CIENA-WS' #for WSAI
    #s.source = 'CIENA-WS5' #for WS5
    and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1' 
) temp, ciena_ne_config cnconfig
where temp.sname = cnconfig.sites_name and temp.ne_sites = cnconfig.ne_sites
group by
	cls_name, customer_sid, ncid;
    
select temp.ne_sites, temp.ne_types, temp.ne_instances, cls_name, vendor, REPLACE(fname, 'PORT', 'PTP') as fname, customer_sid, REPLACE(pm_source, 'PORT', 'PTP') as pm_source, ncid from
(
select
	cnc.ne_sites, 
    'optical-power-instances' as ne_types,
	CONCAT(SUBSTRING_INDEX(SUBSTRING_INDEX(s.pm_source, ":", -1), "-", -2), "-OpticalPower")  as ne_instances,
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
	s.source = 'CIENA-WS' #for WSAI
    #s.source = 'CIENA-WS5' #for WS5
    and s.pm_source=cnf.sourcename and s.customer_sid=cnf.serviceid and s.node_id=cnf.nodeid and s.visible_to_customer='1' 
) temp, ciena_ne_config cnconfig
where temp.sname = cnconfig.sites_name and temp.ne_sites = cnconfig.ne_sites
group by
	cls_name, customer_sid, ncid;





