CREATE TABLE pokerman.access_tokens (
    id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    refresh_token_id BIGINT,
    jti VARCHAR(512) UNIQUE NOT NULL,  -- Unik JWT ID
    expiration TIMESTAMP NOT NULL,  -- Utløpsdato for tokenet
    status VARCHAR(10) DEFAULT 'valid',  -- Status for tokenet
    created_at TIMESTAMP DEFAULT NOW(),  -- Når tokenet ble opprettet
    ip_address VARCHAR(45),  -- Hvis du vil lagre IPv4 eller IPv6-adresser
    device_info VARCHAR(512),  -- Informasjon om enheten
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES pokerman.users(id) ON DELETE CASCADE,
    ForEIGN KEY (refresh_token_id) REFERENCES pokerman.refresh_tokens(id)
);

CREATE INDEX idx_access_tokens_user_id ON pokerman.access_tokens(user_id);
