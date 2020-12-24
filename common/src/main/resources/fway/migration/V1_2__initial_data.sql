--users
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('bestmaster94@mail.ru', 'mil', '$2a$10$Jr9lCI/PvbYu4o0LaWe3BeMCRRL2ode0F7dkx/4pIQYzozYXjLr1C', 'Nikita', 'Striga', 'MALE', '1994-07-01', 'Belarus', 'Minsk', '2019-01-01', '2019-01-01','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('mwyson12@google.cn', 'lbantham12', '$2a$10$m4EgbYRLwD3.RcFAm8r5eexRr9NqPYvfK8fZThA3pbtoWpFEGmCfS', 'Marv', 'Wyson', 'MALE', '2020-11-04', 'Czech Republic', 'Předměřice nad Labem', '2020-08-08', '2020-04-05','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('sitzchaky13@dropbox.com', 'mtyt13', '$2a$10$lSl3ibIC7ClS2c7Xw9/EReC3DQwrCEMvzh7T1c.piJL/2wvUi1PAy', 'Sabine', 'Itzchaky', 'FEMALE', '2020-04-13', 'Kazakhstan', 'Terenozek', '2020-01-26', '2020-10-02','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('clandreth14@ycombinator.com', 'phospital14', '$2a$10$ngJqw2C7b000w6oKPRAiMuO.vcD7yGA7XuUk4QxC661Crkh11ALve', 'Cletus', 'Landreth', 'MALE', '2020-08-26', 'Philippines', 'Guinsadan', '2020-11-13', '2020-02-07','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('fshellcross15@hostgator.com', 'ngommowe15', '$2a$10$clA21ek3ZM3Y9NtHk1JHEuV8zrvpFMdOOau0VqzVLwrrlweTR8ama', 'Findley', 'Shellcross', 'MALE', '2020-06-11', 'France', 'Challans', '2020-02-05', '2020-02-16','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('dmitcham16@networkadvertising.org', 'aborer16', '$2a$10$c4cMzSb0RdhB9lU7z5YMg..iFeXBdPxdlHBuhcD1cHA0d.qd.GBU.', 'Dayna', 'Mitcham', 'FEMALE', '2020-02-01', 'China', 'Shitouhe', '2020-01-11', '2020-02-20','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('osteaning17@mapy.cz', 'ehuckin17', '$2a$10$L4Ej28potguW8JasUMZMauflZpa4sP.7/2AeWzRwL2nRbM.OFO0Ma', 'Oates', 'Steaning', 'MALE', '2020-02-05', 'Belarus', 'Horad Krychaw', '2020-05-21', '2020-08-03','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('echipp18@canalblog.com', 'bmaccartair18', '$2a$10$Jv8VEngYxr0jTuzUPQwy/u70P3dihM.VvPjbPq5CtwSbiN0HEyyXS', 'Ezequiel', 'Chipp', 'MALE', '2020-10-01', 'United States', 'Scottsdale', '2020-11-09', '2020-01-18','2019-01-01', false);
insert into users (email, login, password, first_name, last_name, gender, birthday, country, city,  registration_time, update_time, p_change, delete) values ('pmcniven19@studiopress.com', 'egault19', '$2a$10$TzPG9BgsSZdvpvDNgDzZBehE1UvzCvIjvuWNYEVjIGjzB1HYzFeGW', 'Persis', 'McNiven', 'FEMALE', '2019-12-03', 'Russia', 'Tigil', '2020-08-12', '2020-10-13','2019-01-01', false);

--roles
insert into roles (role, user_id) VALUES ('ROLE_USER',1), ('ROLE_ADMIN',1);
insert into roles (role, user_id) VALUES ('ROLE_USER',2);
insert into roles (role, user_id) VALUES ('ROLE_USER',3);
insert into roles (role, user_id) VALUES ('ROLE_USER',4);
insert into roles (role, user_id) VALUES ('ROLE_USER',5);
insert into roles (role, user_id) VALUES ('ROLE_USER',6);
insert into roles (role, user_id) VALUES ('ROLE_USER',7);
insert into roles (role, user_id) VALUES ('ROLE_USER',8);
insert into roles (role, user_id) VALUES ('ROLE_USER',9)

--friends
insert into user_friends values (1,2);
insert into user_friends values (1,3);
insert into user_friends values (1,4);
insert into user_friends values (2,1);
insert into user_friends values (3,1);
insert into user_friends values (4,1);

--subs
insert into user_subscribers values (1,5);
insert into user_subscribers values (1,6);
insert into user_subscribers values (1,7);

--text
insert into text (created, delete, update_time, text, user_id) values ('2020-04-13 00:00:00.000000',false,'2020-04-13 00:00:00.000000','Its first message on my wall! ',1);
insert into text (created, delete, update_time, text, user_id) values ('2020-04-13 01:00:00.000000',false,'2020-04-13 01:00:00.000000','Wow, its nice day today!',1);

--text comments
insert into text_comments (comment, created, deleted, update_time, text_id, user_id) values ('Agree with you :)','2020-05-13 00:00:00.000000',false,'2020-05-13 00:00:00.000000',2,2);
insert into text_comments (comment, created, deleted, update_time, text_id, user_id) values ('Me 2 :>','2020-05-13 02:00:00.000000',false,'2020-05-13 02:00:00.000000',2,3);
insert into text_comments (comment, created, deleted, update_time, text_id, user_id) values ('What do you know about aliens ?','2020-05-13 05:00:00.000000',false,'2020-05-13 05:00:00.000000',2,4);
insert into text_comments (comment, created, deleted, update_time, text_id, user_id) values ('No smoke more , Man !','2020-05-13 08:00:00.000000',false,'2020-05-13 08:00:00.000000',2,5);