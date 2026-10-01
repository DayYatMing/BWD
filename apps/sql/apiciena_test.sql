SELECT * FROM apiciena.entity;

SELECT * FROM apiciena.customer;

SELECT
	c.id,
	c.user,
	c.contact,
	c.address,
	e.name AS 'entityName',
	e.short_name AS 'entityShortName'
FROM customer c
INNER JOIN entity e
	ON c.entity_id = e.id;