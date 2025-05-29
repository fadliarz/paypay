DROP SCHEMA IF EXISTS store CASCADE;
CREATE SCHEMA store;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS store.stores CASCADE;
CREATE TABLE store.stores
(
  id uuid NOT NULL,
  CONSTRAINT stores_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS store.products CASCADE;
CREATE TABLE store.products
(
  id          uuid                                           NOT NULl,
  image       character varying COLLATE pg_catalog."default" NOT NULL,
  name        character varying COLLATE pg_catalog."default" NOT NULL,
  description character varying COLLATE pg_catalog."default" NOT NULL,
  price       numeric(10, 2)                                 NOT NULL,
  CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS store.store_products CASCADE;
CREATE TABLE store.store_products
(
  id         uuid NOT NULL,
  store_id   uuid NOT NULL,
  product_id uuid NOT NULL,
  CONSTRAINT store_products_pkey PRIMARY KEY (id)
);

ALTER TABLE store.store_products
  ADD CONSTRAINT "FK_STORE_ID" FOREIGN KEY (store_id)
    REFERENCES store.stores (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE store.store_products
  ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
    REFERENCES store.products (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

DROP MATERIALIZED VIEW IF EXISTS store.store_product_m_view;
CREATE MATERIALIZED VIEW store.store_product_m_view
  TABLESPACE pg_default
AS
SELECT s.id          AS store_id,
       p.id          AS product_id,
       p.image       AS product_image,
       p.name        AS product_name,
       p.description AS product_description,
       p.price       AS product_price
FROM store.stores s,
     store.products p,
     store.store_products sp
WHERE s.id = sp.store_id
  AND p.id = sp.product_id
WITH DATA;

REFRESH MATERIALIZED VIEW store.store_product_m_view;

DROP FUNCTION IF EXISTS store.refresh_store_product_m_view;
CREATE OR REPLACE FUNCTION store.refresh_store_product_m_view()
  returns trigger
AS
'
  BEGIN
    REFRESH MATERIALIZED VIEW store.store_product_m_view;
    return null;
  end;
' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS refresh_store_product_m_view ON store.store_products;
CREATE TRIGGER refresh_store_product_m_view
  AFTER INSERT OR UPDATE OR DELETE OR TRUNCATE
  ON store.store_products
  FOR EACH STATEMENT
EXECUTE PROCEDURE store.refresh_store_product_m_view();