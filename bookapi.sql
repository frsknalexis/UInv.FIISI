create table books(
book_id serial not null,
title varchar(50),
author varchar(100),
constraint pk_book primary key(book_id)
);
alter sequence books_book_id_seq rename to book_seq;

select * from books;
insert into books(title, author) values('Three mistakes of my life', 'Chethan bhagath');
insert into books(title, author) values('Two states', 'Chethan bhagath');
insert into books(title, author) values('I too had a love story', 'Ravinder singh');