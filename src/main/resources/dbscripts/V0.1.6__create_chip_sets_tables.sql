
CREATE TABLE pokerman.chip_sets (
    id BIGSERIAL PRIMARY KEY,
    chip_set_name VARCHAR(255) NOT NULL,
    equipment_id BIGINT NOT NULL,
    CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES pokerman.equipments(id)
);

CREATE TABLE pokerman.chip_values (
    id BIGSERIAL PRIMARY KEY,
    chip_set_id BIGINT NOT NULL,
    chip_value INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_chip_set FOREIGN KEY (chip_set_id) REFERENCES pokerman.chip_sets(id)
);

create index idx_chip_sets_name on pokerman.chip_sets(chip_set_name);
CREATE INDEX idx_chip_sets_equipment_id ON pokerman.chip_sets(equipment_id);
CREATE INDEX idx_chip_values_chip_set_id ON pokerman.chip_values(chip_set_id);
CREATE INDEX idx_chip_values_value ON pokerman.chip_values(chip_value);
