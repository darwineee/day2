create table if not exists users (
    uid serial primary key,
    email varchar(50) not null,
    password varchar(50) not null
);

create table if not exists rooms (
    rid serial primary key,
    class char(1) not null
);

create table if not exists reservations (
    id serial primary key,
    user_id int not null,
    room_id int not null,
    check_in timestamp not null,
    check_out timestamp not null,
    constraint fk_user foreign key(user_id) references users(uid),
    constraint fk_room foreign key(room_id) references rooms(rid)
);