SELECT * FROM pm_db.ciena_tokens;
SELECT * FROM pm_db.ciena_tokens where expirationTime is not null;
set SQL_SAFE_UPDATES = 0;
update pm_db.ciena_tokens set accessToken='5df62849ca6aeffb2343' where accessToken='d120a3f88df6deac9a6b';
update pm_db.ciena_tokens set createdTime='2025-06-11 00:43:47' where accessToken='bd884f51874c4aeb8af9';
update pm_db.ciena_tokens set expirationTime='2026-08-01 00:55:39' where accessToken='6954c60e4b82e9288010';
#delete from pm_db.ciena_tokens where accessToken=null;

SELECT * FROM pm_db.ciena_tokens where expirationTime is not null;


SELECT
  concat("Bearer ",accessToken) as authorization
, createdTime
, expirationTime
, SYSDATE() < expirationTime as validToken
FROM ciena_tokens
ORDER BY expirationTime;