
CREATE TABLE pokerman.tournaments (
    id BIGSERIAL PRIMARY KEY,
    tournament_name VARCHAR(100) NOT NULL,
    description TEXT,
    tournament_type_id BIGINT NOT NULL,
    buy_in DECIMAL,
    free_to_play BOOLEAN NOT NULL,
    status_id BIGINT NOT NULL,
    scheduled_start_time TIMESTAMP,
    actual_start_time TIMESTAMP,
    end_time TIMESTAMP,
    current_level INT,
    remaining_level_time INT,
    created_by BIGINT NOT NULL,
    blind_structure_id BIGINT,
    starting_stack_id BIGINT,
    settings_id BIGINT,
    series_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tournament_type FOREIGN KEY (tournament_type_id) REFERENCES pokerman.tournament_types(id),
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES pokerman.statuses(id),
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users(id),
    CONSTRAINT fk_blind_structure FOREIGN KEY (blind_structure_id) REFERENCES pokerman.blind_structures(id),
    CONSTRAINT fk_starting_stack FOREIGN KEY (starting_stack_id) REFERENCES pokerman.starting_stacks(id),
    CONSTRAINT fk_settings FOREIGN KEY (settings_id) REFERENCES pokerman.tournament_settings(id),
    CONSTRAINT fk_series FOREIGN KEY (series_id) REFERENCES pokerman.series(id)
);

CREATE INDEX idx_tournaments_name ON pokerman.tournaments(tournament_name);
CREATE INDEX idx_tournaments_status_id ON pokerman.tournaments(status_id);
CREATE INDEX idx_tournaments_tournament_type_id ON pokerman.tournaments(tournament_type_id);
CREATE INDEX idx_tournaments_created_by ON pokerman.tournaments(created_by);