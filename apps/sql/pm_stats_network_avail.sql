select customer as Customer , servicesource.customer_sid as ServiceId , Capacity, segment as Segment , 
 DATE_FORMAT(startdate, '%d/%m/%Y') as BillingStartDate , DATE_FORMAT(enddate, '%d/%m/%Y') as Decommission_Date ,  0 as ServiceOutrage , 
  ( 1717541893508 - 1685919553508 )/1000 AS TotalTime , 100 as `Availability%` 
from 
( SELECT customer , customer_sid , segment  FROM service_source where  segment is not null and segment in ( 'NZ-HI-01','AS-HI-02','AU-HI-04','NZ-AU-06','HI-UP-07','UP-UF-10','NZ-HI-11','UP-UF-12','AU-HI-14','NZ-AU-16','HI-UP-17','HI-UF-27','UF-UW-30','HI-UL-31','AU-GM-34','AU-NZ-36','AU-HI-54','HI-UF-57')
) servicesource join 
( select serviceid, active , CAST(SUBSTRING(bandwidth, 1, 3)  AS INT) as Capacity, startdate, enddate  from service where bandwidth in ( '10 GB','100 GB' ) and active = 1) service on servicesource.customer_sid = service.serviceid order by Customer, ServiceId , segment  ;


SELECT customer , customer_sid , segment  FROM service_source where  segment is not null and segment in ( 'NZ-HI-01','AS-HI-02','AU-HI-04','NZ-AU-06','HI-UP-07','UP-UF-10','NZ-HI-11','UP-UF-12','AU-HI-14','NZ-AU-16','HI-UP-17','HI-UF-27','UF-UW-30','HI-UL-31','AU-GM-34','AU-NZ-36','AU-HI-54','HI-UF-57');
select serviceid, active , CAST(SUBSTRING(bandwidth, 1, 3)  AS INT) as Capacity, startdate  from service where bandwidth in ( '10 GB','100 GB' ) and active = 1 ;

select * from service_source;
select * from service;
