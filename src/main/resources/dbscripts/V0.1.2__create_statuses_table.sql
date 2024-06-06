
CREATE TABLE pokerman.statuses (
    id BIGSERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE INDEX idx_statuses_name ON pokerman.statuses(status_name);