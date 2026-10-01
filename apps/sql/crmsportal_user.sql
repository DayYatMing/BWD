select * from crms_portal.jhi_user order by id desc;

select * from crms_portal.jhi_user_authority order by user_id desc;
select * from crms_portal.jhi_persistent_audit_event order by event_id desc;
select * from crms_portal.jhi_persistent_audit_evt_data order by event_id desc;

select * from crms_portal.client_login_ip;

select us.login, us.email, us.failed_logins, lo.attempt_time, lo.logged_in 
from crms_portal.jhi_user us, crms_portal.client_login_ip lo 
where us.login = lo.login and lo.login = 'GhOfwiIDLICYOdTsL56Y1Q==';



