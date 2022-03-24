CREATE TABLE book(
    id INT NOT NUll,
    name VARCHAR(255) not null,
    author VARCHAR(255) NOT NUll,
    category VARCHAR(255),
    desciption VARCHAR(500),
    create_date date,
    update_date date,
    PRIMARY KEY (id)
);