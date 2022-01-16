DROP TABLE types IF EXISTS;
DROP TABLE products IF EXISTS;

CREATE TABLE types (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX types_name ON types (name);

CREATE TABLE products (
  id         INTEGER IDENTITY PRIMARY KEY,
  product_name VARCHAR(30),
  last_name  VARCHAR(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
CREATE INDEX products_last_name ON products (last_name);
