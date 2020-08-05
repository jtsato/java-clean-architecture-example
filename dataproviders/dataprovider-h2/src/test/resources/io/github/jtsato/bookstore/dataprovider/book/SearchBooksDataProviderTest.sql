INSERT INTO AUTHORS (AUTHOR_ID, NAME, GENDER, BIRTHDAY) 
VALUES (1, 'Cay S. Horstmann', 'MALE', '1959-06-16');

INSERT INTO AUTHORS (AUTHOR_ID, NAME, GENDER, BIRTHDAY) 
VALUES (2, 'Joshua Bloch', 'MALE', '1961-08-28');

INSERT INTO BOOKS (BOOK_ID, AUTHOR_ID, TITLE, PRICE, AVAILABLE, CREATION_DATE, UPDATE_DATE) 
VALUES (1, 1, 'Core Java: Advanced Features uCertify Labs Access Code Card', 10.11, TRUE, '2020-03-12T21:04:59.123', '2020-04-12T21:04:59.123');

INSERT INTO BOOKS (BOOK_ID, AUTHOR_ID, TITLE, PRICE, AVAILABLE, CREATION_DATE, UPDATE_DATE) 
VALUES (2, 1, 'Core Java SE 9 for the Impatient, 2nd Edition', 20.22, FALSE, '2020-03-12T21:04:59.123', '2020-04-12T21:04:59.123');

INSERT INTO BOOKS (BOOK_ID, AUTHOR_ID, TITLE, PRICE, AVAILABLE, CREATION_DATE, UPDATE_DATE) 
VALUES (3, 2, 'Effective Java', 30.33, TRUE, '2020-03-12T22:04:59.123', '2020-04-12T22:04:59.123');

INSERT INTO BOOKS (BOOK_ID, AUTHOR_ID, TITLE, PRICE, AVAILABLE, CREATION_DATE, UPDATE_DATE) 
VALUES (4, 2, 'Effective Java (2nd Edition)', 40.44, FALSE, '2020-03-12T22:04:59.123', '2020-04-12T22:04:59.123');