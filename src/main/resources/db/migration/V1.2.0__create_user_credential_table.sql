CREATE TABLE pokerman.user_credential (
  id BIGSERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  password VARCHAR(255) NOT NULL,
  is_temporal BOOLEAN DEFAULT FALSE,
  is_active BOOLEAN DEFAULT TRUE,
  valid_from_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES pokerman.users(id)
);

INSERT INTO pokerman.user_credential (user_id, password)
SELECT id, password FROM pokerman.users;

ALTER TABLE pokerman.users DROP COLUMN password;