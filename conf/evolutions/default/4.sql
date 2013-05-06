# --- !Ups

CREATE SEQUENCE pass_remind_id_seq;
CREATE TABLE pass_remind (
);

# --- !Downs

DROP TABLE pass_remind;
DROP SEQUENCE pass_remind_id_seq;
