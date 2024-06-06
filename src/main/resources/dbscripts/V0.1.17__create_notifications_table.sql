
CREATE TABLE pokerman.notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL,
    sent_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id)
);

CREATE INDEX idx_notifications_user_id ON pokerman.notifications(user_id);
CREATE INDEX idx_notifications_is_read ON pokerman.notifications(is_read);