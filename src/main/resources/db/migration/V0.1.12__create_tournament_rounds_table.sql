CREATE TABLE pokerman.tournament_rounds (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    level_id BIGINT NOT NULL,
    round_status_id BIGINT NOT NULL,
    scheduled_start_time TIMESTAMP,
    actual_start_time TIMESTAMP,
    paused_time TIMESTAMP,
    end_time TIMESTAMP,
    remaining_time INT,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_level FOREIGN KEY (level_id) REFERENCES pokerman.levels(id),
    CONSTRAINT fk_round_status FOREIGN KEY (round_status_id) REFERENCES pokerman.statuses(id)
);

CREATE INDEX idx_tournament_rounds_tournament_id ON pokerman.tournament_rounds(tournament_id);
CREATE INDEX idx_tournament_rounds_level_id ON pokerman.tournament_rounds(level_id);
CREATE INDEX idx_tournament_rounds_round_status_id ON pokerman.tournament_rounds(round_status_id);