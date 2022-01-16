CREATE DATABASE IF NOT EXISTS petclinic;
GRANT ALL PRIVILEGES ON petclinic.* TO pc@localhost IDENTIFIED BY 'pc';

USE petclinic;

CREATE TABLE IF NOT EXISTS products (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  product_name VARCHAR(30),
  price VARCHAR(30),
  address VARCHAR(255),
  category VARCHAR(30),
  telephone VARCHAR(20),
  INDEX(product_name)
) engine=InnoDB;
