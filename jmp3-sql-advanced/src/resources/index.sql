create index exam_result_student_id_idx on exam_result using hash(student_id);
create index exam_result_mark_id_idx on exam_result using btree(mark);
create index student_primary_skill_idx on student using btree(primary_skill);
create index student_primary_skill_idx on student using gin(primary_skill gin_trgm_ops);
create index student_surname_idx on student using btree(surname);
create index student_surname_idx on student using hash(surname);
create index student_surname_idx on student using gist(surname gist_trgm_ops);
