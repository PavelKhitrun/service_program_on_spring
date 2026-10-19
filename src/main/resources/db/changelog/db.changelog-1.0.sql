--liquibase formatted sql

--changeset pkhitrun:1
create table user_list
(
    id     bigserial primary key,
    name   varchar(255) NOT NULL,
    role   varchar(255) NOT NULL,
    email  varchar(255) NOT NULL,
    phone  varchar(255) NOT NULL,
    login  varchar(255) NOT NULL,
    pass   varchar(255) DEFAULT '{noop}123',
    image  varchar(255)
);

create table part_list
(
    id       bigserial primary key,
    article  bigint       NOT NULL,
    name     varchar(255) NOT NULL,
    price    numeric      NOT NULL
);

create table equipment_list
(
    id    bigserial primary key,
    name  varchar(255) NOT NULL
);

create table node
(
    id            bigserial primary key,
    equipment_id  bigint       NOT NULL REFERENCES equipment_list,
    name          varchar(255) NOT NULL,
    part_id       bigint       NOT NULL REFERENCES part_list
);

create table order_list
(
    id          bigserial primary key,
    user_id     bigint       NOT NULL REFERENCES user_list,
    date_order  timestamp    NOT NULL,
    status      varchar(32)  NOT NULL
);

create table order_part_list
(
    id            bigserial primary key,
    order_id      bigint NOT NULL REFERENCES order_list,
    part_list_id  bigint NOT NULL REFERENCES part_list,
    quantity      int    NOT NULL
);

create table order_warranty
(
    id                  bigserial primary key,
    user_id             bigint       NOT NULL REFERENCES user_list,
    equipment_id        bigint       NOT NULL REFERENCES equipment_list,
    serial_number       bigint       NOT NULL,
    oil_level           varchar(32)  NOT NULL,
    oil_condition       varchar(32)  NOT NULL,
    reason              varchar(255) NOT NULL,
    description_defect  varchar(255) NOT NULL,
    link_photo          varchar(255) NOT NULL,
    order_id            bigint       NOT NULL REFERENCES order_list
);