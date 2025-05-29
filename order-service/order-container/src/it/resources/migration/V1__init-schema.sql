CREATE SCHEMA IF NOT EXISTS "order";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLING', 'CANCELLED');

DROP TABLE IF EXISTS "order".orders;
CREATE TABLE "order".orders
(
  id               uuid                                           NOT NULL,
  customer_ud      uuid                                           NOT NULL,
  store_id         uuid                                           NOT NULL,
  delivery_address character varying COLLATE pg_catalog."default" NOT NULL,
  total_price      numeric(10, 2)                                 NOT NULL,
  CONSTRAINT orders_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "order".order_items;
CREATE TABLE "order".order_items
(
  id              bigint                                         NOT NULL,
  order_id        uuid                                           NOT NULL,
  product_id      uuid                                           NOT NULL,
  image           character varying COLLATE pg_catalog."default" NOT NULL,
  name            character varying COLLATE pg_catalog."default" NOT NULL,
  description     character varying COLLATE pg_catalog."default" NOT NULL,
  quantity        integer                                        NOT NULL,
  sub_total_price numeric(10, 2)                                 NOT NULL,
  CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id)
);

DROP TABLE IF EXISTS "order".customers;
CREATE TABLE "order".customers
(
  id         uuid                                           NOT NULL,
  username   character varying COLLATE pg_catalog."default" NOT NULL,
  first_name character varying COLLATE pg_catalog."default" NOT NULL,
  last_name  character varying COLLATE pg_catalog."default" NOT NULL
)