# --- !Ups

CREATE SEQUENCE pass_remind_id_seq;
CREATE TABLE pass_remind (
    id INTEGER NOT NULL DEFAULT nextval('pass_remind_id_seq'),
    email VARCHAR(511) NOT NULL,
    token VARCHAR(255) NOT NULL,
    validity BOOLEAN NOT NULL DEFAULT TRUE,
    created_at BIGINT NOT NULL
);
CREATE INDEX token_idx ON pass_remind (token);

# --- !Downs

DROP TABLE pass_remind;
DROP SEQUENCE pass_remind_id_seq;
