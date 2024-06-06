CREATE TABLE pokerman.series (
    id BIGSERIAL PRIMARY KEY,
    series_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by BIGINT NOT NULL,
    prize_pool_percentage DECIMAL,
    accumulated_prize_pool DECIMAL,
    final_tournament_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users(id)
);

CREATE INDEX idx_series_name ON pokerman.series(series_name);
CREATE INDEX idx_series_created_by ON pokerman.series(created_by);