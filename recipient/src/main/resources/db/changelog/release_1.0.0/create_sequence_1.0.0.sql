--liquibase formatted sql

--changeset Ihor:1680422502
create sequence if not exists internal_user_seq increment by 1 start with 1;