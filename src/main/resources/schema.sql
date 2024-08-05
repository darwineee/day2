create table if not exists users (
    id serial primary key,
    email varchar(50) not null,
    password varchar(255) not null,
    active boolean not null default true
);

create table if not exists rooms (
    id serial primary key,
    room_class char(1) not null,
    on_booking boolean not null default false
);

create table if not exists reservations (
    id serial primary key,
    user_id int not null,
    room_id int not null,
    check_in timestamp not null,
    check_out timestamp not null,
    constraint fk_user foreign key(user_id) references users(id),
    constraint fk_room foreign key(room_id) references rooms(id)
);