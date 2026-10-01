select customer, customer_sid, substring(pm_source, 6), node_id 
FROM pm_db.service_source 
where source like 'CIENA%' and `visible_to_customer`=1 and `customer_sid` not in 
	(select SUBSTRING_INDEX(customer_sid, ':', -1) 
	from service_source 
    where `customer_sid` like '%::%' and source like 'CIENA%') 
order by customer, customer_sid;

select * from ciena_backhaul_status_config;

SELECT customer, customer_sid, pm_source FROM pm_db.service_source where source = 'TE-SUBCOM' order by customer, customer_sid;

SELECT * FROM pm_db.service_source;




