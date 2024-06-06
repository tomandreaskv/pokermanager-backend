
CREATE TABLE pokerman.tournament_settings (
    id BIGSERIAL PRIMARY KEY,
    bounty DECIMAL,
    max_rebuys INT,
    rebuy_end_time TIMESTAMP
);