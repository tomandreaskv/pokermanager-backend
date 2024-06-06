-- V1__Create_users_table.sql
CREATE TABLE pokerman.starting_stacks (
    id BIGSERIAL PRIMARY KEY,
    stack_name VARCHAR(100) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users(id)
);

CREATE TABLE starting_stack_chips (
    id BIGSERIAL PRIMARY KEY,
    starting_stack_id BIGINT NOT NULL,
    chip_value_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_starting_stack FOREIGN KEY (starting_stack_id) REFERENCES pokerman.starting_stacks(id),
    CONSTRAINT fk_chip_value FOREIGN KEY (chip_value_id) REFERENCES pokerman.chip_values(id)
);

CREATE INDEX idx_starting_stacks_name ON pokerman.starting_stacks(stack_name);
CREATE INDEX idx_starting_stacks_created_by ON pokerman.starting_stacks(created_by);
CREATE INDEX idx_starting_stack_chips_starting_stack_id ON pokerman.starting_stack_chips(starting_stack_id);
CREATE INDEX idx_starting_stack_chips_chip_value_id ON pokerman.starting_stack_chips(chip_value_id);
