
CREATE TABLE pokerman.series_tournaments (
    id BIGSERIAL PRIMARY KEY,
    series_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_series FOREIGN KEY (series_id) REFERENCES pokerman.series(id),
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES pokerman.users(id)
);

CREATE INDEX idx_series_tournaments_series_id ON pokerman.series_tournaments(series_id);
CREATE INDEX idx_series_tournaments_tournament_id ON pokerman.series_tournaments(tournament_id);
CREATE INDEX idx_series_tournaments_owner_id ON pokerman.series_tournaments(owner_id);
