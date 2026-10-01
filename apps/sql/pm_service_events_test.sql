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

SELECT * FROM tems_och order by id desc;




