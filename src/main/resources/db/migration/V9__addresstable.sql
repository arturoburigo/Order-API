CREATE TABLE address
(
    user_id      BIGINT       NOT NULL,
    street       VARCHAR(255) NULL,
    number       VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255) NULL,
    city         VARCHAR(255) NULL,
    state        VARCHAR(255) NULL,
    zipcode      VARCHAR(255) NULL,
    complement   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (user_id)
);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);