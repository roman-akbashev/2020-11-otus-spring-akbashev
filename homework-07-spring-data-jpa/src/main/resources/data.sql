insert into authors (id, name) values (1, 'Swift')
insert into authors (id, name) values (2, 'Dostoevsky')
insert into authors (id, name) values (3, 'Goethe')

insert into genres (id, name) values (1, 'Satire')
insert into genres (id, name) values (2, 'Novel')
insert into genres (id, name) values (3, 'Tragedy')

insert into books (id, name, author_id, genre_id) values (1, 'Gulliver', 1, 1)
insert into books (id, name, author_id, genre_id) values (2, 'Idiot', 2, 2)
insert into books (id, name, author_id, genre_id) values (3, 'Faust', 3, 3)

insert into comments(id, comment_text, book_id) values (1, 'book comment 1', 1)
insert into comments(id, comment_text, book_id) values (2, 'book comment 2', 1)
insert into comments(id, comment_text, book_id) values (3, 'book comment 3', 1)