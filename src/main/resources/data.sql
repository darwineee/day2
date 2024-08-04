set transaction isolation level repeatable read;

insert into users (id, email, password)
values (0, 'admin', '1234')
on conflict do nothing;

insert into rooms (id, room_class)
values (0, 'A'), (1, 'B'), (2, 'C')
on conflict do nothing;