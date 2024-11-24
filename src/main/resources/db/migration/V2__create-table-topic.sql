CREATE TABLE topics (
    id bigint auto_increment primary key,
    title varchar(255) not null,
    content text not null,
    status tinyint not null default 1,
    course_id bigint,
    created_on timestamp default current_timestamp,
    last_updated_on timestamp default current_timestamp on update current_timestamp,
    constraint fk_topic_course foreign key (course_id) references courses (id) on delete set null
);

CREATE TABLE answers (
    id bigint auto_increment primary key,
    message text not null,
    solution boolean not null default false,
    topic_id bigint not null,
    created_on timestamp default current_timestamp,
    last_updated_on timestamp default current_timestamp on update current_timestamp,
    constraint fk_answer_topic foreign key (topic_id) references topics (id) on delete cascade
);
