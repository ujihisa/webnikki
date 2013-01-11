# --- !Ups

CREATE SEQUENCE member_id_seq;
CREATE TABLE member (
    id integer NOT NULL DEFAULT nextval('member_id_seq'),
    uname varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    password_salt varchar(255) NOT NULL,
    email varchar(511) NOT NULL
);

# --- !Downs
 
DROP TABLE member;
DROP SEQUENCE member_id_seq;
