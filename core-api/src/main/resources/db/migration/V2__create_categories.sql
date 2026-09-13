CREATE TABLE categories (
    id      BIGINT       AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT       NULL,
    name    VARCHAR(100) NOT NULL,
    icon    VARCHAR(50),
    CONSTRAINT fk_categories_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
