-- Hotels 테이블
CREATE TABLE `hotels` (
                          `hotel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                          `name` VARCHAR(100) NOT NULL,
                          PRIMARY KEY (`hotel_id`)
);

-- Rooms 테이블
CREATE TABLE `rooms` (
                         `room_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                         `hotel_id` BIGINT NOT NULL,
                         `name` VARCHAR(100) NOT NULL,
                         `capacity` TINYINT NOT NULL,
                         `floor` TINYINT NOT NULL,
                         `bathtub_flag` TINYINT NOT NULL,
                         `view_type` TINYINT NOT NULL,
                         `created_at` DATETIME NOT NULL,
                         PRIMARY KEY (`room_id`),
                         CONSTRAINT `FK_hotels_TO_rooms_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`hotel_id`)
);

-- Images 테이블
CREATE TABLE `images` (
                          `image_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                          `image_name` VARCHAR(255) NOT NULL,
                          `image_url` VARCHAR(255) NOT NULL,
                          `image_created_at` DATETIME NOT NULL,
                          PRIMARY KEY (`image_id`)
);

-- Users 테이블
CREATE TABLE `users` (
                         `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                         `name` VARCHAR(255) NOT NULL,
                         `username` VARCHAR(255) NOT NULL,
                         `password` VARCHAR(255) NOT NULL,
                         `user_point` BIGINT NOT NULL,
                         PRIMARY KEY (`user_id`)
);

-- Rooms_and_Images 테이블
CREATE TABLE `rooms_and_images` (
                                    `room_image_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                                    `room_id` BIGINT NOT NULL,
                                    `image_id` BIGINT NOT NULL,
                                    PRIMARY KEY (`room_image_id`),
                                    CONSTRAINT `FK_rooms_TO_rooms_and_images_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
                                    CONSTRAINT `FK_images_TO_rooms_and_images_1` FOREIGN KEY (`image_id`) REFERENCES `images` (`image_id`)
);

-- Reviews 테이블
CREATE TABLE `reviews` (
                           `review_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                           `user_id` BIGINT NOT NULL,
                           `room_id` BIGINT NOT NULL,
                           `review_title` VARCHAR(255) NOT NULL,
                           `review_content` VARCHAR(255) NOT NULL,
                           `review_created_at` DATETIME NOT NULL,
                           `review_check` BOOLEAN NOT NULL,
                           PRIMARY KEY (`review_id`),
                           CONSTRAINT `FK_users_TO_reviews_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                           CONSTRAINT `FK_rooms_TO_reviews_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
);

-- Reviews_and_Images 테이블
CREATE TABLE `reviews_and_images` (
                                      `review_image_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                                      `review_id` BIGINT NOT NULL,
                                      `image_id` BIGINT NOT NULL,
                                      PRIMARY KEY (`review_image_id`),
                                      CONSTRAINT `FK_reviews_TO_reviews_and_images_1` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`),
                                      CONSTRAINT `FK_images_TO_reviews_and_images_1` FOREIGN KEY (`image_id`) REFERENCES `images` (`image_id`)
);

-- Reservations 테이블
CREATE TABLE `reservations` (
                                `reservation_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                                `user_id` BIGINT NOT NULL,
                                `room_id` BIGINT NOT NULL,
                                `reservation_check_in_date` DATETIME NOT NULL,
                                `reservation_check_out_date` DATETIME NOT NULL,
                                `reservation_check` BOOLEAN NOT NULL,
                                PRIMARY KEY (`reservation_id`),
                                CONSTRAINT `FK_users_TO_reservations_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                                CONSTRAINT `FK_rooms_TO_reservations_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
);

-- Points 테이블
CREATE TABLE `points` (
                          `point_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
                          `user_id` BIGINT NOT NULL,
                          `reservation_id` BIGINT NULL,
                          `review_id` BIGINT NULL,
                          `point_amount` BIGINT NOT NULL,
                          `point_created_at` DATETIME NOT NULL,
                          PRIMARY KEY (`point_id`),
                          CONSTRAINT `FK_users_TO_points_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                          CONSTRAINT `FK_reservations_TO_points_1` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`),
                          CONSTRAINT `FK_reviews_TO_points_1` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`)
);