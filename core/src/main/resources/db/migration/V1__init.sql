CREATE TABLE books (
    id binary(16) PRIMARY KEY,
    name varchar(255) NOT NULL,
    author varchar(255) NOT NULL,
    is_available tinyint(1) NULL DEFAULT 1
);