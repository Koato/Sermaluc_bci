-- Script para precargar datos en la base de datos
INSERT INTO USERS (id, name, email, password, created, modified, last_login, token, is_active)
VALUES (UUID(), 'Admin User', 'admin@sermaluc.pe', '$2a$12$kDr0TDdIPj53bSCqL0Oxq.4IXhAcW11udwUvCjALb9yQWH/f5hJwK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'dummy-token', TRUE),
       (UUID(), 'Gerente User', 'gerency@sermaluc.pe', '$2a$12$WKFzWxYO4et8dlkVXr6ZoO1J9EYBjXkD//.2yCgYvojoJ/wsWxz0G', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'dummy-token', TRUE);


--Admin@123
--deLl#53