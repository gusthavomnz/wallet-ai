CREATE TABLE transactions (
    id               BIGINT                        AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT                        NOT NULL,
    category_id      BIGINT                        NULL,
    description      VARCHAR(255)                  NOT NULL,
    amount           DECIMAL(10, 2)                NOT NULL,
    type             ENUM('INCOME', 'EXPENSE')      NOT NULL,
    transaction_date DATE                          NOT NULL,
    origin           ENUM('MANUAL', 'OCR', 'CHAT') NOT NULL,
    created_at       TIMESTAMP                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transactions_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_transactions_category
        FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);
