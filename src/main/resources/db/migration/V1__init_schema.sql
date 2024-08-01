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
    store_id BIGINT,
    available_stock INT,
    units_sold BIGINT,
    name VARCHAR(255),
    description TEXT,
    price_unit INT,
    price_subunit INT,
    price_currency VARCHAR(255),
    FOREIGN KEY (store_id) REFERENCES store(id)
);