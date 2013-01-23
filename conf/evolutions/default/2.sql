# --- !Ups

CREATE SEQUENCE post_id_seq;
CREATE TABLE post (
    id INTEGER NOT NULL DEFAULT nextval('post_id_seq'),
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at INTEGER NOT NULL,
    modified_at INTEGER NOT NULL
);

# --- !Downs
 
DROP TABLE post;
DROP SEQUENCE post_id_seq;
