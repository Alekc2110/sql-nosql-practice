--Select all primary skills that contain more than one word (please note that both ‘-‘ and ‘ ’ could be used as a separator)
SELECT primary_skill FROM student WHERE primary_skill ~ '^.*[-\s].*';
SELECT primary_skill FROM student WHERE primary_skill LIKE  '% %' OR primary_skill LIKE '%-%';

--Select all students who does not have second name (it is absent or consists from only one letter/letter with dot)
SELECT * FROM student WHERE surname IS NULL OR surname LIKE '_' OR surname LIKE '_.';
SELECT * FROM student WHERE surname IS NULL OR length(surname) <= 1 OR surname LIKE '_.';

--Select number of students passed exams for each subject and order result by number of student descending
SELECT sb.id, sb.name subject, count(er.student_id) num_students FROM subject sb
                                    JOIN exam_result er on sb.id = er.subject_id
                                                                 WHERE er.mark NOTNULL
                                                                 GROUP BY sb.id
                                                                 ORDER BY num_students DESC;

--Select number of students with the same exam marks for each subject
SELECT er.subject_id, er.mark, count(*) num_students FROM exam_result er
                                                     WHERE er.mark NOTNULL
                                                     GROUP BY er.subject_id, er.mark;

--Select students who passed at least two exams for different subjects
SELECT id, name, surname FROM student WHERE id IN (
                         SELECT student_id FROM exam_result
                         GROUP BY student_id HAVING count(DISTINCT subject_id) >=2
                                                );

--Select students who passed at least two exams for the same subject
SELECT st.name st_name, st.surname, er.subject_id, count(*) as num_exams
FROM student st JOIN exam_result er on er.student_id = st.id
                JOIN subject s on er.subject_id = s.id
WHERE er.mark NOTNULL
GROUP BY st.name, st.surname, er.subject_id
HAVING count(*) >= 2;

--Select all subjects which exams passed only students with the same primary skills
SELECT er.subject_id, sb.name subject_name, s.primary_skill
FROM exam_result er
         JOIN student s ON s.id = er.student_id
         JOIN subject sb ON sb.id = er.subject_id
GROUP BY er.subject_id, sb.name, s.primary_skill
HAVING count(DISTINCT s.id) > 1;

--Select all subjects which exams passed only students with the different primary skills
SELECT er.subject_id, sb.name subject_name, s.primary_skill
FROM exam_result er
         JOIN student s ON s.id = er.student_id
         JOIN subject sb ON sb.id = er.subject_id
GROUP BY er.subject_id, sb.name, s.primary_skill
EXCEPT
SELECT er.subject_id, sb.name subject_name, s.primary_skill
FROM exam_result er
         JOIN student s ON s.id = er.student_id
         JOIN subject sb ON sb.id = er.subject_id
GROUP BY er.subject_id, sb.name, s.primary_skill
HAVING count(DISTINCT s.id) > 1;

-- Select students who does not pass any exam using each the following operator:
-- Outer join
-- SubQuery with ‘not in’ clause
-- SubQuery with ‘any‘ clause
SELECT st.id, st.name, st.surname FROM student st
    LEFT JOIN exam_result er ON st.id = er.student_id
                                  WHERE er.id IS NULL;

SELECT id, name, surname FROM student WHERE id NOT IN(SELECT DISTINCT student_id FROM exam_result);

SELECT id, name, surname FROM student WHERE id = ANY(SELECT student_id FROM exam_result) = FALSE;

--Select all students whose average mark is bigger than overall average mark
SELECT st.id, st.name, st.surname, round(avg(er.mark), 1) as avg_mark
FROM student st
    JOIN exam_result er on st.id = er.student_id
GROUP BY st.id
HAVING avg(er.mark) > (SELECT avg(mark) FROM exam_result);

--Select top 5 students who passed their last exam better than average students
SELECT st.id, st.name, st.surname, round(avg(er.mark), 1) as avg_mark
FROM student st
         JOIN exam_result er on st.id = er.student_id
GROUP BY st.id
HAVING avg(er.mark) > (SELECT avg(mark) FROM exam_result)
ORDER BY avg_mark DESC limit 5;

--Select biggest mark for each student and add text description for the mark (use COALESCE and WHEN operators)
SELECT s.id,
       COALESCE(
           CASE
               WHEN max(mark) <=3 THEN 'BAD'
               WHEN max(mark) <=6 THEN 'AVERAGE'
               WHEN max(mark) <=8 THEN 'GOOD'
               WHEN max(mark) <=10 THEN 'EXCELLENT'
           END,
           'not passed'
           ) as max_mark
FROM exam_result er
        RIGHT JOIN student s ON s.id = er.student_id
GROUP BY s.id;

--Select number of all marks for each mark type (‘BAD’, ‘AVERAGE’,…)
SELECT
    CASE
      WHEN mark <= 3 THEN 'BAD'
      WHEN mark <= 6 THEN 'AVERAGE'
      WHEN mark <= 8 THEN 'GOOD'
      ELSE 'EXCELLENT'
    END AS mark_type,
    count(*) as count
FROM exam_result
GROUP BY mark_type;




