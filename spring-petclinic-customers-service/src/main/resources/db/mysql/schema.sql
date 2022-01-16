CREATE DATABASE IF NOT EXISTS petclinic;
GRANT ALL PRIVILEGES ON petclinic.* TO pc@localhost IDENTIFIED BY 'pc';

USE petclinic;

CREATE TABLE IF NOT EXISTS products (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  type VARCHAR(30),
  price VARCHAR(30),
  description VARCHAR(255),
  img_url VARCHAR(80),
  INDEX(name)
) engine=InnoDB;
