#Insert values into the "styles" table:
INSERT INTO beers.styles (style_id, name)
VALUES (1, 'English Pale Ale');
INSERT INTO beers.styles (style_id, name)
VALUES (2, 'Indian Pale Ale');
INSERT INTO beers.styles (style_id, name)
VALUES (3, 'Pilsner');
INSERT INTO beers.styles (style_id, name)
VALUES (4, 'Bulgarian biri4ka');
INSERT INTO beers.styles (style_id, name)
VALUES (5, 'Stout');
INSERT INTO beers.styles (style_id, name)
VALUES (6, 'Dark');


#Insert values into the "users" table:
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (1, 'Pesho', 'parolaPesho', 'Pesho', 'Peshov', 'Pesho@example.com',true);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (2, 'Gosho', 'parolaGosho', 'Gosho', 'Goshov', 'Gosho@example.com',false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (3, 'Tosho', 'parolaTosho', 'Tosho', 'Toshov', 'Tosho@example.com',false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (4, 'Gencho', 'parolaGencho', 'Gencho', 'Genchov', 'Gencho@example.com',false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (5, 'Todorka', 'parolaTodorka', 'Todorka', 'Todorkova', 'Todorka@example.com',false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (6, 'Sasho', 'parolaSasho', 'Sasho', 'Sashov', 'Sasho@example.com',false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, is_admin)
VALUES (7, 'Angel', 'parolaAngel', 'Angel', 'Angelov', 'Angel@example.com',false);


#Insert values into the "beers" table:
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (1, 'Zagorka', 1.1, 1, 1);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (2, 'Shumensko', 2.2, 2, 2);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (3, 'Pirinsko', 3.3, 3, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (4, 'Ariana', 4.4, 4, 4);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (5, 'Heineken', 5.5, 1, 5);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (6, 'Mlado pivo', 6.6, 2, 6);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (7, 'Stolichno', 7.7, 3, 7);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (8, 'Beks', 8.8, 4, 1);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (9, 'Amstel', 9.9, 5, 2);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (10, 'Budwaiser', 10.10, 2, 3);


#Insert values into the "users_beers" table:
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (1, 1, 0);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (1, 2, 0);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (1, 3, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (1, 4, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (2, 1, 0);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (2, 2, 0);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (2, 3, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (2, 4, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (3, 3, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (3, 4, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (3, 5, 1);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (3, 6, 0);
INSERT INTO beers.users_beers(user_id, beer_id, drunk)
VALUES (5, 7, 1);