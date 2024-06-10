CREATE TABLE pokerman.user_detail (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    date_of_birth DATE,
    country VARCHAR(255),
    city VARCHAR(255),
    address TEXT,
    zip_code VARCHAR(255),
    profile_picture_url VARCHAR(255),
    bio TEXT
);

ALTER TABLE pokerman.users
ADD COLUMN user_detail_id BIGINT,
ADD CONSTRAINT fk_user_user_detail
FOREIGN KEY (user_detail_id)
REFERENCES pokerman.user_detail (id);