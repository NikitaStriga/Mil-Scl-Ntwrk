create table dialogs
(
    id      bigserial not null,
    created timestamp,
    delete  boolean,
    primary key (id)
);
create table messages
(
    id          bigserial    not null,
    created     timestamp,
    delete      boolean,
    text        varchar(350) not null,
    update_time timestamp,
    dialog_id   int8,
    from_who    int8,
    to_who      int8,
    primary key (id)
);
create table photo_comment_likes
(
    id               bigserial not null,
    delete           boolean,
    photo_comment_id int8,
    user_id          int8,
    primary key (id)
);
create table photo_comments
(
    id          bigserial    not null,
    comments    varchar(350) not null,
    created     timestamp,
    delete      boolean,
    update_time timestamp,
    photo_id    int8,
    user_id     int8,
    primary key (id)
);
create table photo_likes
(
    id       bigserial not null,
    delete   boolean,
    photo_id int8,
    user_id  int8,
    primary key (id)
);
create table photos
(
    id          bigserial    not null,
    created     timestamp,
    delete      boolean,
    description varchar(350) not null,
    main_photo  boolean,
    path        varchar(100) not null,
    update_time timestamp,
    user_id     int8,
    primary key (id)
);
create table roles
(
    id      bigserial not null,
    role    varchar(255),
    user_id int8,
    primary key (id)
);
create table text
(
    id          bigserial    not null,
    created     timestamp,
    delete      boolean,
    text        varchar(350) not null,
    update_time timestamp,
    user_id     int8,
    primary key (id)
);
create table text_comment_likes
(
    id              bigserial not null,
    delete          boolean,
    text_comment_id int8,
    user_id         int8,
    primary key (id)
);
create table text_comments
(
    id          bigserial    not null,
    comment     varchar(350) not null,
    created     timestamp,
    deleted     boolean,
    update_time timestamp,
    text_id     int8,
    user_id     int8,
    primary key (id)
);
create table text_likes
(
    id      bigserial not null,
    delete  boolean,
    text_id int8,
    user_id int8,
    primary key (id)
);
create table user_dialogs
(
    dialog_id int8 not null,
    user_id   int8 not null
);
create table user_friends
(
    user_id   int8 not null,
    friend_id int8 not null
);
create table user_subscribers
(
    user_id       int8 not null,
    subscriber_id int8 not null
);
create table users
(
    id                bigserial    not null,
    birthday          date,
    city              varchar(50)  not null,
    country           varchar(50)  not null,
    delete            boolean,
    email             varchar(50)  not null,
    first_name        varchar(50)  not null,
    gender            varchar(255),
    last_name         varchar(50)  not null,
    login             varchar(50)  not null,
    p_change          timestamp,
    password          varchar(255) not null,
    recovery_code     varchar(36),
    registration_time timestamp,
    update_time       timestamp,
    primary key (id)
);
create index dialogs_created_idx on dialogs (created desc);
create index messages_created_idx on messages (created desc);
alter table photo_comment_likes add constraint UK804ku59fpiux8l1knluu4ambm unique (photo_comment_id, user_id);
create index photo_comments_created_idx on photo_comments (created desc);
alter table photo_likes add constraint UKtqysj7gown603ao9qi79b9f0t unique (photo_id, user_id);
create index photos_created_idx on photos (created desc);
create index text_created_idx on text (created desc);
alter table text_comment_likes add constraint UKkpjcc7bm2r5exl2u4mqul91dr unique (text_comment_id, user_id);
create index text_comments_created_idx on text_comments (created desc);
alter table text_likes add constraint UKnl6tov8d3svkam1gjotu90qii unique (text_id, user_id);
alter table user_friends add constraint UKccfkx507uqkvyt4vhcvrvkkk5 unique (user_id, friend_id);
alter table user_subscribers add constraint UK60mid6ledpc5v3ncbvxonyqyv unique (user_id, subscriber_id);
create  index lower_case_username_idx on users ((lower(first_name)));
create  index lower_case_last_name_idx on users ((lower(last_name)));
create  index lower_case_country_idx on users ((lower(country)));
create  index lower_case_city_idx on users ((lower(city)));
create  index gender_idx on users (gender);
create  index birthday_idx on users (birthday);
create  index gender_w_birthday_idx on users (gender,birthday);
alter table messages add constraint FKjbkac2909pydsesgme6wc0mn5 foreign key (dialog_id) references dialogs on delete cascade;
alter table messages add constraint FKpmtn0hnx04lcv7fuurrotmnt3 foreign key (from_who) references users on delete cascade;
alter table messages add constraint FK6owg2yyb3hv91uk9d182kn5n1 foreign key (to_who) references users on delete cascade;
alter table photo_comment_likes add constraint FK751o1hcrp1ylryjm7bvpnwt0t foreign key (photo_comment_id) references photo_comments;
alter table photo_comment_likes add constraint FK88v02l41j918rfl4fh5r034h8 foreign key (user_id) references users on delete cascade;
alter table photo_comments add constraint FK4akkp0kxg9u9eca83wsr9kams foreign key (photo_id) references photos on delete cascade;
alter table photo_comments add constraint FK7359ufgdm1qjuol9orclqiw1p foreign key (user_id) references users on delete cascade;
alter table photo_likes add constraint FK78adkl7qidynp1t8wkare68vk foreign key (photo_id) references photos on delete cascade;
alter table photo_likes add constraint FKinc9pbwm3dd20a54psq7mg517 foreign key (user_id) references users on delete cascade;
alter table photos add constraint FKnm381g1ktlpsorbtpco2ljhuv foreign key (user_id) references users on delete cascade;
alter table roles add constraint FK97mxvrajhkq19dmvboprimeg1 foreign key (user_id) references users on delete cascade;
alter table text add constraint FKgrbh8crmeqmynwv5rdfvw92ng foreign key (user_id) references users on delete cascade;
alter table text_comment_likes add constraint FKaqx1ejlhuell7m3il0gk9vtb3 foreign key (text_comment_id) references text_comments on delete cascade;
alter table text_comment_likes add constraint FKp8ugp351ik2iveuomxm3ex9vx foreign key (user_id) references users on delete cascade;
alter table text_comments add constraint FKer54nkq171s2xnwcsvh0p1tf9 foreign key (text_id) references text on delete cascade;
alter table text_comments add constraint FKtmbxjo9x31bcwby8lapmyrn1s foreign key (user_id) references users on delete cascade;
alter table text_likes add constraint FKbifjjhvoqn4cmnyljyjgv0r5 foreign key (text_id) references text on delete cascade;
alter table text_likes add constraint FKar5tislqwrfhiq2bivtkg50wp foreign key (user_id) references users on delete cascade;
alter table user_dialogs add constraint FK1hol1xmkevv6wx89xx0b371rt foreign key (user_id) references users on delete cascade;
alter table user_dialogs add constraint FKqe7ng4gjq7vnmlc4vi18owoeh foreign key (dialog_id) references dialogs;
alter table user_friends add constraint FK11y5boh1e7gh60rdqixyetv3x foreign key (friend_id) references users;
alter table user_friends add constraint FKk08ugelrh9cea1oew3hgxryw2 foreign key (user_id) references users;
alter table user_subscribers add constraint FKcfwe7dpk9vtqyrlk1r8encdg3 foreign key (subscriber_id) references users;
alter table user_subscribers add constraint FKbk5ej45lnf5sv6k44uynphn29 foreign key (user_id) references users;