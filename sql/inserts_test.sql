INSERT INTO "SCORE_SYSTEM" VALUES (1, 1, 1, 1, 1, 1);
INSERT INTO "COMPLEX" ("complex_id", "score_system_id", "name", "description", "address", "city", "state", "country") 
			VALUES (1,1,'Complejo 1','Descp complejo 1', 'direccion 1', 'city','state','country');
INSERT INTO "COMPLEX" ("complex_id", "score_system_id", "name", "description", "address", "city", "state", "country") VALUES (2,1,'Complejo 2','Descp complejo 2', 'direccion 2', 'city','state','country');			

SELECT "complex_id", "score_system_id", "name", "description", "address", "city", "state", "country" FROM "COMPLEX";

CREATE TABLE TESTTABLE (nombre VARCHAR2(50));
INSERT INTO TESTTABLE VALUES ('JOSEFO');

INSERT INTO SCORE_SYSTEM2 VALUES (1,2,3,4,5,6);
INSERT INTO SCORE_SYSTEM2 VALUES (2,22,23,24,25,26);
INSERT INTO SCORE_SYSTEM2 VALUES (3,32,33,34,35,36);