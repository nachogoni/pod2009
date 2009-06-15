DROP TABLE "RESERVATION" CASCADE CONSTRAINTS;
DROP SEQUENCE reservation_sequence;

DROP TABLE "FIELD" CASCADE CONSTRAINTS;
DROP SEQUENCE field_sequence;

DROP TABLE "TIMETABLE" CASCADE CONSTRAINTS;
DROP SEQUENCE timetable_sequence;

DROP TABLE "EMAIL" CASCADE CONSTRAINTS;
DROP SEQUENCE email_sequence;

DROP TABLE "USERS" CASCADE CONSTRAINTS;
DROP SEQUENCE user_sequence;

DROP TABLE "EXPIRATION_POLICY" CASCADE CONSTRAINTS;
DROP SEQUENCE expiration_policy_sequence;

DROP TABLE "PHONE" CASCADE CONSTRAINTS;
DROP SEQUENCE phone_sequence;

DROP TABLE "COMPLEX" CASCADE CONSTRAINTS;
DROP SEQUENCE complex_sequence;

DROP TABLE "SCORE_SYSTEM" CASCADE CONSTRAINTS;
DROP SEQUENCE score_system_sequence;

DROP TABLE "REGISTER" CASCADE CONSTRAINTS;
DROP SEQUENCE register_sequence;


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
  "name" VARCHAR2(50) NOT NULL ,
  "description" VARCHAR2(100) NULL ,
  "address" VARCHAR2(50) NOT NULL ,
  "neighbourhood" VARCHAR2(50) NOT NULL ,
  "city" VARCHAR2(50) NOT NULL ,
  "state" VARCHAR2(50) NOT NULL ,
  "zip_code" VARCHAR2(50) NOT NULL ,
  "country" VARCHAR2(50) NOT NULL ,
  "fax" VARCHAR2(50) NULL ,
  "email" VARCHAR2(50) NULL ,
  "picture" BLOB NULL ,
  "latitude" VARCHAR2(50) NULL ,
  "longitude" VARCHAR2(50) NULL ,
  CONSTRAINT place_unique UNIQUE ("address", "city", "state", "country"),
  PRIMARY KEY ("complex_id") );


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

CREATE  TABLE "PHONE" (
  "phone_id" INT NOT NULL ,
  "phone" VARCHAR2(50) NOT NULL ,
  "complex_id" INT NOT NULL ,
  PRIMARY KEY ("phone_id"),
  CONSTRAINT "fk_PHONE_COMPLEX"
    FOREIGN KEY ("complex_id" )
    REFERENCES "COMPLEX" ("complex_id")
    ON DELETE CASCADE);

CREATE SEQUENCE phone_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER phone_trigger
BEFORE INSERT
ON "PHONE" 
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT phone_sequence.nextval INTO :NEW."phone_id" FROM dual;
END;
/

CREATE  TABLE "FIELD" (
  "field_id" INT NOT NULL  ,
  "complex_id" INT NOT NULL ,
  "name" VARCHAR2(50) NOT NULL ,
  "description" VARCHAR2(100) NULL ,
  "number_of_players" INT NOT NULL ,
  "has_roof" NUMBER(1) ,
  "type" INT NOT NULL ,
  "price" VARCHAR2(10) NOT NULL ,
  "under_maintenance" NUMBER(1) NOT NULL ,
  "picture" BLOB NULL ,
  PRIMARY KEY ("field_id") ,
  CONSTRAINT "fk_FIELD_COMPLEX"
    FOREIGN KEY ("complex_id" )
    REFERENCES "COMPLEX" ("complex_id" ) 
    ON DELETE CASCADE);

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
  "from" TIMESTAMP NOT NULL ,
  "to" TIMESTAMP NOT NULL ,
  PRIMARY KEY ("timetable_id") ,
  CONSTRAINT "fk_TIMETABLE_COMPLEX"
    FOREIGN KEY ("complex_id" )
    REFERENCES "COMPLEX" ("complex_id" ) ON DELETE CASCADE);

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

