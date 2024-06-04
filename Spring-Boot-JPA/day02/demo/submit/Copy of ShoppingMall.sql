CREATE TABLE `users` (
	`user_id`	varchar(50)	NOT NULL	COMMENT '회원_아이디',
	`user_name`	varchar(50)	NOT NULL	COMMENT '회원_이름',
	`user_password`	varchar(200)	NOT NULL	COMMENT '회원_패스워드',
	`user_birth`	varchar(8)	NOT NULL	COMMENT '회원_생년월일',
	`user_auth`	varchar(10)	NOT NULL	COMMENT '회원_권한',
	`user_point`	int	NOT NULL	DEFAULT 1000000	COMMENT '회원_포인트',
	`created_at`	datetime	NOT NULL	COMMENT '회원_가입일자',
	`latest_login_at`	datetime	NULL	DEFAULT null	COMMENT '마지막 로그인 일자'
);

CREATE TABLE `product` (
	`product_id`	varchar(50)	NOT NULL	COMMENT '상품_아이디',
	`product_name`	varchar(128)	NOT NULL	COMMENT '상품_아이디',
	`product_price`	decimal	NOT NULL	COMMENT '상품_가격',
	`product_description`	varchar(128)	NULL	COMMENT '상품_설명',
	`product_rdate`	datetime	NOT NULL	COMMENT '상품_등록일자',
	`product_image_path`	varchar(128)	NOT NULL	COMMENT '상품_이미지_경로'
);

CREATE TABLE `orders` (
	`order_id`	varchar(50)	NOT NULL	COMMENT '주문_아이디',
	`user_id`	varchar(50)	NOT NULL	COMMENT '회원_아이디',
	`order_date`	datetime	NOT NULL	COMMENT '주문_날짜',
	`order_total_amount`	decimal	NOT NULL	COMMENT '주문_총액',
	`order_point_earned`	int	NOT NULL	COMMENT '주문_적립_포인트'
);

CREATE TABLE `address` (
	`address_id`	varchar(50)	NOT NULL	COMMENT '주소_아이디',
	`address_name`	varchar(128)	NOT NULL	COMMENT '주소_이름'
);

CREATE TABLE `cart` (
	`cart_id`	varchar(50)	NOT NULL	COMMENT '장바구니_아이디',
	`user_id`	varchar(50)	NOT NULL	COMMENT '회원_아이디',
	`product_id`	varchar(50)	NOT NULL	COMMENT '상품_아이디',
	`product_quantity`	int	NOT NULL	COMMENT '상품_수량',
	`order_check`	boolean	NOT NULL	COMMENT '주문_여부'
);

CREATE TABLE `category` (
	`category_id`	varchar(50)	NOT NULL	COMMENT '카테고리_아이디',
	`category_name`	varchar(128)	NOT NULL	COMMENT '카테고리_이름'
);

CREATE TABLE `product_category_mapping` (
	`product_id`	varchar(50)	NOT NULL	COMMENT '상품_아이디',
	`category_id`	varchar(50)	NOT NULL	COMMENT '카테고리_아이디'
);

CREATE TABLE `order_detail` (
	`order_detail_id`	varchar(50)	NOT NULL	COMMENT '주문_상세_아이디',
	`order_id`	varchar(50)	NOT NULL	COMMENT '주문_아이디',
	`user_id`	varchar(50)	NOT NULL	COMMENT '회원_아이디',
	`product_id`	varchar(50)	NOT NULL	COMMENT '상품_아이디',
	`product_quantity`	int	NOT NULL	COMMENT '상품_수량'
);

CREATE TABLE `point_transaction` (
	`point_transaction_id`	varchar(50)	NOT NULL	COMMENT '포인트_거래_아이디',
	`order_id`	varchar(50)	NOT NULL	COMMENT '주문_아이디',
	`user_id`	varchar(50)	NOT NULL	COMMENT '회원_아이디',
	`point_transaction_type`	ENUM('적립', '사용')	NOT NULL	COMMENT '포인트_거래_유형',
	`point_transaction_amount`	int	NOT NULL	COMMENT '포인트 사용액',
	`point_transaction_date`	datetime	NOT NULL	COMMENT '포인트_사용_날짜'
);

CREATE TABLE `address_user_mapping` (
	`user_id`	varchar(50)	NOT NULL	COMMENT '회원_아이디',
	`address_id`	varchar(50)	NOT NULL	COMMENT '주소_아이디'
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

ALTER TABLE `address_user_mapping` ADD CONSTRAINT `PK_ADDRESS_USER_MAPPING` PRIMARY KEY (
	`user_id`,
	`address_id`
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

ALTER TABLE `address_user_mapping` ADD CONSTRAINT `FK_users_TO_address_user_mapping_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `address_user_mapping` ADD CONSTRAINT `FK_address_TO_address_user_mapping_1` FOREIGN KEY (
	`address_id`
)
REFERENCES `address` (
	`address_id`
);

