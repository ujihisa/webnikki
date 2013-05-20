# --- !Ups

CREATE SEQUENCE custom_data_id_seq;
CREATE TYPE purpose AS ENUM ('list', 'page');
CREATE TYPE ctype AS ENUM ('css', 'js');
CREATE TABLE custom_data (
    id INTEGER NOT NULL DEFAULT nextval('custom_data_id_seq'),
    member_id INTEGER DEFAULT NULL,
    content_purpose purpose NOT NULL,
    content_type ctype NOT NULL,
    content TEXT NOT NULL,
    modified_at BIGINT NOT NULL,
    UNIQUE (member_id, content_purpose)
);
CREATE INDEX member_id_purpose_idx ON custom_data (member_id, content_purpose);

# --- !Downs

DROP TABLE custom_data;
DROP TYPE purpose;
DROP TYPE ctype;
DROP SEQUENCE custom_data_id_seq;
