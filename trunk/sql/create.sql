DROP TABLE "SCORE_SYSTEM" CASCADE CONSTRAINTS;
DROP SEQUENCE score_system_sequence;

DROP TABLE "RESERVATION" CASCADE CONSTRAINTS;
DROP SEQUENCE reservation_sequence;

DROP TABLE "COMPLEX" CASCADE CONSTRAINTS;
DROP SEQUENCE complex_sequence;

DROP TABLE "FIELD" CASCADE CONSTRAINTS;
DROP SEQUENCE field_sequence;

DROP TABLE "TIMETABLE" CASCADE CONSTRAINTS;
DROP SEQUENCE timetable_sequence;

DROP TABLE "USER" CASCADE CONSTRAINTS;
DROP SEQUENCE user_sequence;

DROP TABLE "EXPIRATION_POLICY" CASCADE CONSTRAINTS;
DROP SEQUENCE expiration_policy_sequence;

CREATE  TABLE "SCORE_SYSTEM" (
  "score_system_id" INT NOT NULL ,
  "on_reserve" INT NOT NULL ,
  "on_half_payment" INT NOT NULL ,
  "on_full_payment" INT NOT NULL ,
  "drop_reserved" INT NOT NULL ,
  "drop_half_paid" INT NOT NULL ,
  PRIMARY KEY ("score_system_id") );

CREATE SEQUENCE score_system_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER score_system_trigger
BEFORE INSERT
ON "SCORE_SYSTEM"
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT score_system_sequence.nextval INTO :NEW."score_system_id" FROM dual;
END;
/

CREATE  TABLE "COMPLEX" (
  "complex_id" INT NOT NULL ,
  "score_system_id" INT NOT NULL ,
  "name" VARCHAR2(50) NOT NULL ,
  "description" VARCHAR2(100) NULL ,
  "address" VARCHAR2(50) NOT NULL ,
  "city" VARCHAR2(50) NOT NULL ,
  "state" VARCHAR2(50) NOT NULL ,
  "country" VARCHAR2(50) NOT NULL ,
  "phone" VARCHAR2(50) NULL ,
  "fax" VARCHAR2(50) NULL ,
  "email" VARCHAR2(50) NULL ,
  "picture" BLOB NULL ,
  "latitude" VARCHAR2(50) NULL ,
  "longitude" VARCHAR2(50) NULL ,
  PRIMARY KEY ("complex_id") ,
  CONSTRAINT "fk_COMPLEX_POINTSYSTEM"
    FOREIGN KEY ("score_system_id" )
    REFERENCES "SCORE_SYSTEM" ("score_system_id" ) );

CREATE INDEX "fk_COMPLEX_POINTSYSTEM" ON "COMPLEX" ("score_system_id" ASC);

CREATE SEQUENCE complex_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER complex_trigger
BEFORE INSERT
ON "COMPLEX"
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT complex_sequence.nextval INTO :NEW."complex_id" FROM dual;
END;
/

CREATE  TABLE "FIELD" (
  "field_id" INT NOT NULL  ,
  "complex_id" INT NOT NULL ,
  "name" VARCHAR2(50) NOT NULL ,
  "description" VARCHAR2(100) NULL ,
  "number_of_players" INT NOT NULL ,
  "has_roof" NUMBER(1) ,
  "type" VARCHAR2(50) NULL ,
  "price" FLOAT NOT NULL ,
  "under_maintenance" NUMBER(1) NOT NULL ,
  "picture" BLOB NULL ,
  PRIMARY KEY ("field_id") ,
  CONSTRAINT "fk_FIELD_COMPLEX"
    FOREIGN KEY ("complex_id" )
    REFERENCES "COMPLEX" ("complex_id" ) );

CREATE INDEX "fk_FIELD_COMPLEX" ON "FIELD" ("complex_id" ASC);

CREATE SEQUENCE field_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER field_trigger
BEFORE INSERT
ON "FIELD" 
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT field_sequence.nextval INTO :NEW."field_id" FROM dual;
END;
/

CREATE  TABLE "TIMETABLE" (
  "timetable_id" INT NOT NULL ,
  "complex_id" INT NOT NULL ,
  "day" INT NOT NULL ,
  "from" INT NOT NULL ,
  "to" INT NOT NULL ,
  PRIMARY KEY ("timetable_id") ,
  CONSTRAINT "fk_TIMETABLE_COMPLEX"
    FOREIGN KEY ("complex_id" )
    REFERENCES "COMPLEX" ("complex_id" ) );

CREATE INDEX "fk_TIMETABLE_COMPLEX" ON "TIMETABLE" ("complex_id" ASC);

CREATE SEQUENCE timetable_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER timetable_trigger
BEFORE INSERT
ON "TIMETABLE"
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT timetable_sequence.nextval INTO :NEW."timetable_id" FROM dual;
END;
/

CREATE  TABLE "EXPIRATION_POLICY" (
  "expiration_policy_id" INT NOT NULL ,
  "field_id" INT NOT NULL ,
  "from_score" INT NOT NULL ,
  "to_score" INT NOT NULL ,
  "days_being_half_signed" INT NOT NULL ,
  "days_being_reserved" INT NOT NULL ,
  PRIMARY KEY ("expiration_policy_id") ,
  CONSTRAINT "fk_EXPIRATION_POLICY_FIELD"
    FOREIGN KEY ("field_id" )
    REFERENCES "FIELD" ("field_id" ) );

CREATE INDEX "fk_EXPIRATION_POLICY_FIELD" ON "EXPIRATION_POLICY" ("field_id" ASC);

CREATE SEQUENCE expiration_policy_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER expiration_policy_trigger
BEFORE INSERT
ON "EXPIRATION_POLICY"
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT expiration_policy_sequence.nextval INTO :NEW."expiration_policy_id" FROM dual;
END;
/

CREATE  TABLE "USER" (
  "user_id" INT NOT NULL ,
  "name" VARCHAR2(50) NOT NULL ,
  "password" VARCHAR2(50) NOT NULL ,
  "email" VARCHAR2(50) NOT NULL ,
  "score" INT NOT NULL ,
  "notify_before_expiration" INT NOT NULL ,
  "is_admin" NUMBER(1) NOT NULL ,
  PRIMARY KEY ("user_id") );

CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER user_trigger
BEFORE INSERT
ON "USER" 
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT user_sequence.nextval INTO :NEW."user_id" FROM dual;
END;
/

CREATE  TABLE "RESERVATION" (
  "reservation_id" INT NOT NULL ,
  "user_id" INT NOT NULL ,
  "field_id" INT NOT NULL ,
  "state" INT NOT NULL ,
  "start_date" TIMESTAMP NOT NULL ,
  "end_date" TIMESTAMP NOT NULL ,
  PRIMARY KEY ("reservation_id") ,
  CONSTRAINT "fk_has_COMPLEX_has_FIELD_USER"
    FOREIGN KEY ("user_id" )
    REFERENCES "USER" ("user_id" ) ,
  CONSTRAINT "fk_RESERVATION_FIELD"
    FOREIGN KEY ("field_id" )
    REFERENCES "FIELD" ("field_id" ) );

CREATE INDEX "fk_has_COMPLEX_has_FIELD_USER" ON "RESERVATION" ("user_id" ASC);

CREATE INDEX "fk_RESERVATION_FIELD" ON "RESERVATION" ("field_id" ASC);

CREATE SEQUENCE reservation_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER reservation_trigger
BEFORE INSERT
ON "RESERVATION" 
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT reservation_sequence.nextval INTO :NEW."reservation_id" FROM dual;
END;
/
