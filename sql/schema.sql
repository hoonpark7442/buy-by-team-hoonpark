create table if not exists user
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

create table if not exists user_profile
(
    id       INT NOT NULL AUTO_INCREMENT,
    name     varchar(255),
    phone_no varchar(255),
    user_id  int,
    primary key (id)
);

create table if not exists product
(
    id             INT NOT NULL AUTO_INCREMENT,
    name           varchar(255),
    sku_code       varchar(255),
    img_url        varchar(255),
    price_sale     int,
    price_discount int,
    discount_rate  int,
    PRIMARY KEY (id),
    index index_product_on_sku_code (sku_code)
);

create table if not exists deal
(
    id                INT NOT NULL AUTO_INCREMENT,
    product_id        int,
    price_table_id    int,
    group_size        int,
    discount_price    int,
    status            varchar(255),
    participant_count int,
    is_private        boolean,
    expired_at        TIMESTAMP,
    closed_at         TIMESTAMP,
    version           int,
    PRIMARY KEY (id),
    index index_deal_on_status (status)
);

create table if not exists price_table
(
    id                        INT NOT NULL AUTO_INCREMENT,
    product_id                int,
    deal_capacity             int,
    discount_price            int,
    is_deal_private           boolean,
    deal_valid_period_in_days int,
    start_date                TIMESTAMP,
    end_date                  TIMESTAMP,
    created_date              TIMESTAMP,
    updated_date              TIMESTAMP,
    PRIMARY KEY (id)
);

create table if not exists payment
(
    id       INT NOT NULL AUTO_INCREMENT,
    user_id  int,
    order_id int,
    method   int,
    status   int,
    PRIMARY KEY (id)
);

create table if not exists orders
(
    id                INT NOT NULL AUTO_INCREMENT,
    deal_id           int,
    user_id           int,
    quantity          int,
    status            varchar(255),
    receiver_name     varchar(255),
    receiver_address  varchar(255),
    receiver_phone_no varchar(255),
    PRIMARY KEY (id)
);

create table if not exists participants
(
  id                INT NOT NULL AUTO_INCREMENT,
  deal_id           int,
  user_id           int,
  PRIMARY KEY (id)
)

