insert into user(login, password, created_at)
values ('gsantoset@gmail.com', '$2a$10$B2kjx69ekW.H5rgxnxcnrOFpRuo7CV7Q7tk4JNsHX2OnCJcpVonY.', now());

insert into category(name) values('Eletrodoméstico');

insert into product(name, description, value, category_id, owner_id, available_amount, created_at)
values('Microondas', 'Microondas branco novinho em folha.', 500, 1, 1, 5, now());

insert into feature(name, description, product_id)
values('Cor', 'Branco', 1), ('Estado', 'Novo', 1), ('Peso', '2 KG', 1);

insert into image(link, product_id)
values('https://api.mercadolibre.com/pictures/0d519408-f6db-4426-a547-a1233ce26fa4wallpaper1.png', 1);

insert into question(created_at, title, interested_person_id, product_id)
values(now(), 'Qual é o valor do frete para Campinas?', 1, 1), (now(), 'Qual é o valor do frete para Monte Mor?', 1, 1);

insert into opinion(description, score, title, product_id, user_id)
values('Produto em perfeito estado e qualidade.', 5, 'Ótimo produto!', 1, 1);

insert into purchase(gateway, quantity, customer_id, product_id)
values('paypal', 3, 1, 1);

update product set available_amount = 2 where id = 1;