CREATE USER 'root' IDENTIFIED BY 'onlinestore';
GRANT ALL PRIVILEGES ON onlinestore.* TO 'root';
CALL mysql.az_load_timezone();
SELECT name FROM mysql.time_zone_name;
