
CREATE TABLE pokerman.equipment_types (
    id BIGSERIAL PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE pokerman.equipments (
    id BIGSERIAL PRIMARY KEY,
    equipment_name VARCHAR(100) NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_equipment_type FOREIGN KEY (equipment_type_id) REFERENCES pokerman.equipment_types(id)
);


CREATE INDEX idx_equipments_name ON pokerman.equipments(equipment_name);
CREATE INDEX idx_equipments_equipment_type_id ON pokerman.equipments(equipment_type_id);
CREATE INDEX idx_equipment_types_name ON pokerman.equipment_types(type_name);