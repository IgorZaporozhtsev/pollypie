--liquibase formatted sql

--changeset Ihor:1680420393
--comment:add constraint jwt token table
alter table token
    add constraint jwt_token_unique unique (token);

--changeset Ihor:1680420398
--comment:add constraint user_authorities
alter table authorities
    add constraint user_authorities
        foreign key ("user_id")
            references internal_user;

--changeset Ihor:1680420458
alter table if exists addition
    add constraint addition_item
        foreign key (fk_item_id)
            references item;


--changeset Ihor:1680420552
alter table if exists item
    add constraint item_order
        foreign key (fk_order_id)
            references client_order (order_id);;

--changeset Ihor:1680420559
alter table if exists token
    add constraint FK3kmr32p10efolskk3k9oqwv8n
        foreign key (user_id)
            references internal_user;



