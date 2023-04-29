--liquibase formatted sql

--changeset Ihor:1682007929
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset Ihor:1680873560
create table category
(
    id            uuid,
    category_name varchar(255),
    primary key (id)
);

--changeset Ihor:1680873547
create table product
(
    id             uuid,
    price          DECIMAL(10, 2),
    sku            VARCHAR(50),
    product_name   VARCHAR(255),
    category_id_fk uuid
        constraint products_to_category references category (id),
    primary key (id)

);

--changeset Ihor:1680873557
create table client_order
(
    id           uuid,
    title        varchar(255),
    total_price  DECIMAL(10, 2),
    status       varchar(30),
    address      varchar(100),
    email        varchar(255),
    first_name   varchar(10),
    last_name    varchar(10),
    phone_number varchar(255),
    primary key (id),
    order_date   timestamp not null default current_date
);

--changeset Ihor:1682410420
CREATE TABLE order_definitions
(
    id        uuid references client_order (id),
    item_name VARCHAR,
    quantity  INTEGER,
    PRIMARY KEY (id, item_name)
);


--changeset Ihor:1680873553
create table item
(
    id          uuid primary key unique
        constraint item_to_product references product (id),
    quantity    integer,
    order_id_fk uuid
        constraint items_to_order references client_order (id)
);

--changeset Ihor:1680420653
create table internal_user
(
    id        uuid,
    email     varchar(255),
    last_name varchar(255),
    password  varchar(255),
    username  varchar(255),
    primary key (id)
);

--changeset Ihor:1680420665
create table token
(
    id         uuid,
    expired    boolean not null,
    revoked    boolean not null,
    token      varchar(255),
    token_type varchar(255),
    user_id    integer,
    primary key (id)
);

--changeset Ihor:1680420684
create table authorities
(
    user_id     uuid,
    authorities bytea
);

--
-- alter table authorities
--     add constraint user_authorities
--         foreign key ("user_id")
--             references internal_user;

-- alter table if exists token
--     add constraint FK3kmr32p10efolskk3k9oqwv8n
--         foreign key (user_id)
--             references internal_user;