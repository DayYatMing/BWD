SELECT User, Host FROM mysql.user where User like "n%";
SELECT User, Host FROM mysql.user;

CREATE USER 'root'@'10.%.%.%' IDENTIFIED BY 'OKWFw0Oe8sKKXC';
GRANT ALL PRIVILEGES ON nms.* TO 'root'@'10.%.%.%';

DROP USER 'nms'@'10.%';

FLUSH PRIVILEGES;

