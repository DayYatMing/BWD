SELECT * FROM timeline order by event_date desc LIMIT 100;

select * from networkstate;

SELECT * , name as customer FROM (SELECT *  , substring(con.serviceid, 1, 3) AS shrtnm  FROM pm_db.configuration con  ) AS  con JOIN pm_db.customer cust ON  con.shrtnm = cust.shortname  JOIN pm_db.service ser ON con.serviceid = ser.serviceid  WHERE ser.active = 1 AND label_name IS NOT NULL GROUP BY label_name ;

