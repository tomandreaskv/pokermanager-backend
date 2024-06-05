
CREATE TABLE pokerman.poker_tables (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    seats INT NOT NULL,
    CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES pokerman.equipments(id)
);

CREATE INDEX idx_poker_tables_equipment_id ON pokerman.poker_tables(equipment_id);