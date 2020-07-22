drop table if exists local_auth;
drop table if exists third_auth;
drop table if exists article_counter;
drop table if exists user_follow;
drop table if exists article_extra_type;
drop table if exists extra_type_dict;
drop table if exists user_favourite_article;
drop table if exists article_tag;
drop table if exists tag;
drop table if exists comment_reply;
drop table if exists comment;
drop table if exists article;
drop table if exists user;

create table if not exists user
(
    id          int auto_increment,
    f_name      varchar(255) not null,
    l_name      varchar(255) not null,
    email       varchar(32),
    birthday    date,
    description text,
    avatar_url  varchar(255),
    update_time datetime     not null,
    create_time datetime     not null,
    primary key (id)
);

create table if not exists local_auth
(
    user_name     varchar(16),
    password      char(88),
    salt          char(88),
    salt_length   int not null,
    iteration_num int not null,
    user_id       int not null,
    primary key (user_name),
    foreign key (user_id) references user (id)
);

create table if not exists third_auth
(
    id                 int auto_increment,
    user_id            int not null,
    oauth_name         varchar(10),
    oauth_id           varchar(255),
    oauth_access_token text,
    oauth_expires      int,
    primary key (id),
    foreign key (user_id) references user (id)
);

create table if not exists article
(
    id             int auto_increment,
    title          varchar(255) not null,
    author_id      int          not null,
    content        longtext,
    excerpt        longtext,
    comment_status smallint,
    post_type      smallint,
    create_time    datetime,
    update_time    datetime,
    primary key (id),
    foreign key (author_id) references user (id)
);

create table if not exists comment
(
    id          int auto_increment,
    user_id     int,
    article_id  int,
    content     text,
    create_time datetime,
    update_time datetime,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (article_id) references article (id)
);

create table comment_reply
(
    id            int auto_increment,
    comment_id    int      not null comment 'the comment id where reply is',
    reply_id      int      not null comment 'the reply id you replied',
    reply_type    int      not null comment '0--comment reply 1-- reply reply',
    content       text     not null,
    from_user_id  int      not null,
    reply_user_id int      not null,
    create_time   datetime not null,
    update_time   datetime not null,
    constraint comment_reply_pk
        primary key (id),
    foreign key (comment_id) references comment (id),
    foreign key (from_user_id) references user (id),
    foreign key (reply_user_id) references user (id)
)
    comment 'reply comment table';

create table if not exists tag
(
    id       int auto_increment,
    tag_name varchar(32),
    primary key (id)
);

create table if not exists article_tag
(
    id         int auto_increment,
    article_id int,
    tag_id     int,
    primary key (id),
    foreign key (article_id) references article (id),
    foreign key (tag_id) references tag (id)
);

create table if not exists user_favourite_article
(
    id         int auto_increment,
    user_id    int,
    article_id int,
    status     smallint,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (article_id) references article (id)
);

create table if not exists extra_type_dict
(
    id        int auto_increment,
    dict_name varchar(16) not null,
    primary key (id)
);

create table if not exists article_extra_type
(
    article_id int,
    dict_type  int,
    dict_value smallint,
    primary key (article_id, dict_type),
    foreign key (article_id) references article (id),
    foreign key (dict_type) references extra_type_dict (id)
);

create table if not exists user_follow
(
    id               int auto_increment,
    user_id          int,
    followed_user_id int,
    status           smallint,
    create_time      datetime,
    update_time      datetime,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (followed_user_id) references user (id)
);

create table if not exists article_counter
(
    article_id   int not null,
    counter_type int not null,
    counter_num  int,
    primary key (article_id, counter_type),
    foreign key (counter_type) references extra_type_dict (id)
);
