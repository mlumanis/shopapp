create table order_basket (
  id BIGINT not null,
  uuid varchar(36) not null,
  email varchar(36) not null,
  order_timestamp timestamp not null,
  primary key (id));

create table price (
  id BIGINT not null,
  uuid varchar(36) not null,
  created_on timestamp not null,
  price_value decimal(19,2) not null,
  fk_product BIGINT not null,
  primary key (id));

create table product (
  id BIGINT not null,
  uuid varchar(36) not null,
  product_name varchar(36) not null,
  primary key (id));

create table single_order (
  id BIGINT not null,
  uuid varchar(36) not null,
  fk_basket BIGINT not null,
  fk_price BIGINT,
  fk_product BIGINT not null,
  primary key (id));

alter table price add constraint PRODUCT_FOREIGN_KEY_CONSTRAINT foreign key (fk_product) references product;
alter table single_order add constraint BASKET_FOREIGN_KEY_CONSTRAINT foreign key (fk_basket) references order_basket;
alter table single_order add constraint PRICE_FOREIGN_KEY_CONSTRAINT foreign key (fk_price) references price;
alter table single_order add constraint ORDER_PRODUCT_FOREIGN_KEY_CONSTRAINT foreign key (fk_product) references product;

CREATE index index_product_price_basket_fk on single_order (fk_basket, fk_price, fk_product);
CREATE index index_product_uuid on product (uuid);
CREATE index index_single_order_uuid on single_order (uuid);
CREATE index index_order_basket_uuid on order_basket (uuid);

create sequence product_seq start with 1 increment by 1;
create sequence price_seq start with 1 increment by 1;
create sequence single_order_seq start with 1 increment by 1;
create sequence order_basket_seq start with 1 increment by 1;