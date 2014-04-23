# --- !Ups

CREATE SEQUENCE member_id_seq;
CREATE TABLE member (
    id INTEGER NOT NULL DEFAULT nextval('member_id_seq'),
    uname VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(511) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL
);

# --- !Downs
 
DROP TABLE member;
DROP SEQUENCE member_id_seq;
