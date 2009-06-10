
INSERT INTO USERS VALUES(NULL, 'admin1', 'admin1', NULL, NULL, 1);
INSERT INTO USERS VALUES(NULL, 'admin2', 'admin2', NULL, NULL, 1);
INSERT INTO USERS VALUES(NULL, 'user1', 'user1', 1, 1, 0);
INSERT INTO USERS VALUES(NULL, 'user2', 'user2', 1, 1, 0);

INSERT INTO EMAIL VALUES(NULL, '1@admin1.com', 1);
INSERT INTO EMAIL VALUES(NULL, '2@admin1.com', 1);
INSERT INTO EMAIL VALUES(NULL, '3@admin1.com', 1);
INSERT INTO EMAIL VALUES(NULL, '1@admin2.com', 2);
INSERT INTO EMAIL VALUES(NULL, '2@admin2.com', 2);
INSERT INTO EMAIL VALUES(NULL, '3@admin2.com', 2);
INSERT INTO EMAIL VALUES(NULL, '1@user1.com', 3);
INSERT INTO EMAIL VALUES(NULL, '2@user1.com', 3);
INSERT INTO EMAIL VALUES(NULL, '2@user1.com', 3);
INSERT INTO EMAIL VALUES(NULL, '1@user2.com', 4);
INSERT INTO EMAIL VALUES(NULL, '2@user2.com', 4);
INSERT INTO EMAIL VALUES(NULL, '3@user2.com', 4);

INSERT INTO FIELD VALUES(NULL,1,'cancha1', 'descripcion cancha1', 4, 1, 1, 22.4, 0, NULL);
INSERT INTO FIELD VALUES(NULL,1,'cancha2', 'descripcion cancha2', 4, 1, 1, 30.4, 0, NULL);
INSERT INTO FIELD VALUES(NULL,2,'cancha3', 'descripcion cancha3', 4, 1, 1, 30.4, 0, NULL);
COMMIT;


--Cosas de pablo
--procedeer con cautela :)

INSERT INTO "SCORE_SYSTEM" VALUES (1, 1, 1, 1, 1, 1);
INSERT INTO "COMPLEX" ("complex_id", "score_system_id", "name", "description", "address", "city", "state", "country") 
			VALUES (1,1,'Complejo 1','Descp complejo 1', 'direccion 1', 'city','state','country');
INSERT INTO "COMPLEX" ("complex_id", "score_system_id", "name", "description", "address", "city", "state", "country") VALUES (2,1,'Complejo 2','Descp complejo 2', 'direccion 2', 'city','state','country');			

SELECT "complex_id", "score_system_id", "name", "description", "address", "city", "state", "country" FROM "COMPLEX";