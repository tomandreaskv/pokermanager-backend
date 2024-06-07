-- Opprett tabeller med indekser og utenlandske nøkler direkte
CREATE TABLE pokerman.users (
    id BIGSERIAL PRIMARY KEY,
    is_admin BOOLEAN,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    email VARCHAR(255),
    password VARCHAR(255),
    username VARCHAR(255)
);

CREATE INDEX idx_users_username ON pokerman.users (username);

CREATE TABLE pokerman.equipment_types (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE INDEX idx_equipment_types_type_name ON pokerman.equipment_types (type_name);

CREATE TABLE pokerman.statuses (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    status_name VARCHAR(255) UNIQUE
);

CREATE INDEX idx_statuses_status_name ON pokerman.statuses (status_name);

CREATE TABLE pokerman.blind_structures (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    blind_structures_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_blind_structures_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_blind_structures_name ON pokerman.blind_structures (blind_structures_name);

CREATE TABLE pokerman.starting_stacks (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    stack_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_starting_stacks_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_starting_stacks_stack_name ON pokerman.starting_stacks (stack_name);

CREATE TABLE pokerman.tournament_types (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    type_name VARCHAR(255) UNIQUE
);

CREATE INDEX idx_tournament_types_type_name ON pokerman.tournament_types (type_name);

CREATE TABLE pokerman.equipments (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    equipment_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_equipments_equipment_type_id FOREIGN KEY (equipment_type_id) REFERENCES pokerman.equipment_types (id)
);

CREATE INDEX idx_equipments_equipment_name ON pokerman.equipments (equipment_name);

CREATE TABLE pokerman.chip_sets (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    chip_set_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_chip_sets_equipment_id FOREIGN KEY (equipment_id) REFERENCES pokerman.equipments (id)
);

CREATE INDEX idx_chip_sets_chip_set_name ON pokerman.chip_sets (chip_set_name);

CREATE TABLE pokerman.chip_values (
    id BIGSERIAL PRIMARY KEY,
    chip_value INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    chip_set_id BIGINT NOT NULL,
    CONSTRAINT fk_chip_values_chip_set_id FOREIGN KEY (chip_set_id) REFERENCES pokerman.chip_sets (id)
);

CREATE INDEX idx_chip_values_chip_value ON pokerman.chip_values (chip_value);

CREATE TABLE pokerman.levels (
    id BIGSERIAL PRIMARY KEY,
    ante INTEGER,
    big_blind INTEGER NOT NULL,
    color_up BOOLEAN NOT NULL,
    duration INTEGER NOT NULL,
    level_order INTEGER NOT NULL,
    small_blind INTEGER NOT NULL,
    blind_structure_id BIGINT NOT NULL,
    level_type VARCHAR(31) NOT NULL,
    CONSTRAINT fk_levels_blind_structure_id FOREIGN KEY (blind_structure_id) REFERENCES pokerman.blind_structures (id)
);

CREATE INDEX idx_levels_big_blind ON pokerman.levels (big_blind);

CREATE TABLE pokerman.tournament_specification (
    id BIGSERIAL PRIMARY KEY,
    buy_in DECIMAL(53) NOT NULL,
    free_to_play BOOLEAN NOT NULL,
    guaranteed_prize_pool DECIMAL(53),
    late_registration_period INTEGER,
    max_participants INTEGER,
    max_rebuys INTEGER,
    blind_structure_id BIGINT,
    bounty BIGINT,
    rebuy_end_time TIMESTAMP(6),
    starting_stack_id BIGINT,
    tournament_type_id BIGINT NOT NULL,
    CONSTRAINT fk_tournament_specification_blind_structure_id FOREIGN KEY (blind_structure_id) REFERENCES pokerman.blind_structures (id),
    CONSTRAINT fk_tournament_specification_starting_stack_id FOREIGN KEY (starting_stack_id) REFERENCES pokerman.starting_stacks (id),
    CONSTRAINT fk_tournament_specification_tournament_type_id FOREIGN KEY (tournament_type_id) REFERENCES pokerman.tournament_types (id)
);

CREATE INDEX idx_tournament_specification_buy_in ON pokerman.tournament_specification (buy_in);

CREATE TABLE pokerman.prize_distributions (
    id BIGSERIAL PRIMARY KEY,
    percentage DECIMAL(53) NOT NULL,
    position INTEGER NOT NULL,
    specification_id BIGINT NOT NULL,
    CONSTRAINT fk_prize_distributions_specification_id FOREIGN KEY (specification_id) REFERENCES pokerman.tournament_specification (id)
);

CREATE INDEX idx_prize_distributions_position ON pokerman.prize_distributions (position);

CREATE TABLE pokerman.tournaments (
    id BIGSERIAL PRIMARY KEY,
    actual_start_time TIMESTAMP(6),
    created_at TIMESTAMP(6) NOT NULL,
    created_by BIGINT NOT NULL,
    current_round_id BIGINT UNIQUE,
    end_time TIMESTAMP(6),
    scheduled_start_time TIMESTAMP(6),
    specification_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    description VARCHAR(255),
    tournament_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_tournaments_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users (id),
    CONSTRAINT fk_tournaments_specification_id FOREIGN KEY (specification_id) REFERENCES pokerman.tournament_specification (id),
    CONSTRAINT fk_tournaments_status_id FOREIGN KEY (status_id) REFERENCES pokerman.statuses (id)
);

CREATE INDEX idx_tournaments_tournament_name ON pokerman.tournaments (tournament_name);

CREATE TABLE pokerman.series (
    id BIGSERIAL PRIMARY KEY,
    accumulated_prize_pool DECIMAL(53),
    prize_pool_percentage DECIMAL(53),
    created_at TIMESTAMP(6),
    created_by BIGINT,
    final_tournament_id BIGINT,
    updated_at TIMESTAMP(6),
    description VARCHAR(255),
    series_name VARCHAR(255) UNIQUE,
    CONSTRAINT fk_series_created_by FOREIGN KEY (created_by) REFERENCES pokerman.users (id),
    CONSTRAINT fk_series_final_tournament_id FOREIGN KEY (final_tournament_id) REFERENCES pokerman.tournaments (id)
);

CREATE INDEX idx_series_series_name ON pokerman.series (series_name);

CREATE TABLE pokerman.tournament_results (
    id BIGSERIAL PRIMARY KEY,
    bounties_won INTEGER,
    points INTEGER NOT NULL,
    position INTEGER NOT NULL,
    won_prize BOOLEAN,
    created_at TIMESTAMP(6) NOT NULL,
    tournament_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_tournament_results_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id),
    CONSTRAINT fk_tournament_results_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_tournament_results_position ON pokerman.tournament_results (position);

CREATE TABLE pokerman.prizes (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(53) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    tournament_result_id BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT fk_prizes_tournament_result_id FOREIGN KEY (tournament_result_id) REFERENCES pokerman.tournament_results (id)
);

CREATE INDEX idx_prizes_amount ON pokerman.prizes (amount);

CREATE TABLE pokerman.series_tournaments (
    id BIGSERIAL PRIMARY KEY,
    prize_pool_percentage DECIMAL(53),
    series_id BIGINT,
    tournament_id BIGINT,
    CONSTRAINT fk_series_tournaments_series_id FOREIGN KEY (series_id) REFERENCES pokerman.series (id),
    CONSTRAINT fk_series_tournaments_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id)
);

CREATE INDEX idx_series_tournaments_series_id ON pokerman.series_tournaments (series_id);

CREATE TABLE pokerman.tournament_rounds (
    id BIGSERIAL PRIMARY KEY,
    remaining_time INTEGER,
    actual_start_time TIMESTAMP(6),
    end_time TIMESTAMP(6),
    level_id BIGINT NOT NULL,
    paused_time TIMESTAMP(6),
    round_status_id BIGINT NOT NULL,
    scheduled_start_time TIMESTAMP(6),
    tournament_id BIGINT NOT NULL,
    CONSTRAINT fk_tournament_rounds_level_id FOREIGN KEY (level_id) REFERENCES pokerman.levels (id),
    CONSTRAINT fk_tournament_rounds_round_status_id FOREIGN KEY (round_status_id) REFERENCES pokerman.statuses (id),
    CONSTRAINT fk_tournament_rounds_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id)
);

CREATE INDEX idx_tournament_rounds_level_id ON pokerman.tournament_rounds (level_id);

CREATE TABLE pokerman.user_activity_logs (
    id BIGSERIAL PRIMARY KEY,
    activity_timestamp TIMESTAMP(6) NOT NULL,
    user_id BIGINT NOT NULL,
    activity_action VARCHAR(255) NOT NULL,
    details VARCHAR(255),
    CONSTRAINT fk_user_activity_logs_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_user_activity_logs_activity_timestamp ON pokerman.user_activity_logs (activity_timestamp);

CREATE TABLE admin_permissions (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    permission_name VARCHAR(255) NOT NULL
);

CREATE INDEX idx_admin_permissions_permission_name ON admin_permissions (permission_name);

CREATE TABLE admin_user_permissions (
    id BIGSERIAL PRIMARY KEY,
    admin_permission_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_admin_user_permissions_admin_permission_id FOREIGN KEY (admin_permission_id) REFERENCES admin_permissions (id),
    CONSTRAINT fk_admin_user_permissions_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_admin_user_permissions_user_id ON admin_user_permissions (user_id);

CREATE TABLE invitations (
    id BIGSERIAL PRIMARY KEY,
    from_user_id BIGINT NOT NULL,
    sent_at TIMESTAMP(6) NOT NULL,
    status_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    user_id BIGINT,
    email VARCHAR(255),
    CONSTRAINT fk_invitations_from_user_id FOREIGN KEY (from_user_id) REFERENCES pokerman.users (id),
    CONSTRAINT fk_invitations_status_id FOREIGN KEY (status_id) REFERENCES pokerman.statuses (id),
    CONSTRAINT fk_invitations_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id),
    CONSTRAINT fk_invitations_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_invitations_sent_at ON invitations (sent_at);

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    is_read BOOLEAN NOT NULL,
    sent_at TIMESTAMP(6) NOT NULL,
    user_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    CONSTRAINT fk_notifications_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_notifications_is_read ON notifications (is_read);

CREATE TABLE pokerman.poker_tables (
    id BIGSERIAL PRIMARY KEY,
    seats INTEGER NOT NULL,
    equipment_id BIGINT NOT NULL,
    CONSTRAINT fk_poker_tables_equipment_id FOREIGN KEY (equipment_id) REFERENCES pokerman.equipments (id)
);

CREATE INDEX idx_poker_tables_seats ON pokerman.poker_tables (seats);

CREATE TABLE pokerman.specification_equipments (
    equipment_id BIGINT NOT NULL,
    specification_id BIGINT NOT NULL,
    PRIMARY KEY (equipment_id, specification_id),
    CONSTRAINT fk_specification_equipments_equipment_id FOREIGN KEY (equipment_id) REFERENCES pokerman.equipments (id),
    CONSTRAINT fk_specification_equipments_specification_id FOREIGN KEY (specification_id) REFERENCES pokerman.tournament_specification (id)
);

CREATE INDEX idx_specification_equipments_equipment_id ON pokerman.specification_equipments (equipment_id);

CREATE TABLE pokerman.table_balance_logs (
    id BIGSERIAL PRIMARY KEY,
    seat_number INTEGER NOT NULL,
    from_table_id BIGINT NOT NULL,
    moved_at TIMESTAMP(6) NOT NULL,
    to_table_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_table_balance_logs_from_table_id FOREIGN KEY (from_table_id) REFERENCES pokerman.poker_tables (id),
    CONSTRAINT fk_table_balance_logs_to_table_id FOREIGN KEY (to_table_id) REFERENCES pokerman.poker_tables (id),
    CONSTRAINT fk_table_balance_logs_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id),
    CONSTRAINT fk_table_balance_logs_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_table_balance_logs_moved_at ON pokerman.table_balance_logs (moved_at);

CREATE TABLE pokerman.table_seating (
    id BIGSERIAL PRIMARY KEY,
    is_dealer BOOLEAN NOT NULL,
    seat_number INTEGER NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    table_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_table_seating_table_id FOREIGN KEY (table_id) REFERENCES pokerman.poker_tables (id),
    CONSTRAINT fk_table_seating_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id),
    CONSTRAINT fk_table_seating_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_table_seating_seat_number ON pokerman.table_seating (seat_number);

CREATE TABLE pokerman.user_permissions (
    id BIGSERIAL PRIMARY KEY,
    can_edit_players BOOLEAN,
    can_edit_prize_distribution BOOLEAN,
    can_start_pause_stop BOOLEAN,
    tournament_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_permissions_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id),
    CONSTRAINT fk_user_permissions_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_user_permissions_user_id ON pokerman.user_permissions (user_id);

CREATE TABLE pokerman.user_tournaments (
    tournament_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (tournament_id, user_id),
    CONSTRAINT fk_user_tournaments_tournament_id FOREIGN KEY (tournament_id) REFERENCES pokerman.tournaments (id),
    CONSTRAINT fk_user_tournaments_user_id FOREIGN KEY (user_id) REFERENCES pokerman.users (id)
);

CREATE INDEX idx_user_tournaments_user_id ON pokerman.user_tournaments (user_id);

-- Legg til fremmednøkkel for current_round_id til slutt
ALTER TABLE pokerman.tournaments
    ADD CONSTRAINT fk_tournaments_current_round_id FOREIGN KEY (current_round_id) REFERENCES pokerman.tournament_rounds (id);
