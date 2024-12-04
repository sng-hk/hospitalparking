-- ParkingSpace 테이블에 주차 공간 데이터 삽입
INSERT INTO parking_space (id, location) VALUES (1, 'A1');
INSERT INTO parking_space (id, location) VALUES (2, 'A2');
INSERT INTO parking_space (id, location) VALUES (3, 'A3');
INSERT INTO parking_space (id, location) VALUES (4, 'A4');
INSERT INTO parking_space (id, location) VALUES (5, 'A5');
INSERT INTO parking_space (id, location) VALUES (6, 'B1');
INSERT INTO parking_space (id, location) VALUES (7, 'B2');
INSERT INTO parking_space (id, location) VALUES (8, 'B3');
INSERT INTO parking_space (id, location) VALUES (9, 'B4');
INSERT INTO parking_space (id, location) VALUES (10, 'B5');
INSERT INTO parking_space (id, location) VALUES (11, 'C1');
INSERT INTO parking_space (id, location) VALUES (12, 'C2');
INSERT INTO parking_space (id, location) VALUES (13, 'C3');
INSERT INTO parking_space (id, location) VALUES (14, 'C4');
INSERT INTO parking_space (id, location) VALUES (15, 'C5');

INSERT INTO user (rrn, phone_number, name, id, login_id, password, car_number, email) VALUES ('1234','1234','admin', 2, '1234', '$2a$10$Gao07nxHB9WVLclsXLU7.e9gnzm7gCOuiWNxYWG4UFqWg5Xj3U.6.', '1234', '123@gmail.com');
INSERT INTO user_roles (user_id, roles) VALUES
    (2, 'ROLE_ADMIN');