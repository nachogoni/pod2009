
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
INSERT INTO EMAIL VALUES(NULL, '3@user1.com', 3);
INSERT INTO EMAIL VALUES(NULL, '1@user2.com', 4);
INSERT INTO EMAIL VALUES(NULL, '2@user2.com', 4);
INSERT INTO EMAIL VALUES(NULL, '3@user2.com', 4);

INSERT INTO SCORE_SYSTEM VALUES (1, 1, 1, 1, 1, 1);

INSERT INTO COMPLEX VALUES (NULL,'Complejo1','Desc complejo 1', 'direccion 1', 'ciudad1','state1','country1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO COMPLEX VALUES (NULL,'Complejo2','Desc complejo 2', 'direccion 2', 'ciudad2','state2','country2', NULL, NULL, NULL, NULL, NULL);

INSERT INTO FIELD VALUES(NULL,1,'cancha1', 'descripcion cancha1', 4, 1, 1, 22.4, 0, NULL);
INSERT INTO FIELD VALUES(NULL,1,'cancha2', 'descripcion cancha2', 4, 1, 1, 30.4, 0, NULL);
INSERT INTO FIELD VALUES(NULL,2,'cancha3', 'descripcion cancha3', 4, 1, 1, 30.4, 0, NULL);

INSERT INTO RESERVATION VALUES (NULL, 1, 1, 1, sysdate, sysdate);
INSERT INTO RESERVATION VALUES (NULL, 1, 2, 1, sysdate, sysdate);

INSERT INTO TIMETABLE VALUES (NULL, 1, 0, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 1, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 2, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 3, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 4, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 5, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 6, sysdate, sysdate);

COMMIT;
