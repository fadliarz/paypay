-- store 1

INSERT INTO store.stores(id)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a28');

-- store 2

INSERT INTO store.stores(id)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a29');

-- store 1 > products

INSERT INTO store.products(id, image, name, description, price)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a30', 'image_1',
        'name_1', 'description_1', 10.00);

INSERT INTO store.products(id, image, name, description, price)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a31', 'image_2',
        'name_2', 'description_2', 10.00);

INSERT INTO store.products(id, image, name, description, price)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a32', 'image_3',
        'name_3', 'description_3', 10.00);

INSERT INTO store.store_products(id, store_id, product_id)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a33', '9ee53be2-1474-4b22-ad8b-ed53746c8a28',
        '9ee53be2-1474-4b22-ad8b-ed53746c8a30');

INSERT INTO store.store_products(id, store_id, product_id)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a34', '9ee53be2-1474-4b22-ad8b-ed53746c8a28',
        '9ee53be2-1474-4b22-ad8b-ed53746c8a31');

INSERT INTO store.store_products(id, store_id, product_id)
VALUES ('9ee53be2-1474-4b22-ad8b-ed53746c8a35', '9ee53be2-1474-4b22-ad8b-ed53746c8a28',
        '9ee53be2-1474-4b22-ad8b-ed53746c8a32');
