drop table if exists users;
create table users (
    id int primary key auto_increment,
    email varchar(250) not null,
    constraint uq_email unique (email)
);