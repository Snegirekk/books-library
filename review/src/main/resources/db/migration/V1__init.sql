CREATE TABLE book_reviews (
    id binary(16) PRIMARY KEY,
    book_id binary(16) NOT NULL,
    text varchar(255) NOT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
);