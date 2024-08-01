CREATE TABLE store (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    address_country VARCHAR(255),
    address_city VARCHAR(255),
    address_street VARCHAR(255),
    address_zipcode VARCHAR(255)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    version BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    available_stock INT,
    units_sold BIGINT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price_unit INT NOT NULL,
    price_subunit INT NOT NULL,
    price_currency VARCHAR(255) NOT NULL,
    FOREIGN KEY (store_id) REFERENCES store(id)
);

ALTER TABLE store
ADD CONSTRAINT unique_store_name UNIQUE (name);

ALTER TABLE product
ADD CONSTRAINT unique_product_name UNIQUE (name);