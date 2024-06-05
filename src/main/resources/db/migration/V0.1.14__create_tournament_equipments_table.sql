CREATE TABLE pokerman.tournament_equipments (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    equipment_id BIGINT NOT NULL,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES pokerman.equipments(id)
);

CREATE INDEX idx_tournament_equipments_tournament_id ON pokerman.tournament_equipments(tournament_id);
CREATE INDEX idx_tournament_equipments_equipment_id ON pokerman.tournament_equipments(equipment_id);
