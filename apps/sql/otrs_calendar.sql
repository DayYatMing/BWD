select * from ticket order by id desc;
select * from ticket where title like '%TEST%' order by create_time desc;

select * from calendar_appointment order by create_time desc;
select * from calendar_appointment where id=17458;
select * from calendar_appointment where id=17458 or parent_id=17458;
select * from calendar_appointment where create_by = 56;
select * from calendar_appointment_plugin order by create_time desc;
select * from calendar_appointment_ticket;

select * from scheduler_future_task;
select * from scheduler_recurrent_task;
