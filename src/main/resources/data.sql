insert into Roles values(1, 'ROLE_USER');
insert into Roles values(2, 'ROLE_ADMIN');

insert into Users(id, email, first_name, is_enabled, last_name, password, username, token_id) values(1, 'admin@admin.pl', 'Admin', true, 'Admin', '$2y$10$/mgho.JCtmvMKdivPbE2huplL3IEuK2095bb94dVHgsrllK6xwQNK', 'admin', NULL);

insert into User_roles values(1, 2);

