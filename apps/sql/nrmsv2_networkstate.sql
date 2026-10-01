select id, updatedby, dateupdated from nrmsv2.networkstate order by id desc;

select * from nrmsv2.networkstate order by id desc limit 2;
select * from nrmsv2.networkstate where id = 596;

update nrmsv2.networkstate set networkimage='' where id='635';

#delete from nrmsv2.networkstate where id = '959';

