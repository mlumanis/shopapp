create table order_basket (
  id varchar(255) not null,
  email varchar(255) not null,
  order_timestamp timestamp not null,
  primary key (id));

create table price (
  id varchar(255) not null,
  created_on timestamp not null,
  price_value decimal(19,2) not null,
  fk_product varchar(255) not null,
  primary key (id));

create table product (
  id varchar(255) not null,
  product_name varchar(255) not null,
  primary key (id));

create table single_order (
  id varchar(255) not null,
  fk_basket varchar(255) not null,
  fk_price varchar(255),
  fk_product varchar(255) not null,
  primary key (id));

alter table price add constraint PRODUCT_FOREIGN_KEY_CONSTRAINT foreign key (fk_product) references product;
alter table single_order add constraint BASKET_FOREIGN_KEY_CONSTRAINT foreign key (fk_basket) references order_basket;
alter table single_order add constraint PRICE_FOREIGN_KEY_CONSTRAINT foreign key (fk_price) references price;
alter table single_order add constraint ORDER_PRODUCT_FOREIGN_KEY_CONSTRAINT foreign key (fk_product) references product;

CREATE index index_product_price_basket_fk on single_order (fk_basket, fk_price, fk_product);