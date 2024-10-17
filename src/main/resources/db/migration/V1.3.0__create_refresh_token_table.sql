CREATE TABLE pokerman.refresh_tokens (
    id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    token VARCHAR(512) NOT NULL,
    expiration TIMESTAMP NOT NULL,  -- Utløpsdato for tokenet
    created_at TIMESTAMP DEFAULT NOW(),  -- Når tokenet ble opprettet
    status VARCHAR(10) DEFAULT 'valid',  -- Status for tokenet
    ip_address VARCHAR(45),  -- Valgfritt: IP-adresse
    device_info VARCHAR(512),  -- Valgfritt: Enhetsinformasjon
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES pokerman.users(id)
);

CREATE INDEX idx_refresh_tokens_user_id ON pokerman.refresh_tokens(user_id);
