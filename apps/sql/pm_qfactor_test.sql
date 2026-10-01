select * from tems_och;

select * from tems_och where customer_sid in ("T5.FP2.HIKA.194.2377") and `PM Source`="HIKA-CWSAI-8"; 
set SQL_SAFE_UPDATES = 0; #update is prohibited by setting 1, 0 is allowed.
update tems_och set customer_sid = "T5.FP1.HIKA.195.75" where customer_sid in ("T1.FP1.HIKA.195.75") and `PM Source`="HIKA-CWS5-5"; 
#Ignore duplicates
#update ignore tems_och set customer_sid = "T5.FP1.HIKA.195.75" where customer_sid in ("T1.FP1.HIKA.195.75") and `PM Source`="HIKA-CWS5-5"; 

select * from ciena_qfactor_config;
select * from ciena_qfactor_config where fibre_pair like "T5.FP6.HIKA." and cls_name like "%CWSAI-4";
INSERT INTO ciena_qfactor_config (fibre_pair, cls_name, f_name, frequency, disable_collection, visible_to_customer)
VALUES("T4.FP2.USFB.", "USFB-CWS5-3", "PTP-1-2", "194.825", 1, 1);
INSERT INTO ciena_qfactor_config (fibre_pair, cls_name, f_name, frequency, disable_collection, visible_to_customer)
VALUES("T4.FP2.USFB.", "HIKA-CWS5-4", "PTP-1-2", "194.825", 1, 1);

SELECT
customer_sid, concat(SUBSTRING_INDEX(`customer_sid`,'.',3),':',SUBSTRING_INDEX(`PM Source`,'-',-2),':',SUBSTRING_INDEX(`customer_sid`,'.',-2)) as metric
FROM tems_och tems
WHERE `customer_sid` LIKE 'T3.FP2.AUSY.%' and
      `customer_sid` LIKE 'T3.FP2.AUSY.%' and
      (SUBSTRING_INDEX(`customer_sid`, '.', -2) in ('195.4865', '195.4685')) 
      and `Type`='Ciena-QFactor';

select * from bandwidth_data_collection_tmp;

select * from pm_db.tems_och;
SELECT *
FROM pm_db.tems_och  WHERE `Date/Time`>'2023-05-15 00:00:00' 
	and `Date/Time`<'2023-05-15 00:30:00' 
    and `Q (dB) Minimum` IS NOT NULL 
    and `Type` in ('C100-MUX Line Card', 'C100-TRNS Line Card');
    
    