CREATE TABLE "EXPIRATION_POLICY" (
  "expiration_policy_id" INT NOT NULL ,
  "complex_id" INT NOT NULL ,
  "field_id" INT ,
  "from_score" INT NOT NULL ,
  "to_score" INT NOT NULL ,
  "days_being_half_signed" INT NOT NULL ,
  "days_being_reserved" INT NOT NULL ,
  PRIMARY KEY ("expiration_policy_id") ,
  CONSTRAINT "fk_EXPIRATION_POLICY_COMPLEX"
  	FOREIGN KEY ("complex_id")
  	REFERENCES "COMPLEX" ("complex_id")
  	ON DELETE CASCADE,
  CONSTRAINT "fk_EXPIRATION_POLICY_FIELD"
    FOREIGN KEY ("field_id" )
    REFERENCES "FIELD" ("field_id" )
    ON DELETE CASCADE );

CREATE INDEX "fk_EXPIRATION_POLICY_FIELD" ON "EXPIRATION_POLICY" ("complex_id" ASC);

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

CREATE  TABLE "USERS" (
  "user_id" INT NOT NULL ,
  "name" VARCHAR2(50) NOT NULL ,
  "password" VARCHAR2(50) NOT NULL ,
  "score" INT ,
  "notify_before_expiration" INT ,
  "is_admin" NUMBER(1) NOT NULL ,
  CONSTRAINT name_unique UNIQUE ("name"),
  PRIMARY KEY ("user_id") );

CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER user_trigger
BEFORE INSERT
ON "USERS" 
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT user_sequence.nextval INTO :NEW."user_id" FROM dual;
END;
/

CREATE  TABLE "EMAIL" (
  "email_id" INT NOT NULL ,
  "email" VARCHAR2(50) NOT NULL ,
  "user_id" INT NOT NULL ,
  CONSTRAINT email_unique UNIQUE ("email"),
  PRIMARY KEY ("email_id"),
  CONSTRAINT "fk_EMAIL_USERS"
    FOREIGN KEY ("user_id" )
    REFERENCES "USERS" ("user_id") ON DELETE CASCADE);

CREATE SEQUENCE email_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER email_trigger
BEFORE INSERT
ON "EMAIL" 
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT email_sequence.nextval INTO :NEW."email_id" FROM dual;
END;
/

CREATE  TABLE "RESERVATION" (
  "reservation_id" INT NOT NULL ,
  "user_id" INT NOT NULL ,
  "field_id" INT NOT NULL ,
  "state" INT NOT NULL ,
  "start_date" TIMESTAMP NOT NULL ,
  "end_date" TIMESTAMP NOT NULL ,
  "cost" VARCHAR2(10) NOT NULL ,
  "paid" VARCHAR2(10) NOT NULL ,
  "expiration_date" TIMESTAMP ,
  PRIMARY KEY ("reservation_id") ,
  CONSTRAINT booking_unique UNIQUE ("field_id", "start_date", "end_date"),
  CONSTRAINT "fk_has_COMPLEX_has_FIELD_USERS"
    FOREIGN KEY ("user_id" )
    REFERENCES "USERS" ("user_id" ) ,
  CONSTRAINT "fk_RESERVATION_FIELD"
    FOREIGN KEY ("field_id" )
    REFERENCES "FIELD" ("field_id" ) ON DELETE CASCADE );

CREATE INDEX "fk_has_COMPLEX_has_FIELD_USERS" ON "RESERVATION" ("user_id" ASC);

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

CREATE  TABLE "REGISTER" (
  "id" INT NOT NULL ,
  "name" VARCHAR2(50) NOT NULL ,
  "password" VARCHAR2(50) NOT NULL ,
  "email" VARCHAR2(50) NOT NULL ,
  "is_admin" NUMBER(1) NOT NULL ,
  "hash" VARCHAR2(40) NOT NULL ,
  PRIMARY KEY ("id") ,
  CONSTRAINT register_name_unique UNIQUE ("name"),
  CONSTRAINT register_hash_unique UNIQUE ("hash")
  );

CREATE SEQUENCE register_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER register_trigger
BEFORE INSERT
ON "REGISTER"
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT register_sequence.nextval INTO :NEW."id" FROM dual;
END;
/
