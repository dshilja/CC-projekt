DROP TABLE products IF EXISTS;

CREATE INDEX types_name ON types (name);

CREATE TABLE products (
  id         INTEGER IDENTITY PRIMARY KEY,
  product_name VARCHAR(30),
  price      VARCHAR(30),
  address    VARCHAR(255),
  category       VARCHAR(30),
  telephone  VARCHAR(20)
);
CREATE INDEX products_price ON products (product_name);
