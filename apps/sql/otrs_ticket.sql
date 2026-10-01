select * from queue where id=6;
select * from ticket_lock_type;
select * from ticket_type where id=5;
select * from service where id=299;
select * from sla;
select * from users where id=56;
select * from ticket_priority where id=4;
select * from ticket_state where id=20;
select * from customer_company where customer_id="BHT_ONENZ";

select * from customer_user;
select * from customer_user where email like "%fb.com%" or email like "%meta.com%";

#UPDATE otrs.ticket SET service_id = '411' WHERE (id = '62632');
select * from ticket order by id desc;
select * from ticket where create_by=3 order by id desc;
select * from ticket_flag where ticket_id='61756' order by ticket_id desc;
select * from ticket_history where name like '%2024121090000012%' order by create_time desc;

select * from communication_log_object_entry where log_value like "%catch-up threshold of 5 minutes was exceeded%";
select * from communication_log_object where id="1964492";
select * from communication_log where id=1668169;

select * from article where id like "180287%";
select * from article_data_mime_send_error where article_id like "180287%";
select * from article_data_mime where a_from = 'Christian Alvaro <chralv@bw-digital.com>';

select * from calendar order by id desc;
select * from calendar_appointment where id=23953 order by id desc;
select id, title, start_time, end_time, notify_time, recur_until, create_time, create_by from calendar_appointment where id = 23953 order by id desc;












