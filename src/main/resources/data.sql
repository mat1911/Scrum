insert into Roles values(1, 'ROLE_USER');
insert into Roles values(2, 'ROLE_ADMIN');

insert into Users(id, email, first_name, is_enabled, last_name, password, username, token_id) values(1, 'admin@admin.pl', 'Admin', true, 'Admin', '$2y$10$/mgho.JCtmvMKdivPbE2huplL3IEuK2095bb94dVHgsrllK6xwQNK', 'admin', NULL);
insert into Users(id, email, first_name, is_enabled, last_name, password, username, token_id) values(2, 'admin2@admin.pl', 'Admin2', true, 'Admin2', '$2y$10$/mgho.JCtmvMKdivPbE2huplL3IEuK2095bb94dVHgsrllK6xwQNK', 'admin2', NULL);

insert into User_roles values(1, 2);
insert into User_roles values(2, 1);


insert into statuses (id, name) values
                                   (1, 'TO_DO'),
                                   (2, 'DOING'),
                                   (3, 'VERIFY'),
                                   (4, 'DONE');

insert into projects (id, name, description, owner_id) values (1, 'Project 1', 'Testowy projekt w aplikacji Scruminium', 1);
insert into projects (id, name, description, owner_id) values (2, 'Project 2', 'Testowy projekt w aplikacji Scruminium', 2);


insert into user_projects(user_id, project_id, user_capacity) values (1, 1, 20);
insert into user_projects(user_id, project_id, user_capacity) values (1, 2, 15);

insert into user_projects(user_id, project_id, user_capacity) values (2, 1, 15);
insert into user_projects(user_id, project_id, user_capacity) values (2, 2, 15);

insert into sprints (id, title, description, begin_date, end_date, status_id, project_id) values (1, '1. Pierwszy sprint', 'opis testowy', current_timestamp(), current_timestamp() + 7, 2, 1);
insert into sprints (id, title, description, begin_date, end_date, status_id, project_id) values (2, '2. Drugi sprint', 'opis testowy', current_timestamp() + 7, current_timestamp() + 14, 2, 1);
insert into sprints (id, title, description, begin_date, end_date, status_id, project_id) values (3, '1. Pierwszy sprint drugiego projektu', 'opis testowy', current_timestamp(), current_timestamp() + 7, 2, 2);

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id)
values ('Pierwsza historyjka w 1 Sprincie', 'krotki opis story1', 'dlugi opis', 1, 'akceptacja cos tam', 5, 1, 2, 1);
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id)
values ('Druga historyjka w 1 Sprincie', 'krotki opis story2', 'dlugi opis', 2, 'akceptacja cos tam', 5, 1, 3, 1);

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id)
values ('Pierwsza historyjka w 2 Sprincie', 'krotki opis story3', 'dlugi opis', 3, 'akceptacja cos tam', 5, 2, 1, 1);
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id)
values ('Druga historyjka w 2 Sprincie', 'krotki opis story4', 'dlugi opis', 4, 'akceptacja cos tam', 5, 2, 1, 1);


