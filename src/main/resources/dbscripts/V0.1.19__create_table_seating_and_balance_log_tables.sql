
CREATE TABLE pokerman.table_seating (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    table_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    seat_number INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_table FOREIGN KEY (table_id) REFERENCES pokerman.poker_tables(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id)
);

CREATE TABLE pokerman.table_balance_logs (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    from_table_id BIGINT NOT NULL,
    to_table_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    seat_number INT NOT NULL,
    moved_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_from_table FOREIGN KEY (from_table_id) REFERENCES pokerman.poker_tables(id),
    CONSTRAINT fk_to_table FOREIGN KEY (to_table_id) REFERENCES pokerman.poker_tables(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id)
);

CREATE INDEX idx_table_seating_tournament_id ON pokerman.table_seating(tournament_id);
CREATE INDEX idx_table_seating_table_id ON pokerman.table_seating(table_id);
CREATE INDEX idx_table_seating_user_id ON pokerman.table_seating(user_id);
CREATE INDEX idx_table_balance_logs_tournament_id ON pokerman.table_balance_logs(tournament_id);
CREATE INDEX idx_table_balance_logs_from_table_id ON pokerman.table_balance_logs(from_table_id);
CREATE INDEX idx_table_balance_logs_to_table_id ON pokerman.table_balance_logs(to_table_id);
CREATE INDEX idx_table_balance_logs_user_id ON pokerman.table_balance_logs(user_id);