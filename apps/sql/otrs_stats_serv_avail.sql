Select  
case	when 
	lefttable.customer  IS NULL then righttable.customer 
	ELSE lefttable.customer
	END AS  Customer , 
case	when 
	lefttable.serviceid  IS NULL then righttable.serviceid 
	ELSE lefttable.serviceid
	END AS  Serviceid , 
case	when 
	lefttable.`Billing Start Date` IS NULL then righttable.`Billing Start Date` 
	ELSE lefttable.`Billing Start Date`
	END AS  `Billing Start Date` ,
case	when 
	lefttable.`Decommission Date` IS NULL then righttable.`Decommission Date` 
	ELSE lefttable.`Decommission Date`
	END AS  `Decommission Date` ,
case	when 
	lefttable.`Status` IS NULL then righttable.`Status` 
	ELSE lefttable.`Status`
	END AS  `Status` ,
case	when 
	lefttable.`ServiceOutrage` IS NULL then righttable.`ServiceOutrage` 
	ELSE lefttable.`ServiceOutrage`
	END AS  `ServiceOutrage` ,
case	when 
	lefttable.`TotalTime` IS NULL then righttable.`TotalTime` 
	ELSE lefttable.`TotalTime`
	END AS  `TotalTime` ,
case	when 
	lefttable.`Availability%` IS NULL then righttable.`Availability%` 
	ELSE lefttable.`Availability%`
	END AS  `Availability` 

