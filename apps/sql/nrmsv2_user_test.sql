select * from nrmsv2.jhi_user_authority;
select * from nrmsv2.jhi_authority;
select * from nrmsv2.jhi_user;

select * from nrmsv2.jhi_persistent_audit_event order by event_date desc;
select * from nrmsv2.jhi_persistent_audit_evt_data;

select * from nrmsv2.networkstate where id = "298";

select user0_.id as id1_5_, user0_.created_by as created_2_5_, user0_.created_date as created_3_5_, user0_.last_modified_by as last_mod4_5_, user0_.last_modified_date as last_mod5_5_, user0_.activated as activate6_5_, user0_.activation_key as activati7_5_, user0_.email as email8_5_, user0_.first_name as first_na9_5_, user0_.image_url as image_u10_5_, user0_.lang_key as lang_ke11_5_, user0_.last_name as last_na12_5_, user0_.login as login13_5_, user0_.notificationfeatures as notific14_5_, user0_.password_hash as passwor15_5_, user0_.reset_date as reset_d16_5_, user0_.reset_key as reset_k17_5_, user0_.userimage as userima18_5_ from jhi_user user0_ where upper(user0_.email)=upper("oyip@bw-digital.com");