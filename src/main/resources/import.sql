

INSERT INTO usuarios (email, created, modified, is_active, last_login, name, password, token, id) VALUES ('h.hidalgo1990@gmail.com', '2023-04-23', '2023-04-23', true, '2023-04-23', 'Hector Hidalgo', 'Hector12', '52f2e44a-53b7-4986-b456-a632924c7618', 1);
INSERT INTO usuarios (email, created, modified, is_active, last_login, name, password, token, id) VALUES ('juan@rlodriguez.org', '2023-04-23', '2023-04-23', true, '2023-04-23', 'Juan Rodriguez', 'Jupiter56', '52f2e44a-53b7-4986-b456-a632924c7618', 2);

INSERT INTO telefonos (citycode, countrycode, number, usuario_id, id) VALUES ('5', '55', '123456557', 1, 111);
INSERT INTO telefonos (citycode, countrycode, number, usuario_id, id) VALUES ('6', '56', '962234800', 2, 222);
INSERT INTO telefonos (citycode, countrycode, number, usuario_id, id) VALUES ('7', '77', '877654222', 2, 333);

INSERT INTO usuarios_phones (usuario_id, phone_id) VALUES (1, 111);
INSERT INTO usuarios_phones (usuario_id, phone_id) VALUES (2, 222);
INSERT INTO usuarios_phones (usuario_id, phone_id) VALUES (2, 333);