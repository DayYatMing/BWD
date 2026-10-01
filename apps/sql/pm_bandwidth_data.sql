SELECT * FROM pm_db.bandwidth_data_collection WHERE service_id = "DEC-BC0320" ORDER BY collectiontime DESC;
SELECT * FROM pm_db.bandwidth_data_collection WHERE collectiontime > "2025-05-09 03:10:00" AND collectiontime < NOW() AND service_id LIKE "DEC-BC0320" ORDER BY collectiontime DESC;

SELECT * FROM pm_db.bandwidth_data_collection WHERE service_id LIKE "RLZ-BX0049" and collectiontime > "2024-04-09" and collectiontime < "2024-04-10 00:30:00";
SELECT * FROM pm_db.bandwidth_data_collection WHERE collectiontime > "2024-04-09" and collectiontime < "2024-04-10 00:30:00" and rxmaxvalue=0 and txmaxvalue=0 and rxminvalue=0 and txminvalue=0 and rxavgvalue=0 and txavgvalue=0;

SELECT * FROM pm_db.bandwidth_data_collection WHERE service_id LIKE "RLZ-BX0048" or service_id LIKE "RLZ-BC0028" ORDER BY collectiontime DESC;

SELECT * FROM pm_db.bandwidth_data_collection WHERE collectiontime > "2024-02-08";

SELECT * FROM pm_db.bandwidth_data_collection ORDER BY collectiontime DESC;
SELECT * FROM pm_db.bandwidth_data_collection ORDER BY ID DESC;

SELECT * FROM pm_db.bandwidth_data_collection WHERE collectiontime >= "2024-03-03 00:00:00" and collectiontime < "2024-03-06 00:00:00" ORDER BY collectiontime DESC;
#DELETE FROM pm_db.bandwidth_data_collection WHERE collectiontime >= "2024-02-25 00:00:00" and collectiontime < "2024-02-26 00:00:00";
SELECT * FROM pm_db.bandwidth_data_collection WHERE collectiontime >= "2024-02-26 00:45:00" and collectiontime < "2024-02-26" and service_id = "IQX-BC1053" and node_id LIKE "96f6e6c1-441c-33be-ad2a-3fd0cb21a%";

SELECT * FROM pm_db.configuration WHERE serviceid LIKE "ETA%";
SELECT * FROM pm_db.service_source WHERE customer = "AUSSIE BROADBAND" GROUP BY customer_sid;

SELECT distinct(customer) FROM service_source where customer != 'HAWAIKI' and source like 'CIENA%';
SELECT customer_sid FROM service_source WHERE customer="COLT" and visible_to_customer='1' and source like 'CIENA%' group by customer_sid;
SELECT pm_source FROM service_source WHERE  customer="COLT" AND customer_sid LIKE "PTJ-%" and source like 'CIENA%' and (pm_source like '%PORT%' OR pm_source like '%ETTP%') group by pm_source;

SELECT * FROM pm_db.ciena_tokens;
SELECT * FROM network_configuration;

