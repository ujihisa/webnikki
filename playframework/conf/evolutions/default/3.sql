# --- !Ups

CREATE SEQUENCE comment_id_seq;
CREATE TABLE comment (
    id INTEGER NOT NULL DEFAULT nextval('comment_id_seq'),
    post_id INTEGER NOT NULL,
    member_id INTEGER DEFAULT NULL,
    uname VARCHAR(255) DEFAULT NULL,
    content TEXT NOT NULL,
    is_public BOOLEAN,
    created_at BIGINT NOT NULL,
    modified_at BIGINT NOT NULL
);
CREATE INDEX post_id ON comment (post_id);
CREATE INDEX post_id_is_public_idx ON comment (post_id, is_public);

# --- !Downs

DROP TABLE comment;
DROP SEQUENCE comment_id_seq;
