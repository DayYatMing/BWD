SELECT * FROM pm_db.ciena_tokens;
SELECT * FROM pm_db.ciena_tokens where expirationTime is not null;
set SQL_SAFE_UPDATES = 0;
update pm_db.ciena_tokens set accessToken='e43ea41b615e2721bb5f' where accessToken='437e8db187fd93c71e5f';
update pm_db.ciena_tokens set createdTime='2024-12-16 01:15:30' where accessToken='995c46a91ab02843d826';
update pm_db.ciena_tokens set expirationTime='2026-05-23 01:22:31' where accessToken='5343384f5f927c9ff174';
#delete from pm_db.ciena_tokens where accessToken is null;

SELECT * FROM pm_db.ciena_tokens_test where expirationTime is not null;


SELECT
  concat("Bearer ",accessToken) as authorization
, createdTime
, expirationTime
, SYSDATE() < expirationTime as validToken
FROM ciena_tokens
ORDER BY expirationTime;