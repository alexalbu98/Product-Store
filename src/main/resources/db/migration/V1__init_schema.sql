CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255),
    user_role VARCHAR(255)
);

CREATE TABLE stores (
    id SERIAL PRIMARY KEY,
    version BIGINT,
    user_ref BIGINT,
    name VARCHAR(255),
    description TEXT,
    address_country VARCHAR(255),
    address_city VARCHAR(255),
    address_street VARCHAR(255),
    address_zip_code VARCHAR(255),
    FOREIGN KEY (user_ref) REFERENCES users(id)
);

CREATE TABLE products (
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
    FOREIGN KEY (store_ref) REFERENCES stores(id)
);