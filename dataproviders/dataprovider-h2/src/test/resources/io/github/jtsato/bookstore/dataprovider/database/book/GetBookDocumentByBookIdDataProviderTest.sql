INSERT INTO AUTHORS (AUTHOR_ID, NAME, GENDER, BIRTHDAY) VALUES (1, 'Cay S. Horstmann', 'MALE', '1959-06-16');

INSERT INTO BOOKS (BOOK_ID, AUTHOR_ID, TITLE, PRICE, AVAILABLE, CREATION_DATE, UPDATE_DATE) VALUES (1, 1, 'Core Java: Advanced Features uCertify Labs Access Code Card', 10.11, TRUE, '2020-03-12T21:04:59.123', '2020-04-12T21:04:59.123');

INSERT INTO BOOK_DOCUMENTS (BOOK_DOCUMENT_ID, BOOK_ID, CONTENT_TYPE, EXTENSION, NAME, SIZE, CONTENT, CREATION_DATE, UPDATE_DATE) VALUES (1, 1, 'text/plain', 'txt', 'Document', 123, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', '2020-03-12T21:04:59.123', '2020-04-12T21:04:59.123');
