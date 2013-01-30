# --- !Ups

CREATE SEQUENCE post_id_seq;
CREATE TABLE post (
    id INTEGER NOT NULL DEFAULT nextval('post_id_seq'),
    member_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at INTEGER NOT NULL,
    modified_at INTEGER NOT NULL
);
CREATE INDEX member_id_idx ON post (member_id);
CREATE INDEX created_at_idx ON post (created_at);

# --- !Downs
 
DROP TABLE post;
DROP SEQUENCE post_id_seq;
