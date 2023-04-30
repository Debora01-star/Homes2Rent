-- wachtwoord is Password
INSERT INTO users(username, email, firstname, enabled, apikey, lastname, password) VALUES( 'admin', 'admin@test.nl', 'Debora', true, 'iets', 'Rodriguez Montaldo', '$2a$12$NhcXDx2B6/02Gl2CJfLqsekr9raWsVYanJIvaQr.5JvqPmOBHu1BK');
INSERT INTO users(username, email, firstname, enabled, apikey, lastname, password) VALUES( 'user', 'user@test.nl', 'Alex', true, 'iets anders', 'Van Halen', '$2a$12$NhcXDx2B6/02Gl2CJfLqsekr9raWsVYanJIvaQr.5JvqPmOBHu1BK');


INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO customers(id, email, firstname, lastname, streetname, town, zipcode) VALUES (19, 'jaap@job.nl', 'Jaap', 'Job', 'koevoet45', 'Nijmegen', '1234AB');
INSERT INTO customers(id, email, firstname, lastname, streetname, town, zipcode) VALUES (20, 'melinda@carlisle.nl', 'Melinda', 'Carlisle', 'hogetoren90', 'Roosendaal', '2345BC');
INSERT INTO woningen(id, type, name, price, rented) VALUES (1, 'huis', 'villa happiness', 1500, 'rented');
INSERT INTO woningen(id, type, name, price, rented) VALUES (2, 'appartament', 'cozy rooms', 750, 'rented');
INSERT INTO boekingen( id, finish_date, status, type_boeking, woning_id, price) VALUES (1, '2023, 03, 15','status', 'geboekt', 1, 1500);
INSERT INTO boekingen( id, finish_date, status, type_boeking, woning_id, price) VALUES (2, '2023, 03, 21','status', 'geboekt', 2, 750);
INSERT INTO factuur(id, klant, price) VALUES (1, 'Jaap Job', 1500);
INSERT INTO factuur(id, klant, price) VALUES (2, 'Melinda Carlisle', 750);
INSERT INTO annulering(id, finish_date, status, type_boeking, price, name, woning_id) VALUES (1, '2023,03,15', 'status', 'niet geboekt', 1500, 'Dion', 1);
INSERT INTO annulering(id, finish_date, status, type_boeking, price, name, woning_id) VALUES (2, '2023,03,21', 'status', 'niet geboekt', 750, 'Charlie', 2);

--INSERT INTO customers(id, e_mail, firstname, lastname, streetname, town, zipcode) VALUES (1, 'jaap@job.nl', 'Jaap', 'Job', 'koevoet45', 'Nijmegen', '1234AB');INSERT INTO customers(id, e_mail, firstname, lastname, streetname, town, zipcode) VALUES (1, 'melinda@carlisle.nl', 'Melinda', 'Carlisle', 'hogetoren90', 'Roosendaal', '2345BC');


-- INSERT INTO boekingen (id, finish_date, status, type_boeking, woning, price) VALUES (1, '2022, 03, 15',  'status', 'geboekt',  woning1, 1500);
-- INSERT INTO boekingen (id, finish_date, status, type_boeking, woning, price) VALUES (2, '2022, 03, 21', 'status', 'geboekt',  woning2, 750);
-- als camelcase naar sql gaat hoofletter vervangen door underscore, bijv (ditGaatNietInSQL, dit_gaat_niet_in_s_q_l)