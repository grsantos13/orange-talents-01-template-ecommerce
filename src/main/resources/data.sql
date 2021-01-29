insert into user(login, password, created_at)
values ('gsantoset@gmail.com', '$2a$10$B2kjx69ekW.H5rgxnxcnrOFpRuo7CV7Q7tk4JNsHX2OnCJcpVonY.', now());

insert into category(name) values('Eletrodoméstico');

insert into product(name, description, value, category_id, owner_id, available_amount, created_at)
values('Microondas', 'Microondas branco novinho em folha.', 500, 1, 1, 5, now());

insert into feature(name, description, product_id)
values('Cor', 'Branco', 1), ('Estado', 'Novo', 1), ('Peso', '2 KG', 1);