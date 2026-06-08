CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL,
    price NUMERIC(38,2) NOT NULL,
    quantity INTEGER NOT NULL,
    image_url VARCHAR(255) NOT NULL
);