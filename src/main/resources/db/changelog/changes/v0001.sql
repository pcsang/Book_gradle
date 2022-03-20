Create table book(
    id int not null,
    name varchar(255) not null,
    author varchar(255) not null,
    category varchar(255),
    desciption varchar(500),
    createDate date,
    updateDate date,
    primary key (id)
    );