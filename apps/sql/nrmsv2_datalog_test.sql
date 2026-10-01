
select * from nrmsv2.DATABASECHANGELOGLOCK;


update nrmsv2.DATABASECHANGELOGLOCK
set locked=0, lockgranted=null, lockedby=null
where id=1;

select * from nrmsv2.DATABASECHANGELOG;
select * from nrmsv2.DBQuery;

show processlist;
kill 623003;
