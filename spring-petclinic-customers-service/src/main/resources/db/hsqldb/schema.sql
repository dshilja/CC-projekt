DROP TABLE products IF EXISTS;

CREATE TABLE products (
  id            INTEGER IDENTITY PRIMARY KEY,
  name          VARCHAR(30),
  type          VARCHAR(30),
  price         VARCHAR(30),
  description   VARCHAR(255),
  img_url       VARCHAR(80)
);
CREATE INDEX products_price ON products (name);
