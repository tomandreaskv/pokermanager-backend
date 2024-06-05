
CREATE TABLE pokerman.blind_structures (
    id BIGSERIAL PRIMARY KEY,
    blind_structures_name VARCHAR(100) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users(id)
);

CREATE TABLE pokerman.levels (
    id BIGSERIAL PRIMARY KEY,
    blind_structure_id BIGINT NOT NULL,
    level_order INT NOT NULL,
    duration INT NOT NULL,
    level_type VARCHAR(31) NOT NULL,  -- Discriminator column
    small_blind INT,
    big_blind INT,
    ante INT,
    color_up BOOLEAN,
    CONSTRAINT fk_blind_structure FOREIGN KEY (blind_structure_id) REFERENCES pokerman.blind_structures(id)
);

CREATE INDEX idx_blind_structures_name ON pokerman.blind_structures(blind_structures_name);
CREATE INDEX idx_blind_structures_created_by ON pokerman.blind_structures(created_by);
CREATE INDEX idx_levels_blind_structure_id ON pokerman.levels(blind_structure_id);
CREATE INDEX idx_levels_type ON pokerman.levels(level_type);
