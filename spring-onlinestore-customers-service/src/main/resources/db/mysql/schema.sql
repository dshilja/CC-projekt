CREATE DATABASE IF NOT EXISTS onlinestore;
GRANT ALL PRIVILEGES ON onlinestore.* TO pc@localhost IDENTIFIED BY 'pc';

USE onlinestore;

CREATE TABLE IF NOT EXISTS products (
  product_id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  product_name VARCHAR(30),
  product_type varchar(255),
  product_price double,
  product_description VARCHAR(255),
  product_img_url varchar(255),
  INDEX(product_name)
) engine=InnoDB;
