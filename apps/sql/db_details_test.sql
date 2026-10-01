SELECT `AUTO_INCREMENT`
FROM  INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'pm_db'
AND   TABLE_NAME   = 'customer';

#conf properties: /etc/mysql/mariadb.conf.d/50-server.cnf
#SET GLOBAL max_connections = 500; 
SHOW GLOBAL VARIABLES LIKE "max_connections";
show processlist;
kill 1115;



















































