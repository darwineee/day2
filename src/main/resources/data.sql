set transaction isolation level repeatable read;

insert into users (id, email, password)
values (0, 'admin', '1234')
on conflict do nothing;

insert into rooms (class)
values ('A'), ('B'), ('C')
on conflict do nothing;