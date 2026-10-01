SELECT Customer , ServiceId , Capacity , Segment , BillingStartDate , Decommission_Date ,  ServiceOutrage , TotalTime ,
case 
  when BillingStartDate IS NULL or BillingStartDate= '' then 0
  when 1717541893508 < UNIX_TIMESTAMP(STR_TO_DATE(BillingStartDate, '%d/%m/%Y'))*1000 then 0
  ELSE (( TotalTime - ServiceOutrage )/TotalTime )* 100
  END AS `Availability%` 
from 
(
SELECT Customer , ServiceId , Capacity , Segment , BillingStartDate , Decommission_Date , SUM( outragesec ) as ServiceOutrage ,
case 
	when BillingStartDate IS NULL or BillingStartDate= '' then ( 1717541893508 - 1685919553508 )/1000
	when 1717541893508 < UNIX_TIMESTAMP(STR_TO_DATE(BillingStartDate, '%d/%m/%Y'))*1000 then 0
	when ( 1717541893508 > UNIX_TIMESTAMP(STR_TO_DATE(Decommission_Date, '%d/%m/%Y'))*1000 ) and (1685919553508 < UNIX_TIMESTAMP(STR_TO_DATE(Decommission_Date, '%d/%m/%Y'))*1000 ) then  ( UNIX_TIMESTAMP(STR_TO_DATE(Decommission_Date, '%d/%m/%Y'))*1000 - 1685919553508)/1000
	when  1685919553508 < UNIX_TIMESTAMP(STR_TO_DATE(BillingStartDate, '%d/%m/%Y'))*1000  then ( 1717541893508 - UNIX_TIMESTAMP(STR_TO_DATE(BillingStartDate, '%d/%m/%Y'))*1000 )/1000
	when  1685919553508 > UNIX_TIMESTAMP(STR_TO_DATE(BillingStartDate, '%d/%m/%Y'))*1000  then ( 1717541893508 - 1685919553508 )/1000
	ELSE ( 1717541893508 - UNIX_TIMESTAMP(STR_TO_DATE(BillingStartDate, '%d/%m/%Y'))*1000)/1000  
	END AS TotalTime 

from (
SELECT Customer , ServiceId , Capacity , dls as Segment , 
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(servicename.preferences_value, '</BillingStartDate>', 1) ,'<BillingStartDate>',-1)) = CHARACTER_LENGTH(servicename.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(servicename.preferences_value, '</BillingStartDate>', 1) ,'<BillingStartDate>',-1)
END AS BillingStartDate	,
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(servicename.preferences_value, '</Decommission_Date>', 1) ,'<Decommission_Date>',-1)) = CHARACTER_LENGTH(servicename.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(servicename.preferences_value, '</Decommission_Date>', 1) ,'<Decommission_Date>',-1)
END AS Decommission_Date	,
case 
		when CHARACTER_LENGTH(SUBSTRING_INDEX ( SUBSTRING_INDEX(servicename.preferences_value, '</UseInCalculation>', 1) ,'<UseInCalculation>',-1)) = CHARACTER_LENGTH(servicename.preferences_value) then NULL
		ELSE SUBSTRING_INDEX ( SUBSTRING_INDEX(servicename.preferences_value, '</UseInCalculation>', 1) ,'<UseInCalculation>',-1)
END AS UseInCalculation	, 
outragesec 
	FROM ( 
	SELECT df.dls , df.Capacity , 
	df.RepairStartTime ,  df.RecoveryStartTime , TIMESTAMPDIFF(SECOND, df.RepairStartTime, df.RecoveryStartTime) 
	AS outragesec , 
	t.id , t.tn , t.title , t.service_id , t.customer_id , ts.name AS tktstatename , 
	tt.name AS tttype , Summary as ClosedSummary
	from ticket t left join  
	(select drows.object_id,max(case when label="Criticality" then value end) as "Criticality",
		max(case when label="Summary" then value end) as "Summary",
		max(case when label="Due Date" then value end) as "DueDate",
		max(case when label="Impact" then value end) as "Impact",
		max(case when label="Master Ticket" then value end) as "MasterTicket",
		max(case when label="Recovery Start Time" then value end) as "RecoveryStartTime",
		max(case when label="Repair Start Time" then value end) as "RepairStartTime",
		max(case when label="Review Required" then value end) as "Review Required",
		max(case when label="DLS" then value end) as "dls", 
	  max(case when label="Capacity" then value end) as "Capacity"
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
   	CASE WHEN 
   	false = true THEN  
		 ( `RepairStartTime` IS null ) OR (   `RepairStartTime` BETWEEN FROM_UNIXTIME(1685919553) AND FROM_UNIXTIME(1717541893) )
		ELSE
      `RepairStartTime` BETWEEN FROM_UNIXTIME(1685919553) AND FROM_UNIXTIME(1717541893) 
	END
    AND 
    ts.name = 'On-Net-Close' 
    and df.dls in ('NZ-HI-01','AS-HI-02','AU-HI-04','NZ-AU-06','HI-UP-07','UP-UF-10','NZ-HI-11','UP-UF-12','AU-HI-14','NZ-AU-16','HI-UP-17','HI-UF-27','UF-UW-30','HI-UL-31','AU-GM-34','AU-NZ-36','AU-HI-54','HI-UF-57')) tickets JOIN 
     ( 
		 SELECT s.name AS ServiceId,   s.id as service_id ,  custcomp.name AS Customer , sp.preferences_value 
		from service s,service_customer_user sc, service_preferences sp ,
		customer_user cu,customer_company custcomp  WHERE  s.id = sp.service_id and
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
		  AND s.name IN (SELECT s.name AS serviceid  from service s where s.valid_id = 1 and s.name NOT LIKE '_Identity and Access Management (Portals)' 	AND s.name NOT LIKE '_Network Maintenance' 	AND s.name NOT LIKE '_Server Incident' AND s.name NOT LIKE '_Wireless Network' AND s.name NOT LIKE '_VPN' AND s.name NOT LIKE '_Switch PORT' AND s.name NOT LIKE '_Network Access' AND s.name NOT LIKE '_Firewall Policy' AND s.name NOT LIKE '_Firewall Operations' AND s.name NOT LIKE '_Desktop Productivity Tools' AND s.name NOT LIKE '_Desktop Management' AND s.name NOT LIKE '_Desktop Hardware' AND s.name NOT LIKE 'Priority SLA' AND s.name NOT LIKE 'Mutiple Telstra Services%' 
		  AND s.name NOT LIKE 'MVY-BX0081/MVY-BX0200' AND s.name NOT LIKE 'ONC-BX0033/ONC-BX0062/ONC-BX0063' 
		  AND s.name NOT LIKE '_Backups and archiving' AND s.name NOT LIKE 'Provisioning'	group by serviceid 
		  AND s.valid_id = 1
		  ORDER BY serviceid)  
		  ORDER BY s.NAME , custcomp.name  ) servicename ON 
		  tickets.service_id = servicename.service_id
		  where tttype = 'Incident'
		  GROUP BY id 
		  ) TicketServiceData GROUP BY Customer , ServiceId  ) TotalOutrage;