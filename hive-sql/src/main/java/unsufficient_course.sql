--未选全课程学生
--solution1
SELECT cc.student_id
from
(
  SELECT  aa.student_id,
          bb.course_id
  from (
  SELECT student_id
  from dev.student_course_tbl
  group by student_id
  ) aa
  inner join
  dev.course_tbl bb
) cc
left join
dev.student_course_tbl dd
on  cc.student_id = dd.student_id
and cc.course_id = dd.course_id
where dd.student_id is NULL
group by cc.student_id



--solution2
SELECT  aa.student_id
from
(
SELECT  student_id,
        COUNT(1) as cnt
from dev.student_course_tbl
group by student_id
) aa
inner join
(
SELECT COUNT(1) as cnt
from dev.course_tbl
) bb
where  aa.cnt < bb.cnt
group by aa.student_id