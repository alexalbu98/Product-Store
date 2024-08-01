CREATE TABLE store (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    address_country VARCHAR(255),
    address_city VARCHAR(255),
    address_street VARCHAR(255),
    address_zip_code VARCHAR(255)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    version BIGINT,
    store_ref BIGINT,
    available_stock INT,
    units_sold BIGINT,
    name VARCHAR(255),
    description TEXT,
    price_unit INT,
    price_sub_unit INT,
    price_currency VARCHAR(255),
    FOREIGN KEY (store_ref) REFERENCES store(id)
);