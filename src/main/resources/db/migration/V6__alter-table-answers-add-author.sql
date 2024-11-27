alter table answers
add column user_id bigint not null,
add constraint fk_answers_users foreign key (user_id) references users (id) on delete cascade;