alter table topics
add column solution_answer_id bigint null;

alter table topics
add constraint fk_topics_solution_answer
foreign key (solution_answer_id) references answers (id)
on delete set null;
