CREATE TABLE roles (
    id bigint auto_increment primary key,
    name varchar(50) not null unique
);
insert into roles (name) values ('ROLE_USER'), ('ROLE_ADMIN');
alter table users
    add column role_id BIGINT,
    add constraint fk_user_role foreign key (role_id) references roles (id);
update users set role_id = (select id from roles where name = 'ROLE_USER');
alter table users MODIFY role_id BIGINT not null;
