INSERT INTO authors (author_id, name, gender, birthdate) 
VALUES (1, 'Cay S. Horstmann', 'MALE', '1959-06-16');

INSERT INTO books (book_id, author_id, title, price, available, creation_date, update_date) 
VALUES (1, 1, 'Core Java: Advanced Features uCertify Labs Access Code Card', 10.11, TRUE, '2020-03-12T21:04:59.123', '2020-04-12T21:04:59.123');

INSERT INTO book_documents (book_document_id, book_id, content_type, extension, name, size, content, creation_date, update_date) 
VALUES (1, 1, 'text/plain', 'txt', 'Document', 123, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', '2020-03-12T21:04:59.123', '2020-04-12T21:04:59.123');

