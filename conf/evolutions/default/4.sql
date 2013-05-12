# --- !Ups

CREATE SEQUENCE pass_reset_id_seq;
CREATE TABLE pass_reset (
    id INTEGER NOT NULL DEFAULT nextval('pass_reset_id_seq'),
    email VARCHAR(511) NOT NULL,
    token VARCHAR(255) NOT NULL,
    validity BOOLEAN NOT NULL DEFAULT TRUE,
    created_at BIGINT NOT NULL
);
CREATE INDEX token_idx ON pass_reset (token);

# --- !Downs

DROP TABLE pass_reset;
DROP SEQUENCE pass_reset_id_seq;
