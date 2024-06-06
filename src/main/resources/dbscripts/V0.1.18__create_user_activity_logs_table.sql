
CREATE TABLE pokerman.user_activity_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    activity_action VARCHAR(255) NOT NULL,
    activity_timestamp TIMESTAMP NOT NULL,
    details TEXT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id)
);

CREATE TABLE pokerman.user_permissions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    can_start_pause_stop BOOLEAN,
    can_edit_prize_distribution BOOLEAN,
    can_edit_settings BOOLEAN,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id),
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id)
);

CREATE TABLE pokerman.admin_permissions (
    id BIGSERIAL PRIMARY KEY,
    permission_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE pokerman.admin_user_permissions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    admin_permission_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id),
    CONSTRAINT fk_admin_permission FOREIGN KEY (admin_permission_id) REFERENCES pokerman.admin_permissions(id)
);

CREATE INDEX idx_user_activity_logs_user_id ON pokerman.user_activity_logs(user_id);
CREATE INDEX idx_user_permissions_user_id ON pokerman.user_permissions(user_id);
CREATE INDEX idx_user_permissions_tournament_id ON pokerman.user_permissions(tournament_id);
CREATE INDEX idx_admin_user_permissions_user_id ON pokerman.admin_user_permissions(user_id);
CREATE INDEX idx_admin_user_permissions_admin_permission_id ON pokerman.admin_user_permissions(admin_permission_id);