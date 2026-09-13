CREATE TABLE messages (
    id              BIGINT                    AUTO_INCREMENT PRIMARY KEY,
    conversation_id BIGINT                    NOT NULL,
    sender          ENUM('USER', 'ASSISTANT') NOT NULL,
    content         TEXT                      NOT NULL,
    created_at      TIMESTAMP                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_messages_conversation
        FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
);
