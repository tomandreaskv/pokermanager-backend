
CREATE TABLE pokerman.tournament_results (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    position INT NOT NULL,
    points INT NOT NULL,
    bounties_won INT,
    won_prize BOOLEAN,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id)
);

CREATE TABLE pokerman.prizes (
    id BIGSERIAL PRIMARY KEY,
    tournament_result_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount DECIMAL NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tournament_result FOREIGN KEY (tournament_result_id) REFERENCES pokerman.tournament_results(id)
);

CREATE TABLE pokerman.prize_distributions (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    position INT NOT NULL,
    percentage DECIMAL NOT NULL,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id)
);

CREATE INDEX idx_tournament_results_tournament_id ON pokerman.tournament_results(tournament_id);
CREATE INDEX idx_tournament_results_user_id ON pokerman.tournament_results(user_id);
CREATE INDEX idx_tournament_results_position ON pokerman.tournament_results(position);
CREATE INDEX idx_prizes_tournament_result_id ON pokerman.prizes(tournament_result_id);
CREATE INDEX idx_prize_distributions_tournament_id ON pokerman.prize_distributions(tournament_id);