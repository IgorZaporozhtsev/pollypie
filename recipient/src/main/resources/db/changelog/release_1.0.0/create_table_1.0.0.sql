--liquibase formatted sql

--changeset Ihor:1680420597
create table addition
(
    add_id     uuid not null,
    item_price numeric(38, 2),
    name       varchar(255),
    fk_item_id uuid,
    primary key (add_id)
);

--changeset Ihor:1680420629
create table item
(
    item_id     uuid not null,
    amount      integer,
    item_price  numeric(38, 2),
    name        varchar(255),
    fk_order_id uuid,
    primary key (item_id)
);

--changeset Ihor:1680420638
create table client_order
(
    order_id     uuid not null,
    address      varchar(100),
    email        varchar(255),
    first_name   varchar(10),
    last_name    varchar(10),
    phone_number varchar(255),
    description  varchar(255),
    state        varchar(255),
    total_price  numeric(38, 2),
    primary key (order_id)
);
--changeset Ihor:1680420653
create table internal_user
(
    id        integer not null,
    email     varchar(255),
    last_name varchar(255),
    password  varchar(255),
    username  varchar(255),
    primary key (id)
);


--changeset Ihor:1680420665
create table token
(
    id         integer not null,
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
    user_id     integer not null,
    authorities bytea
);

