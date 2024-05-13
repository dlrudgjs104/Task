CREATE TABLE `users` (
	`user_id`	varchar(50)	NOT NULL,
	`user_name`	varchar(50)	NOT NULL,
	`user_password`	varchar(200)	NOT NULL,
	`user_birth`	varchar(8)	NOT NULL,
	`user_auth`	varchar(10)	NOT NULL,
	`user_point`	int	NOT NULL	DEFAULT 1000000,
	`created_at`	datetime	NOT NULL,
	`latest_login_at`	datetime	NULL	DEFAULT null
);

CREATE TABLE `product` (
	`product_id`	varchar(50)	NOT NULL,
	`product_name`	varchar(128)	NOT NULL,
	`product_price`	decimal	NOT NULL,
	`product_description`	varchar(128)	NULL,
	`product_rdate`	datetime	NOT NULL
);

CREATE TABLE `orders` (
	`order_id`	varchar(50)	NOT NULL,
	`user_id`	varchar(50)	NOT NULL,
	`order_date`	datetime	NOT NULL,
	`order_total_amount`	decimal	NOT NULL,
	`order_point_earned`	int	NOT NULL
);

CREATE TABLE `address` (
	`address_id`	varchar(50)	NOT NULL,
	`address_name`	varchar(128)	NOT NULL,
	`user_id`	varchar(50)	NOT NULL
);

CREATE TABLE `cart` (
	`cart_id`	varchar(50)	NOT NULL,
	`user_id`	varchar(50)	NOT NULL,
	`product_id`	varchar(50)	NOT NULL,
	`product_quantity`	int	NOT NULL,
	`order_check`	boolean	NOT NULL
);

CREATE TABLE `category` (
	`category_id`	varchar(50)	NOT NULL,
	`category_name`	varchar(128)	NOT NULL
);

CREATE TABLE `product_category_mapping` (
	`product_id`	varchar(50)	NOT NULL,
	`category_id`	varchar(50)	NOT NULL
);

CREATE TABLE `order_detail` (
	`order_detail_id`	varchar(50)	NOT NULL,
	`order_id`	varchar(50)	NOT NULL,
	`user_id`	varchar(50)	NOT NULL,
	`product_id`	varchar(50)	NOT NULL,
	`product_quantity`	int	NOT NULL
);

CREATE TABLE `point_transaction` (
	`point_transaction_id`	varchar(50)	NOT NULL,
	`order_id`	varchar(50)	NOT NULL,
	`user_id`	varchar(50)	NOT NULL,
	`point_transaction_type`	ENUM('적립', '사용')	NOT NULL,
	`point_transaction_amount`	int	NOT NULL,
	`point_transaction_date`	datetime	NOT NULL
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `product` ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
	`product_id`
);

ALTER TABLE `orders` ADD CONSTRAINT `PK_ORDERS` PRIMARY KEY (
	`order_id`,
	`user_id`
);

ALTER TABLE `address` ADD CONSTRAINT `PK_ADDRESS` PRIMARY KEY (
	`address_id`
);

ALTER TABLE `cart` ADD CONSTRAINT `PK_CART` PRIMARY KEY (
	`cart_id`,
	`user_id`,
	`product_id`
);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`category_id`
);

ALTER TABLE `product_category_mapping` ADD CONSTRAINT `PK_PRODUCT_CATEGORY_MAPPING` PRIMARY KEY (
	`product_id`,
	`category_id`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `PK_ORDER_DETAIL` PRIMARY KEY (
	`order_detail_id`,
	`order_id`,
	`user_id`,
	`product_id`
);

ALTER TABLE `point_transaction` ADD CONSTRAINT `PK_POINT_TRANSACTION` PRIMARY KEY (
	`point_transaction_id`,
	`order_id`,
	`user_id`
);

ALTER TABLE `orders` ADD CONSTRAINT `FK_users_TO_orders_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `cart` ADD CONSTRAINT `FK_users_TO_cart_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `cart` ADD CONSTRAINT `FK_product_TO_cart_1` FOREIGN KEY (
	`product_id`
)
REFERENCES `product` (
	`product_id`
);

ALTER TABLE `product_category_mapping` ADD CONSTRAINT `FK_product_TO_product_category_mapping_1` FOREIGN KEY (
	`product_id`
)
REFERENCES `product` (
	`product_id`
);

ALTER TABLE `product_category_mapping` ADD CONSTRAINT `FK_category_TO_product_category_mapping_1` FOREIGN KEY (
	`category_id`
)
REFERENCES `category` (
	`category_id`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_orders_TO_order_detail_1` FOREIGN KEY (
	`order_id`
)
REFERENCES `orders` (
	`order_id`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_orders_TO_order_detail_2` FOREIGN KEY (
	`user_id`
)
REFERENCES `orders` (
	`user_id`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_product_TO_order_detail_1` FOREIGN KEY (
	`product_id`
)
REFERENCES `product` (
	`product_id`
);

ALTER TABLE `point_transaction` ADD CONSTRAINT `FK_orders_TO_point_transaction_1` FOREIGN KEY (
	`order_id`
)
REFERENCES `orders` (
	`order_id`
);

ALTER TABLE `point_transaction` ADD CONSTRAINT `FK_orders_TO_point_transaction_2` FOREIGN KEY (
	`user_id`
)
REFERENCES `orders` (
	`user_id`
);

