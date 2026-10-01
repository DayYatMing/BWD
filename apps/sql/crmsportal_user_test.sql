select * from crms_portal.jhi_user order by id desc;
select id, login, activated, reset_key, reset_date, last_modified_by, last_modified_date, failed_logins from crms_portal.jhi_user order by id desc limit 5;
select * from crms_portal.client_login_ip order by attempt_time desc;

select * from crms_portal.jhi_authority;
select * from crms_portal.jhi_user_authority order by user_id desc;
select * from crms_portal.jhi_persistent_audit_event order by event_id desc;
select * from crms_portal.jhi_persistent_audit_evt_data order by event_id desc;
