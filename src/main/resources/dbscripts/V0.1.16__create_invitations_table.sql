
CREATE TABLE pokerman.invitations (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    user_id BIGINT,
    from_user_id BIGINT NOT NULL,
    email VARCHAR(100),
    status_id BIGINT NOT NULL,
    sent_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tournament FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pokerman.users(id),
    CONSTRAINT fk_from_user FOREIGN KEY (from_user_id) REFERENCES pokerman.users(id),
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES pokerman.statuses(id)
);

CREATE INDEX idx_invitations_tournament_id ON pokerman.invitations(tournament_id);
CREATE INDEX idx_invitations_user_id ON pokerman.invitations(user_id);
CREATE INDEX idx_invitations_from_user_id ON pokerman.invitations(from_user_id);
CREATE INDEX idx_invitations_email ON pokerman.invitations(email);
CREATE INDEX idx_invitations_status_id ON pokerman.invitations(status_id);