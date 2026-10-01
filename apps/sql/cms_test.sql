SELECT * FROM cms.client_login_ip;

SELECT * FROM cms.customer;

SELECT * FROM cms.entity;

SELECT id, name, status, entity_id, duration FROM cms.orders;


-- To update orders.entity_id based on entity.id 
-- SET SQL_SAFE_UPDATES = 0;
-- UPDATE orders o
-- JOIN entity e 
--     ON o.name LIKE CONCAT(e.short_name, '%')
-- SET o.entity_id = e.id;