from (
select customer ,  e.serviceid ,e.BillingStartDate as `Billing Start Date`,e.Decommission_Date as `Decommission Date` , e.Status , e.ServiceOutrage , e.TotalTime , 
case 
  when e.BillingStartDate IS NULL or e.BillingStartDate= '' then 0
  when 1717541991642 < UNIX_TIMESTAMP(STR_TO_DATE(e.BillingStartDate, '%d/%m/%Y'))*1000 then 0
  ELSE (( e.TotalTime - e.ServiceOutrage )/e.TotalTime )* 100
  END AS `Availability%` 
from ( SELECT d.customer , d.serviceid
,d.BillingStartDate ,d.Decommission_Date ,
case 
    when d.BillingStartDate  IS NULL then 0
		when d.Decommission_Date  IS NULL then 3
		when ( 1704067200000 > UNIX_TIMESTAMP(STR_TO_DATE(d.Decommission_Date, '%d/%m/%Y'))*1000 ) And ( 1717541991642 > UNIX_TIMESTAMP(STR_TO_DATE(d.Decommission_Date, '%d/%m/%Y'))*1000 ) then 2.02
		when 1717541991642 > UNIX_TIMESTAMP(STR_TO_DATE(d.Decommission_Date, '%d/%m/%Y'))*1000 then 1
		when 1717541991642 < UNIX_TIMESTAMP(STR_TO_DATE(d.BillingStartDate, '%d/%m/%Y'))*1000 then 2
		ELSE 3
END AS Status
,  SUM( d.outragesec ) as ServiceOutrage,  
 case 
	when d.BillingStartDate IS NULL or d.BillingStartDate= '' then ( 1717541991642 - 1704067200000 )/1000
	when 1717541991642 < UNIX_TIMESTAMP(STR_TO_DATE(d.BillingStartDate, '%d/%m/%Y'))*1000 then 0
	when ( 1717541991642 > UNIX_TIMESTAMP(STR_TO_DATE(d.Decommission_Date, '%d/%m/%Y'))*1000 ) and (1704067200000 < UNIX_TIMESTAMP(STR_TO_DATE(d.Decommission_Date, '%d/%m/%Y'))*1000 ) then  ( UNIX_TIMESTAMP(STR_TO_DATE(d.Decommission_Date, '%d/%m/%Y'))*1000 - 1704067200000)/1000
	when  1704067200000 < UNIX_TIMESTAMP(STR_TO_DATE(d.BillingStartDate, '%d/%m/%Y'))*1000  then ( 1717541991642 - UNIX_TIMESTAMP(STR_TO_DATE(d.BillingStartDate, '%d/%m/%Y'))*1000 )/1000
	when  1704067200000 > UNIX_TIMESTAMP(STR_TO_DATE(d.BillingStartDate, '%d/%m/%Y'))*1000  then ( 1717541991642 - 1704067200000 )/1000
	ELSE ( 1717541991642 - UNIX_TIMESTAMP(STR_TO_DATE(d.BillingStartDate, '%d/%m/%Y'))*1000)/1000  
	END AS TotalTime 
FROM ( SELECT * FROM 
(
SELECT 
cs.customer , cs.name AS serviceid , tds.tttype AS TYPE , 
tds.RepairStartTime , tds.RecoveryStartTime ,  tds.id,
case 
		when tds.outragesec  IS NULL then 0
		ELSE tds.outragesec
END AS outragesec, cs.validity ,  cs.BillingStartDate , cs.Decommission_Date , cs.UseInCalculation 
,  tds.tktstatename

FROM (
SELECT cs1.name, cs1.service_id , cs1.customer, cs1.validity, 
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</BillingStartDate>', 1) ,'<BillingStartDate>',-1)) = CHARACTER_LENGTH(sp.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</BillingStartDate>', 1) ,'<BillingStartDate>',-1)
END AS BillingStartDate	,
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</Decommission_Date>', 1) ,'<Decommission_Date>',-1)) = CHARACTER_LENGTH(sp.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</Decommission_Date>', 1) ,'<Decommission_Date>',-1)
END AS Decommission_Date	,
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</UseInCalculation>', 1) ,'<UseInCalculation>',-1)) = CHARACTER_LENGTH(sp.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</UseInCalculation>', 1) ,'<UseInCalculation>',-1)
END AS UseInCalculation	
FROM (
SELECT s.name ,   s.id as service_id, custcomp.name AS customer ,   s.valid_id AS validity 
from service s,service_customer_user sc,
customer_user cu,customer_company custcomp  WHERE  
sc.service_id=s.id and 	cu.login=sc.customer_user_login and 
custcomp.customer_id=cu.customer_id 

	AND s.name NOT LIKE '_Identity and Access Management (Portals)'
	AND s.name NOT LIKE '_Network Maintenance'
	AND s.name NOT LIKE '_Server Incident'
	AND s.name NOT LIKE 'Provisioning'
	AND s.name NOT LIKE 'Priority SLA'
	AND s.name NOT LIKE 'Mutiple Telstra Services'
	AND custcomp.name !='HAWAIKIPORTAL' 
	AND custcomp.name != 'hawaiki-cab'  
	AND custcomp.name != 'Hawaiki Incident'
	AND custcomp.name != 'Hawaiki Limited' 
	AND custcomp.name != 'HAWAIKI_NOC'
	AND custcomp.name != 'test'

AND custcomp.name IN ( SELECT NAME  FROM customer_company  where  valid_id=1  AND    NAME !='HAWAIKIPORTAL' AND NAME!= 'hawaiki-cab'   AND name != 'Hawaiki Incident'   AND name != 'Hawaiki Limited'   AND name != 'HAWAIKI_NOC'   ORDER by name ) AND s.name IN (SELECT s.name AS serviceid  from service s where s.name NOT LIKE '_Identity and Access Management (Portals)' 	AND s.name NOT LIKE '_Network Maintenance' 	AND s.name NOT LIKE '_Server Incident' AND s.name NOT LIKE '_Wireless Network' AND s.name NOT LIKE '_VPN' AND s.name NOT LIKE '_Switch PORT' AND s.name NOT LIKE '_Network Access' AND s.name NOT LIKE '_Firewall Policy' AND s.name NOT LIKE '_Firewall Operations' AND s.name NOT LIKE '_Desktop Productivity Tools' AND s.name NOT LIKE '_Desktop Management' AND s.name NOT LIKE '_Desktop Hardware' AND s.name NOT LIKE 'Priority SLA' AND s.name NOT LIKE 'Mutiple Telstra Services%' AND s.name NOT LIKE 'MVY-BX0081/MVY-BX0200' AND s.name NOT LIKE 'ONC-BX0033/ONC-BX0062/ONC-BX0063' AND s.name NOT LIKE '_Backups and archiving' AND s.name NOT LIKE 'Provisioning' AND cu.valid_id = 1	group by serviceid ORDER BY serviceid)  ORDER BY s.NAME , custcomp.name) cs1
  LEFT JOIN service_preferences sp 
 ON cs1.service_id = sp.service_id 
 ) cs LEFT JOIN  
 (
	
	SELECT
	df.RepairStartTime ,  df.RecoveryStartTime , TIMESTAMPDIFF(SECOND, df.RepairStartTime, df.RecoveryStartTime) 
	AS outragesec , 
	t.id , t.tn , t.title , t.service_id , t.customer_id , ts.name AS tktstatename , 
	tt.name AS tttype  
	from ticket t left join  
	(select drows.object_id,max(case when label="Criticality" then value end) as "Criticality",
		max(case when label="Summary" then value end) as "Summary",
		max(case when label="Due Date" then value end) as "DueDate",
		max(case when label="Impact" then value end) as "Impact",
		max(case when label="Master Ticket" then value end) as "MasterTicket",
		max(case when label="Recovery Start Time" then value end) as "RecoveryStartTime",
		max(case when label="Repair Start Time" then value end) as "RepairStartTime",
		max(case when label="Review Required" then value end) as "Review Required"
	from 
		(
		select
			dn.label,dv.object_id,
			case 
			when dv.value_text is not null then value_text
			when dv.value_date is not null then value_date end as value
		from dynamic_field_value dv join dynamic_field dn on dv.field_id=dn.id) drows
		group by drows.object_id) df on df.object_id=t.id,
	ticket_state ts,ticket_type tt,ticket_priority tp,	queue q 
 where 
	t.ticket_state_id=ts.id and
	t.type_id=tt.id and
	q.id=t.queue_id and
	(q.name not like "%IT%" and q.name not like "%RAW%" and q.name not like "%Mailbox%") and
	tp.id=t.ticket_priority_id 
	AND  
    `RepairStartTime` BETWEEN FROM_UNIXTIME(1704067200) AND FROM_UNIXTIME(1717541991) 

 ) tds  ON 
 cs.service_id = tds.service_id
 where  (COALESCE(tds.tttype,'') IN (( SELECT name from ticket_type where valid_id = 1 )) OR tds.tttype IS NULL ) OR ((  SELECT name FROM service LIMIT 1) )
 GROUP BY cs.customer , cs.service_id , tds.id , cs.validity
		ORDER BY cs.customer , cs.service_id 
		) e 
	WHERE  COALESCE(e.tktstatename,'') LIKE  '%%'	
	)	d 
 	GROUP BY d.serviceid, d.customer 
		ORDER BY d.customer , d.serviceid ) e
 ) lefttable RIGHT JOIN  ( 
SELECT cs.name AS serviceid , cs.customer AS customer , cs.BillingStartDate as `Billing Start Date` , 
cs.Decommission_Date as `Decommission Date` , 
case 
    when cs.BillingStartDate  IS NULL then 0
		when cs.Decommission_Date  IS NULL then 3
		when ( 1704067200000 > UNIX_TIMESTAMP(STR_TO_DATE(cs.Decommission_Date, '%d/%m/%Y'))*1000 ) And ( 1717541991642 > UNIX_TIMESTAMP(STR_TO_DATE(cs.Decommission_Date, '%d/%m/%Y'))*1000 ) then 2.02
		when 1717541991642 > UNIX_TIMESTAMP(STR_TO_DATE(cs.Decommission_Date, '%d/%m/%Y'))*1000 then 1
		when 1717541991642 < UNIX_TIMESTAMP(STR_TO_DATE(cs.BillingStartDate, '%d/%m/%Y'))*1000 then 2
		ELSE 3
END AS Status
,  0 as ServiceOutrage,  
 case 
	when cs.BillingStartDate IS NULL or cs.BillingStartDate= '' then ( 1717541991642 - 1704067200000 )/1000
	when 1717541991642 < UNIX_TIMESTAMP(STR_TO_DATE(cs.BillingStartDate, '%d/%m/%Y'))*1000 then 0
	when ( 1717541991642 > UNIX_TIMESTAMP(STR_TO_DATE(cs.Decommission_Date, '%d/%m/%Y'))*1000 ) and (1704067200000 < UNIX_TIMESTAMP(STR_TO_DATE(cs.Decommission_Date, '%d/%m/%Y'))*1000 ) then  ( UNIX_TIMESTAMP(STR_TO_DATE(cs.Decommission_Date, '%d/%m/%Y'))*1000 - 1704067200000)/1000
	when  1704067200000 < UNIX_TIMESTAMP(STR_TO_DATE(cs.BillingStartDate, '%d/%m/%Y'))*1000  then ( 1717541991642 - UNIX_TIMESTAMP(STR_TO_DATE(cs.BillingStartDate, '%d/%m/%Y'))*1000 )/1000
	when  1704067200000 > UNIX_TIMESTAMP(STR_TO_DATE(cs.BillingStartDate, '%d/%m/%Y'))*1000  then ( 1717541991642 - 1704067200000 )/1000
	ELSE ( 1717541991642 - UNIX_TIMESTAMP(STR_TO_DATE(cs.BillingStartDate, '%d/%m/%Y'))*1000)/1000  
	END AS TotalTime , 100 AS  `Availability%`
FROM (
SELECT cs1.name, cs1.service_id , cs1.customer, cs1.validity, 
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</BillingStartDate>', 1) ,'<BillingStartDate>',-1)) = CHARACTER_LENGTH(sp.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</BillingStartDate>', 1) ,'<BillingStartDate>',-1)
END AS BillingStartDate	,
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</Decommission_Date>', 1) ,'<Decommission_Date>',-1)) = CHARACTER_LENGTH(sp.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</Decommission_Date>', 1) ,'<Decommission_Date>',-1)
END AS Decommission_Date	,
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</UseInCalculation>', 1) ,'<UseInCalculation>',-1)) = CHARACTER_LENGTH(sp.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(sp.preferences_value, '</UseInCalculation>', 1) ,'<UseInCalculation>',-1)
END AS UseInCalculation	
FROM (
SELECT s.name ,   s.id as service_id, custcomp.name AS customer ,   s.valid_id AS validity 
from service s,service_customer_user sc,
customer_user cu,customer_company custcomp  WHERE  
sc.service_id=s.id and 	cu.login=sc.customer_user_login and 
custcomp.customer_id=cu.customer_id 

	AND s.name NOT LIKE '_Identity and Access Management (Portals)'
	AND s.name NOT LIKE '_Network Maintenance'
	AND s.name NOT LIKE '_Server Incident'
	AND s.name NOT LIKE 'Provisioning'
	AND s.name NOT LIKE 'Priority SLA'
	AND s.name NOT LIKE 'Mutiple Telstra Services'
	AND custcomp.name !='HAWAIKIPORTAL' 
	AND custcomp.name != 'hawaiki-cab'  
	AND custcomp.name != 'Hawaiki Incident'
	AND custcomp.name != 'Hawaiki Limited' 
	AND custcomp.name != 'HAWAIKI_NOC'
	AND custcomp.name != 'test'
  AND custcomp.name IN ( SELECT NAME  FROM customer_company  where  valid_id=1  AND    NAME !='HAWAIKIPORTAL' AND NAME!= 'hawaiki-cab'   AND name != 'Hawaiki Incident'   AND name != 'Hawaiki Limited'   AND name != 'HAWAIKI_NOC'   ORDER by name )
  AND s.name IN (SELECT s.name AS serviceid  from service s where s.name NOT LIKE '_Identity and Access Management (Portals)' 	AND s.name NOT LIKE '_Network Maintenance' 	AND s.name NOT LIKE '_Server Incident' AND s.name NOT LIKE '_Wireless Network' AND s.name NOT LIKE '_VPN' AND s.name NOT LIKE '_Switch PORT' AND s.name NOT LIKE '_Network Access' AND s.name NOT LIKE '_Firewall Policy' AND s.name NOT LIKE '_Firewall Operations' AND s.name NOT LIKE '_Desktop Productivity Tools' AND s.name NOT LIKE '_Desktop Management' AND s.name NOT LIKE '_Desktop Hardware' AND s.name NOT LIKE 'Priority SLA' AND s.name NOT LIKE 'Mutiple Telstra Services%' AND s.name NOT LIKE 'MVY-BX0081/MVY-BX0200' AND s.name NOT LIKE 'ONC-BX0033/ONC-BX0062/ONC-BX0063' AND s.name NOT LIKE '_Backups and archiving' AND s.name NOT LIKE 'Provisioning' AND cu.valid_id = 1	group by serviceid ORDER BY serviceid)  ORDER BY s.NAME , custcomp.name) cs1
  LEFT JOIN service_preferences sp 
 ON cs1.service_id = sp.service_id 
 ) cs LEFT JOIN  
 (
	
	SELECT
	df.RepairStartTime ,  df.RecoveryStartTime , TIMESTAMPDIFF(SECOND, df.RepairStartTime, df.RecoveryStartTime) 
	AS outragesec , 
	t.id , t.tn , t.title , t.service_id , t.customer_id , ts.name AS tktstatename , 
	tt.name AS tttype  
	from ticket t left join  
	(select drows.object_id,max(case when label="Criticality" then value end) as "Criticality",
		max(case when label="Summary" then value end) as "Summary",
		max(case when label="Due Date" then value end) as "DueDate",
		max(case when label="Impact" then value end) as "Impact",
		max(case when label="Master Ticket" then value end) as "MasterTicket",
		max(case when label="Recovery Start Time" then value end) as "RecoveryStartTime",
		max(case when label="Repair Start Time" then value end) as "RepairStartTime",
		max(case when label="Review Required" then value end) as "Review Required"
	from 
		(
		select
			dn.label,dv.object_id,
			case 
			when dv.value_text is not null then value_text
			when dv.value_date is not null then value_date end as value
		from dynamic_field_value dv join dynamic_field dn on dv.field_id=dn.id) drows
		group by drows.object_id) df on df.object_id=t.id,
	ticket_state ts,ticket_type tt,ticket_priority tp,	queue q 
 where 
	t.ticket_state_id=ts.id and
	t.type_id=tt.id and
	q.id=t.queue_id and
	(q.name not like "%IT%" and q.name not like "%RAW%" and q.name not like "%Mailbox%") and
	tp.id=t.ticket_priority_id 
	AND  
    `RepairStartTime` BETWEEN FROM_UNIXTIME(1704067200) AND FROM_UNIXTIME(1717541991) 

 ) tds  ON 
 cs.service_id = tds.service_id
 GROUP BY cs.customer , cs.service_id , tds.id , cs.validity
		ORDER BY cs.customer , cs.service_id 
 ) righttable ON 
 righttable.serviceid = lefttable.serviceid;