
INSERT INTO USERS (email, name, password, created_at, updated_at) 
VALUES 
('user1@gmail.com', 'User1', '$2a$10$3.UlVS2zcj.knCbF.c.qc.9Q3X7JKjo4ZNQQS0HBNerLB1omUo/0G', '2024-02-20', '2024-02-20'),
('user2@gmail.com', 'User2', '$2a$10$nV7iaqJiqLvyQQ9P2s/vxeqcPFybUEgGxv697sQkQL01LP39tyVQe', '2024-02-20', '2024-02-20'),
('user3@gmail.com', 'User3', '$2a$10$6F5mDGekNDJ2dnGJ8.7KvOB4NoOmW4WGHKjle.23V96D4C6QE9sKu', '2024-02-20', '2024-02-20')
;

INSERT INTO RENTALS (name, surface, price, picture, description, owner_id, created_at, updated_at) 
VALUES 
('Rental 1', 50, 800, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg','awesome apartment', 1, '2024-02-01', '2024-02-02'),
('Rental 2', 100, 2600, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg','awesome house', 1, '2024-02-05', '2024-02-07'),
('Rental 3', 20, 550, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg', 'awesome studio', 2, '2024-02-08', '2024-02-11'),
('Rental 4', 30, 750, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg','awesome two room flat', 3, '2024-02-15', '2024-02-20')
;

INSERT INTO MESSAGES (rental_id, user_id, message, created_at, updated_at) 
VALUES 
(1, 2, 'message about rental 10', '2024-02-01', '2024-02-01'),
(2, 3, 'message about rental 11', '2024-02-08', '2024-02-08'),
(3, 1, 'message about rental 20', '2024-02-10', '2024-02-10'),
(4, 1, 'message about rental 21', '2024-02-19', '2024-02-19')
;


