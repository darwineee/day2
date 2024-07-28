create table if not exists users (
    uid serial primary key,
    email varchar(50) not null,
    password varchar(50) not null
);

create table if not exists rooms (
    rid serial primary key,
    constraint fk_room_class
        foreign key(rcls_id)
        references room_class(id)
);

create table if not exists room_class (
    id serial primary key,
    quantity int not null,
    price int not null
);

create table if not exists reservations (
    id serial primary key,
    constraint fk_user
        foreign key(user_id)
        references users(uid),
    constraint fk_room
        foreign key(room_id)
        references rooms(rid),
    check_in timestamp not null,
    check_out timestamp not null
);