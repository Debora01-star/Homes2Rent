INSERT INTO users(username, email, firstname, enabled, apikey, lastname, password) VALUES( 'admin', 'admin@test.nl', 'Debora', true, 'iets', 'Rodriguez Montaldo', 'verysecret');
INSERT INTO users(username, email, firstname, enabled, apikey, lastname, password) VALUES( 'user', 'user@test.nl', 'Alex', true, 'iets anders', 'Van Halen', 'justsecret');


INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');


INSERT INTO boekingen( id, finish_date, status, type_boeking, woning, price) VALUES (1, '2022, 03, 15','status', 'geboekt', 'woning1', 1500);
INSERT INTO boekingen( id, finish_date, status, type_boeking, woning, price) VALUES (2, '2022, 03, 21','status', 'geboekt', 'woning2', 750);


INSERT INTO woningen(id, type, name, price, rented) VALUES (1, 'huis', 'villa happiness', 1500, 'rented');
INSERT INTO woningen(id, type, name, price, rented) VALUES (1, 'appartamnet', 'cozy rooms', 750, 'rented');

INSERT INTO factuur(id, klant, price) VALUES (1, 'Jaap Job', 1500);
INSERT INTO factuur(id, klant, price) VALUES (2, 'Melinda Carlisle', 750);


INSERT INTO annulering(id, finish_date, status, type_boeking, price, name, woning) VALUES (1, '2022,03,15', 'status', 'niet geboekt', 1500, 'Dion', 'woning1');
INSERT INTO annulering(id, finish_date, status, type_boeking, price, name, woning) VALUES (2, '2022,03,21', 'status', 'niet geboekt', 750, 'Charlie', 'woning2');

INSERT INTO customers(id, e_mail, firstname, lastname, streetname, town, zipcode) VALUES (1, 'jaap@job.nl', 'Jaap', 'Job', 'koevoet45', 'Nijmegen', '1234AB');
INSERT INTO customers(id, e_mail, firstname, lastname, streetname, town, zipcode) VALUES (1, 'melinda@carlisle.nl', 'Melinda', 'Carlisle', 'hogetoren90', 'Roosendaal', '2345BC');


-- INSERT INTO boekingen (id, finish_date, status, type_boeking, woning, price) VALUES (1, '2022, 03, 15',  'status', 'geboekt',  woning1, 1500);
-- INSERT INTO boekingen (id, finish_date, status, type_boeking, woning, price) VALUES (2, '2022, 03, 21', 'status', 'geboekt',  woning2, 750);
-- als camelcase naar sql gaat hoofletter vervangen door underscore, bijv (ditGaatNietInSQL, dit_gaat_niet_in_s_q_l)