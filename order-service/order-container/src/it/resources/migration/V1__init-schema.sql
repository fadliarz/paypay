CREATE SCHEMA IF NOT EXISTS "order";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLING', 'CANCELLED');

DROP TABLE IF EXISTS "order".orders;
CREATE TABLE "order".orders
(
  id               uuid                                           NOT NULL,
  customer_id      uuid                                           NOT NULL,
  store_id         uuid                                           NOT NULL,
  delivery_address character varying COLLATE pg_catalog."default" NOT NULL,
  total_price      numeric(10, 2)                                 NOT NULL,
  order_status     order_status                                   NOT NULL,
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
  price           numeric(10, 2)                                 NOT NULL,
  quantity        integer                                        NOT NULL,
  sub_total_price numeric(10, 2)                                 NOT NULL,
  CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_items
  ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
    REFERENCES "order".orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS "order".customers;
CREATE TABLE "order".customers
(
  id         uuid                                           NOT NULL,
  username   character varying COLLATE pg_catalog."default" NOT NULL,
  first_name character varying COLLATE pg_catalog."default" NOT NULL,
  last_name  character varying COLLATE pg_catalog."default" NOT NULL
);

DROP TYPE IF EXISTS saga_status;
CREATE TYPE saga_status AS ENUM ('STARTED', 'FAILED', 'SUCCEEDED', 'PROCESSING', 'COMPENSATING', 'COMPENSATED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_stats AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TABLE IF EXISTS "order".payment_outbox CASCADE;
CREATE TABLE "order".payment_outbox
(
  id            uuid                                           NOT NULl,
  saga_id       uuid                                           NOT NULL,
  created_at    TIMESTAMP WITH TIME ZONE                       NOT NULL,
  processed_at  TIMESTAMP WITH TIME ZONE,
  type          character varying COLLATE pg_catalog."default" NOT NULL,
  payload       jsonb                                          NOT NULL,
  order_status  order_status                                   NOT NULL,
  saga_status   saga_status                                    NOT NULL,
  outbox_status outbox_stats                                   NOT NULL,
  version       integer                                        NOT NULL,
  CONSTRAINT payment_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "payment_outbox_saga_status" ON "order".payment_outbox (type, outbox_status, saga_status);