INSERT INTO authors (author_id, name, gender, birthdate) 
VALUES (1, 'Cay S. Horstmann', 'MALE', '1959-06-16');

INSERT INTO authors (author_id, name, gender, birthdate) 
VALUES (2, 'Joshua Bloch', 'MALE', '1961-08-28');

INSERT INTO books (book_id, author_id, title, price, available, creation_date, update_date) 
VALUES (1, 2, 'Effective Java (2nd Edition)', 40.44, FALSE, '2020-03-11T22:04:59.123', '2020-04-12T22:04:59.123');

INSERT INTO books (book_id, author_id, title, price, available, creation_date, update_date) 
VALUES (2, 2, 'Effective Java', 30.33, TRUE, '2021-03-12T22:04:59.123', '2021-04-12T22:04:59.123');

INSERT INTO books (book_id, author_id, title, price, available, creation_date, update_date) 
VALUES (3, 1, 'Core Java SE 9 for the Impatient, 2nd Edition', 20.22, FALSE, '2020-03-13T21:04:59.123', '2020-04-12T21:04:59.123');

INSERT INTO books (book_id, author_id, title, price, available, creation_date, update_date) 
VALUES (4, 1, 'Core Java: Advanced Features uCertify Labs Access Code Card', 10.11, TRUE, '2020-03-14T21:04:59.123', '2020-04-12T21:04:59.123');
