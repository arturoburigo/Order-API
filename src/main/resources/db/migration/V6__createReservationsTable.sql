CREATE TABLE reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              product_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL,
                              quantity INT NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_reservation_product FOREIGN KEY (product_id) REFERENCES products (id),
                              CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users (id)
);
