
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


INSERT INTO COMPLEX VALUES (NULL,'Complejo1','Desc complejo 1', 'direccion 1', 'barrio1', 'ciudad1','state1', '1337', 'country1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO COMPLEX VALUES (NULL,'Complejo2','Desc complejo 2', 'direccion 2', 'barrio2', 'ciudad2','state2', '1337', 'country2', NULL, NULL, NULL, NULL, NULL);

INSERT INTO FIELD VALUES(NULL,1,'cancha1', 'descripcion cancha1', 4, 1, 1, '22.40', 0, NULL);
INSERT INTO FIELD VALUES(NULL,1,'cancha2', 'descripcion cancha2', 4, 1, 1, '30.40', 0, NULL);
INSERT INTO FIELD VALUES(NULL,2,'cancha3', 'descripcion cancha3', 4, 1, 1, '30.40', 0, NULL);

INSERT INTO EXPIRATION_POLICY VALUES(NULL, 1, NULL, -2000000000000, 2000000000000, 12, 48);
INSERT INTO SCORE_SYSTEM VALUES(NULL, 10, 50, 100, 30, 100);

INSERT INTO TIMETABLE VALUES (NULL, 1, 5, '20-JUN-09 11.00.00.000000 AM', '20-JUN-09 2.00.00.000000 PM');
INSERT INTO TIMETABLE VALUES (NULL, 1, 1, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 2, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 3, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 4, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 5, sysdate, sysdate);
INSERT INTO TIMETABLE VALUES (NULL, 1, 6, sysdate, sysdate);

INSERT INTO RESERVATION VALUES (NULL, 1, 1, 1, '20-JUN-09 11.00.00.000000 AM', '20-JUN-09 12.00.00.000000 AM','22.40','0.00','20-JUN-09 07.00.00.000000 AM');
INSERT INTO RESERVATION VALUES (NULL, 1, 2, 1, sysdate, sysdate,'30.4','0.00',sysdate);


COMMIT;
