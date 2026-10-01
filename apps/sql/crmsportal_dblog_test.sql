
select * from crms_portal.DATABASECHANGELOGLOCK;

update crms_portal.DATABASECHANGELOGLOCK
set locked=0, lockgranted=null, lockedby=null
where id=1;