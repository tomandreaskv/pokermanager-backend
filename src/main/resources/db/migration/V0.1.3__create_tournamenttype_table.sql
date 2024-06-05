
CREATE TABLE pokerman.tournament_types (
    id BIGSERIAL PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE INDEX idx_tournament_types_name ON pokerman.tournament_types(type_name);