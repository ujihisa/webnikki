# --- !Ups

CREATE SEQUENCE css_custom_id_seq;
CREATE TYPE css_purpose AS ENUM ('list', 'page');
CREATE TABLE css_custom (
    id INTEGER NOT NULL DEFAULT nextval('css_custom_id_seq'),
    member_id INTEGER DEFAULT NULL,
    purpose css_purpose NOT NULL,
    content TEXT NOT NULL,
    modified_at BIGINT NOT NULL
);
CREATE INDEX member_id_purpose_idx ON css_custom (member_id, purpose);

# --- !Downs

DROP TABLE css_custom;
DROP TYPE css_purpose;
DROP SEQUENCE css_custom_id_seq;
